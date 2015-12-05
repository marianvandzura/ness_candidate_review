angular.module('NESS-TCFA', ['ngCookies','ngAnimate','ngRoute','ui.codemirror'])//App init
  .run(['$rootScope', '$http','$window',function($rootScope, $http, $window){
    $rootScope.serverUrl = 'http://localhost:8080/rest/';
    $rootScope.settings = {
      logged: false
    };
    $rootScope.connProblem = {
      problem : false,
      message: 'Connection problem, please contact Admin!'
    };
    $rootScope.login = function(name, password){
      $http.get($rootScope.serverUrl+'oauth/token?grant_type=password&client_id=ness-candidates-review&username='+name+'&password='+hex_md5(password)).then(
        function(response){
          $rootScope.settings.logged = true;
          console.log(response.data);
          //ziskavanie tokenu
          $rootScope.settings.auth = response.data;
          //automaticky update tokenu
          $rootScope.settings.passwordRefresher = setInterval(function(){
            ///rest/oauth/token?grant_type=refresh_token&refresh_token=a8960176-e73f-4358-a6d8-ae74478719bc&client_id=ness-candidates-review
            $http.get($rootScope.serverUrl+'oauth/token?grant_type=refresh_token&client_id=ness-candidates-review&refresh_token='+$rootScope.settings.auth.refresh_token).then(
              function(response){
                $rootScope.settings.auth = response.data;
                console.log(response.data);

              },
              function(){
                console.log("Connecting problem!");
              }
            );
            console.log("sranda");

          },240000);
          //informacie o prihlasenom pouzivatelovi (hlavne id)
          $http.get($rootScope.serverUrl+'admin/user/info/'+name+"?access_token="+$rootScope.settings.auth.access_token).then(
            function(response){
              $rootScope.settings.user = {
                id: response.data.user_id,
                name: response.data.user_name,
                surname: response.data.email
              };
              console.log(response.data);

            },
            function(){
              console.log("Connecting problem!");
            }
          );

        },
        function(){
          console.log("Connecting problem!");
        }
      );


      if(true){//TODO: Authentication
        $rootScope.settings.user = {
          id:1,
          name: 'Tony',
          surname: 'Potato'
        };
        $rootScope.settings.logged = true;
        $('#loginModal').modal('hide');
        $window.location.href = '#/controlPanel/';
      }
    };
    $rootScope.login('admin','admin');
    $rootScope.logout = function(){
      delete $rootScope.settings.user;
      $rootScope.settings.logged = false;
      $window.location.href = '#/home/';
      clearInterval($rootScope.settings.passwordRefresher);
    };
    $rootScope.languages = [{
      id: 'javascript',
      name: 'JavaScript'
    },{
      id: 'java',
      name: 'Java'
    },{
      id: 'c',
      name: 'C'
    },{
      id: 'c++',
      name: 'C++'
    },{
      id: 'c#',
      name: 'C#'
    },{
      id: 'php',
      name: 'PHP'
    }
    ];
    $rootScope.communicator = new Communicator($http,$rootScope.serverUrl);
    //Playground



  }])
  .controller('homeController',['$scope','$rootScope','$window', '$http', function ($scope,$rootScope, $window, $http) {//homeController
    $http.get($rootScope.serverUrl+'tests/').then(
      function(response){
        $scope.tests = response.data;
        console.log(response.data);
      },
      function(){
        console.log("Connecting problem!");
      }
    );

    /*$scope.$on('$locationChangeStart', function( event ) {
      var answer = confirm("Are you sure you want to leave this page?")
      if (!answer) {
        event.preventDefault();
      }
    });*/





  }])
  .controller('controlPanelController',['$scope','$rootScope', function ($scope,$rootScope) {

  }])
  .controller('editTestController',['$scope','$rootScope','$routeParams','$http', function ($scope,$rootScope,$routeParams,$http) {
    $scope.categories = [];
    $scope.categoryQuestions = [];
    $http.get($rootScope.serverUrl+'admin/categories&access_token='+$rootScope.settings.auth.access_token).then(
      function(response){
        $scope.categories = response.data;
      },
      function(){
        console.log("Connecting problem!");
      }
    );

    if($routeParams.testId == 0)
      $scope.test = new Test(null,'',false,'','',$rootScope.settings.user.id);
    else{
      $http.get($rootScope.serverUrl+'admin/test/'+$routeParams.testId+"?access_token="+$rootScope.settings.auth.access_token).then(
        function(response){
          $scope.test = parseTestForEdit(response.data);
          //code mirror
          for(x in $scope.test.questions)
            if($scope.test.questions[x].language == undefined || $scope.test.questions[x].language == null) {
              $scope.test.questions[x].cmOption = {
                lineNumbers: true,
                theme:'dracula',
                indentWithTabs: true,
                mode: '',
                onLoad : function(_cm){
                  $scope.test.questions[x].modeChanged = function(){
                    console.log(this.language);
                    _cm.setOption("mode", this.language);
                  };
                }
              };
            }
            else{
              $scope.test.questions[x].cmOption = {
                lineNumbers: true,
                theme:'dracula',
                indentWithTabs: true,
                mode: $scope.test.questions[x].language,
                onLoad : function(_cm){
                  $scope.test.questions[x].modeChanged = function(){
                    console.log(this.language);
                    _cm.setOption("mode", this.language);
                  };
                }
              };
            }
        },
        function(){
          console.log("Connecting problem!");
        }
      );
    }
    $scope.changeCategory = function(){
      if($scope.selectedCategory){
        //questions/category/1
        $http.get($rootScope.serverUrl+'questions/category/' + $scope.selectedCategory.id+"?access_token="+$rootScope.settings.auth.access_token).then(
          function(response){
            $scope.categoryQuestions = response.data;
            console.log(response.data);
          },
          function(){
            console.log("Connecting problem!");
          }
        );

      }else $scope.categoryQuestions = [];
    };
    $scope.questionSelected = function(){
      if(!$scope.selectedCategory) return;
      var temp = $scope.selectedQuestion[0];
      for(x in $scope.test.questions)
        if($scope.test.questions[x].id == temp.id){
          if(confirm("This question is already in test.\nDo you want to copy (create new similar) question?")){
            $scope.test.copyQuestion($scope.test.questions[x]);
            return;
          }else
            return;
        }
      $scope.test.questions.push(temp);
    };
    $scope.saveTest = function () {
      console.log($scope.test);
      if($routeParams.testId == 0){
        //create
        $http.post($rootScope.serverUrl+'test?access_token='+$rootScope.settings.auth.access_token,removeCircle($scope.test)).then(
          function(response){
            console.log("preslo to");
            $scope.test = parseTestForEdit(response.data);
            console.log(JSON.stringify($scope.test));
            $routeParams.testId = $scope.test.id;
            console.log($scope.test);
            console.log($routeParams);
          },
          function(){
            console.log("Connecting problem!");
          }
        );
      }
      else{
        //update
        $http.put($rootScope.serverUrl+'test/',removeCircle($scope.test)).then(
          function(response){
            $scope.test = parseTestForEdit(response.data);
          },
          function(){
            console.log("Connecting problem!");
          }
        );
      }
    };
    $("#visible").bootstrapSwitch();
    $("#visible").on('switchChange.bootstrapSwitch', function(event, state) {
      $scope.test.visible = state;
    });
    $scope.temporary = function(){ console.log($scope.test);};
    //codemirror
    $scope.initCodeQuestion = function(){
      $scope.test.addNewQuestion('code');
      var question = $scope.test.questions[$scope.test.questions.length-1];
      question.cmOption = {
        lineNumbers: true,
        theme: 'dracula',
        indentWithTabs: true,
        mode: '',
        onLoad: function (_cm) {
          question.modeChanged = function (language) {
            _cm.setOption("mode", language);
          };
        }
      }
    }
  }])
  .controller('questionsControlController',['$scope','$rootScope','$http', function ($scope,$rootScope,$http) {
    $scope.setEditTag = function(obj){
      $rootScope.communicator.addSlowRequest(obj,'question/','PUT');
    };

    $scope.categories = [];
    //Na počiatku ziskam kategorie
    $http.get($rootScope.serverUrl+'admin/categories?access_token='+$rootScope.settings.auth.access_token).then(
      function(response){
        for(x in response.data)
          $scope.categories.push(new Category($scope.categories,response.data[x].id,response.data[x].category_name));
      },
      function(){
        console.log("Connecting problem!");
      }
    );
    //na počiatku prazdne pole otazok, neskor cez getQuestions dostavam podla kategorie
    $scope.questions = [];
    $scope.getQuestions = function(){
      $rootScope.communicator.sendData();
      if($scope.selectedCategory != null)
        $http.get($rootScope.serverUrl+'admin/questions/category/' + $scope.selectedCategory.category_id+"?access_token="+$rootScope.settings.auth.access_token).then(
          function(response){
            $scope.questions = parseQuestions(response.data);
            for(x in $scope.questions)
              if($scope.questions[x].language == undefined || $scope.questions[x].language == null) {
                $scope.questions[x].cmOption = {
                  lineNumbers: true,
                  theme:'dracula',
                  indentWithTabs: true,
                  mode: '',
                  onLoad : function(_cm){
                    $scope.questions[x].modeChanged = function(){
                      console.log(this.language);
                      _cm.setOption("mode", this.language);
                    };
                  }
                };
              }
              else{
                $scope.questions[x].cmOption = {
                  lineNumbers: true,
                  theme:'dracula',
                  indentWithTabs: true,
                  mode: $scope.questions[x].language,
                  onLoad : function(_cm){
                    $scope.questions[x].modeChanged = function(){
                      console.log(this.language);
                      _cm.setOption("mode", this.language);
                    };
                  }
                };
              }
          },
          function(){
            console.log("Connecting problem!");
          }
        );
      else $scope.questions = [];
    };
    $scope.selectedFilter = function(item){
      if(item.selected == true) return true;
      else return false;
    };
    $scope.addQuestion = function(type){
      var newQuestion;
      if(type === 'checkbox'){
        newQuestion = new Question($scope.questions,null,$scope.selectedCategory.category_id,'checkbox','',1,'','','',[]);
      }else if(type === 'combobox'){
        newQuestion = new Question($scope.questions,null,$scope.selectedCategory.category_id,'combobox','',1,'','','',[]);
      }else if(type === 'text'){
        newQuestion = new Question($scope.questions,null,$scope.selectedCategory.category_id,'text','',1,'','','',[]);
      }else if (type === 'code'){
        newQuestion = new Question($scope.questions,null,$scope.selectedCategory.category_id,'code','',1,'','','',[]);

      }
      newQuestion.selected = true;
      console.log(newQuestion);
      $scope.questions.push(newQuestion);
      $rootScope.communicator.addSlowRequest(newQuestion,'question/','POST');
    };
    $scope.initCodeQuestion = function(){
      $scope.addQuestion('code');
      var question = $scope.questions[$scope.questions.length-1];
      console.log(question);
      question.cmOption = {
        lineNumbers: true,
        theme: 'dracula',
        indentWithTabs: true,
        mode: '',
        onLoad: function (_cm) {
          question.modeChanged = function (language) {
            _cm.setOption("mode", language);
          };
        }
      }
    };
    $scope.addCategory = function(){
      $http.post($rootScope.serverUrl+'category?access_token='+$rootScope.settings.auth.access_token, {category_name:$scope.selectedCategory.category_name}).then(
        function(response){
          var cat = new Category($scope.categories,response.data.id,response.data.category_name);
          $scope.categories.push(cat);
          $scope.selectedCategory = cat;

        },
        function(){
          console.log("Connecting problem!");
        }
      );
    };

    $scope.removeCategory = function(){
      console.log($rootScope.serverUrl+'category/'+$scope.selectedCategory.category_id);
      $http.delete($rootScope.serverUrl+'category/'+$scope.selectedCategory.category_id).then(
        function(data){
          console.log(data);
          $scope.selectedCategory.removeCategory();
          delete $scope.slectedCategory;
        },
        function(data){
          //TODO: WTF, preco to vyhadzuje error ani krstny nevie
          $scope.selectedCategory.removeCategory();
          delete $scope.slectedCategory;
          console.log("Connecting problem!");
          console.log(data);
        }
      );
    };
    $scope.copyCat = function(){
      $scope.selectedCategory = {category_id:$scope.selectedCategory.category_id,category_name:$scope.selectedCategory.category_name};
    };
    $scope.editCategory = function(){
      console.log($scope.selectedCategory);
      $http.put($rootScope.serverUrl+'category/',{id:$scope.selectedCategory.category_id,category_name:$scope.selectedCategory.category_name}).then(
        function(response){
          console.log(response.data);
          for(x in $scope.categories){
            console.log($scope.categories[x].category_id +' vs '+ response.data.id)
            if($scope.categories[x].category_id == response.data.id){
              $scope.categories[x].category_name = response.data.category_name;
              break;
            }
          }
        },
        function(data){
          console.log("Connecting problem!");
        }
      );
    };

    $scope.showme = function(){
      console.log($scope);
    };

  }])
  .controller('questionController',['$scope','$rootScope','$routeParams','$window', function ($scope,$rootScope,$routeParams,$window) {
    $scope.googleTime = 0;
    $scope.leaveTime = 0;
    $scope.startTime = new Date().getTime();
    $scope.question = $rootScope.currentTest.questions[$routeParams.questionId];
    $scope.questionId = parseInt($routeParams.questionId);
    $scope.question = new Question(null,1,1,'code','toto',3,'kod',null,'javascript',[]);
    //Codemirror configuration
    if($scope.question.type == 'code')
      $scope.cmOption = {
        lineNumbers: true,
        theme: 'dracula',
        indentWithTabs: true,
        mode: $scope.question.language
      };

    //Time counting
    if(!$scope.question.totalTime) $scope.question.totalTime = 0;
    if(!$scope.question.googleTime) $scope.question.googleTime = 0;
    if(($scope.question.type == 'combobox' || $scope.question.type == 'checkbox') && $scope.question.totalTime == 0){
      console.log("mazem");
      for(x in $scope.question.options)
        $scope.question.options[x].truly = false;
    }

    $scope.$on('$locationChangeStart', function( event ) {
      $scope.question.totalTime += new Date().getTime() - $scope.startTime;
      $scope.question.googleTime += $scope.googleTime;
      $(document).unbind( "mouseenter" );
      $(document).unbind( "mouseleave" );
    });

    $(document)
      .mouseenter(function() {
        $scope.googleTime += new Date().getTime() - $scope.leaveTime;
        if($scope.googleTime > 999999999) $scope.googleTime = 0; //ked pri zacati otazky bola myska mimo obrazovky
        console.log(new Date().getTime() - $scope.leaveTime);
        console.log($scope.googleTime);
      })
      .mouseleave(function() {
        $scope.leaveTime = new Date().getTime();
      });


    $scope.isLast = function(){
      return $routeParams.questionId == $rootScope.currentTest.questions.length-1;
    };
    $scope.isFirst = function(){
      return $routeParams.questionId == 0;

    };
    $scope.showScopes = function(){
      console.log($scope);
      console.log($rootScope);
    };



  }])
  .controller('testController',['$scope','$rootScope', '$routeParams', '$http', '$window', function ($scope,$rootScope,$routeParams,$http,$window) {
    $rootScope.currentTest = null;
    $http.get($rootScope.serverUrl+'test/'+$routeParams.testId).then(
      function(response){
        $rootScope.currentTest = response.data;
      },
      function(){
        console.log("Connecting problem!");
      }
    );
    $scope.getQuestion = function(){
      $rootScope.currentTest.candidate = {
        firstName: $scope.name,
        lastName: $scope.surname,
        email: $scope.email,
        testName: $rootScope.currentTest.name,
        date: moment().format("DD-MM-YYYY"),
        totalTime: 123,
        numberOfQuestions: $rootScope.currentTest.questions.length
      };
      $window.location.href = '#/question/0';
    };

  }])
  .controller('usersController',['$scope','$rootScope','$http', function ($scope,$rootScope,$http) {
    $http.get($rootScope.serverUrl+'admin/users?access_token='+$rootScope.settings.auth.access_token).then(
      function(response){
        $rootScope.users = response.data;
      },
      function(){
        console.log("Connecting problem!");
      }
    );
    $scope.showEditModal = function(user){
      $('#detailModal').modal('show');
      $scope.action = 'edit';
      $scope.info = user;
      $scope.info.old_password = $scope.info.password;
    };
    $scope.showCreateModal = function(){
      $('#detailModal').modal('show');
      $scope.action = 'create';
      $scope.info = {
        user_id : null,
        old_password : null,
        email: "email@email.com",
        enabled: true,
        user_name: "new user",
        password: "",
        user_role: ["ROLE_ADMIN"]
      };
    };

    $scope.hideModal = function(){
      $('#detailModal').modal('hide');
    };
    $scope.editUser = function(user){
      if(user.password.length > 0)
        user.password = hex_md5(user.password);
      else
        user.password = user.old_password;
      $http.put($rootScope.serverUrl+'admin/user?access_token='+$rootScope.settings.auth.access_token,user).then(
        function(response){
          user = response.data;
          console.log("data edited :-*");
          $('#detailModal').modal('hide');
        },
        function(){
          console.log("Connecting problem!");
        }
      );
    };
    $scope.createUser = function(user){
      console.log(user);
      user.password = hex_md5(user.password);
      $http.post($rootScope.serverUrl+'admin/user?access_token='+$rootScope.settings.auth.access_token,user).then(
        function(response){
          console.log("vyšlo to");
          console.log(response.data);
          $scope.users.push(response.data);
          $('#detailModal').modal('hide');
        },
        function(){
          console.log("Connecting problem!");
        }
      );
    };
    $scope.deleteUser = function(user){
      console.log($rootScope.serverUrl+'admin/user/'+user.user_id+'?access_token='+$rootScope.settings.auth.access_token);
      $http.delete($rootScope.serverUrl+'admin/user/'+user.user_id+'?access_token='+$rootScope.settings.auth.access_token).then(
        function(){
          $scope.users.splice($scope.users.indexOf(user),1);
        },
        function(){
          console.log("Connecting problem!");
        }
      );
    };
  }])
  .controller('finishController',['$scope','$rootScope', function ($scope,$rootScope) {
    $rootScope.currentTest.candidate.totalTime = 0;
    for(x in $rootScope.currentTest.questions){
      $rootScope.currentTest.candidate.totalTime += $rootScope.currentTest.questions[x].totalTime;

    }
    console.log($rootScope.currentTest);
  }])
  .controller('loginModalController',['$scope','$rootScope', function ($scope,$rootScope) {
    $scope.enter = function(e){
      if(e.keyCode == 13) $rootScope.login();
    }
  }])
  .controller('settingController',['$scope','$rootScope','$sce', function ($scope,$rootScope,$sce) {


  }])
  .controller('testsControlController',['$scope','$rootScope','$http', function ($scope,$rootScope,$http) {
    $scope.tests = [];
    $http.get($rootScope.serverUrl+'mytests/'+$rootScope.settings.user.id+"?access_token="+$rootScope.settings.auth.access_token).then(
      function(response){
        $scope.tests = response.data;
      },
      function(){
        console.log("Connecting problem!");
      }
    );
    $scope.removeTest = function(test){
      $http.delete($rootScope.serverUrl+'test/'+test.id+"?access_token="+$rootScope.settings.auth.access_token).then(
        function(response){
          $scope.tests.splice($scope.tests.indexOf(test),1);
        },
        function(){
          console.log("Connecting problem!");
        }
      );
    };
    $scope.activeTests = function(){
      var number = 0;
      for(test in $scope.tests)
        if($scope.tests[test].visible == true) number ++;
      return number;
    }
  }])
  .controller('resultsController',['$scope', function ($scope) {
    $scope.results = [{
      name: "Alfred",
      surname: "Stan",
      email: "alfred.stan@hotmail.com",
      testName: "Senior Java Dev",
      date: new Date(),
      ttime: "1Hour 54minutes 4seconds",
      result: 56.2,
      showModal : function(){
        $('#detailModal').modal('show');
        $scope.info = this;

      }
    },{
      name: "Adam",
      surname: "Lipka",
      email: "madamek@centrum.sk",
      testName: "Window cleaner",
      date: new Date(),
      ttime: "1Hour 54minutes 4seconds",
      result: 100,
      showModal : function(){
        $('#detailModal').modal('show');
        $scope.info = this;

      }
    },{
      name: "Jiži",
      surname: "Novák",
      email: "pokemon64@azet.sk",
      testName: "Junior Java Dev",
      date: new Date(),
      ttime: "1Hour 54minutes 4seconds",
      result: 68.2,
      showModal : function(){
        $('#detailModal').modal('show');
        $scope.info = this;

      }
    }
    ];

    $scope.hideModal = function(){
      $('#detailModal').modal('hide');
    };
    $scope.exportToPDF = function(){}; //TODO: result to PDF
  }])
  .config(function($routeProvider, $locationProvider) {
    $routeProvider
      .when('/home/', {
        templateUrl: 'partials/home.html',
        controller: 'homeController'
      })
      .when('/controlPanel', {
        templateUrl: 'partials/controlPanel.html',
        controller: 'controlPanelController'
      })
      .when('/testsControl', {
        templateUrl: 'partials/testsControl.html',
        controller: 'testsControlController'
      })
      .when('/settings', {
        templateUrl: 'partials/settings.html',
        controller: 'settingController'
      })
      .when('/results', {
        templateUrl: 'partials/results.html',
        controller: 'resultsController'
      })
      .when('/editTest/:testId', {
        templateUrl: 'partials/editTest.html',
        controller: 'editTestController'
      })
      .when('/testControl', {
        templateUrl: 'partials/testControl.html',
        controller: 'testControlController'
      })
      .when('/questionsControl', {
        templateUrl: 'partials/questionsControl.html',
        controller: 'questionsControlController'
      })
      .when('/test/:testId', {
        templateUrl: 'partials/test.html',
        controller: 'testController'
      })
      .when('/question/:questionId', {
        templateUrl: 'partials/question.html',
        controller: 'questionController'
      })
      .when('/finish', {
        templateUrl: 'partials/finish.html',
        controller: 'finishController'
      })
      .when('/users', {
        templateUrl: 'partials/users.html',
        controller: 'usersController'
      })
      .otherwise({
      redirectTo: '/home/'
    });

  });




function updateObject(item, update){
  for (var prop in update) {
    if (update.hasOwnProperty(prop)) {
      item[prop] = update[prop];
    }
  }
}

function Communicator($http,url){
  this.url = url;
  this.http = $http;
  this.fastQueue = [];
  this.slowQueue = [];
  this.blocked = false;
  this.slowSending = false;
}
/*Communicator.prototype.addFastRequest = function(request){
 this.fastQueue.push(request);
 this.fastSend();
 };*/
Communicator.prototype.addFastRequest = function(data, postfix, method){
  this.fastQueue.push(new Request(data,postfix,method));
  this.fastSend();
};

Communicator.prototype.fastSend = function(){
  communicator = this;
  if(this.fastQueue.length > 0 && this.blocked == false){
    this.blocked = true;
    var item = this.fastQueue.pop();
    console.log(communicator.url + item.postfix+ item.action());
    this.http({
      method: item.action(),
      url: communicator.url + item.postfix+"?access_token="+$rootScope.settings.auth.access_token,
      data: item.data
    }).then(function successCallback(response) {
      item.onSuccess(response);

      communicator.blocked = false;
      if(communicator.slowSending == true) communicator.sendData();
      else communicator.fastSend();
    }, function errorCallback(response) {
      console.log("Capitan communicator reported error :O");
    });
  }

};

/*Communicator.prototype.addSlowRequest = function(request){
 this.slowQueue.push(request);
 };*/
Communicator.prototype.addSlowRequest = function(data, postfix, method){
  console.log(this.slowQueue.length);
  if(method == 'DELETE' && data.id == null){
    for(x in this.slowQueue)
      if(this.slowQueue[x].data.id == data.id)
        this.slowQueue.splice(x,1);
  }else if(method == 'DELETE' && data.id != null){
    for(x in this.slowQueue)
      if(this.slowQueue[x].data.id == data.id)
        this.slowQueue.splice(x,1);
    this.slowQueue.push(new Request(data,postfix,method));
  }else if(method == 'PUT' && data.id == null){
    //nic lebo ho este len ide vytvarat
  }else if(method == 'PUT' && data.id != null && data.inQueue == true){
    //nic lebo ho este len ide vytvarat
  }else if(method == 'PUT' && data.id != null && data.inQueue != true){
    data.inQueue = true;
    this.slowQueue.push(new Request(data,postfix,method));
  }else if(method == 'POST' && data.id == null){
    //vytvaranie
    this.slowQueue.push(new Request(data,postfix,method));
  }
  console.log(this.slowQueue.length);
};

Communicator.prototype.sendData = function(){
  this.slowSending = true;
  communicator = this;
  if((this.slowQueue.length > 0 || this.fastQueue.length > 0) && this.blocked == false){
    this.blocked = true;
    var item;
    if(this.fastQueue.length > 0) item = this.fastQueue.pop();
    else item = this.slowQueue.pop();
    //delete item.data.collection;
    if(item.method == 'DELETE'){
      console.log(communicator.url + item.postfix + item.data.id);
      this.http({
        method: 'DELETE',
        url: communicator.url + item.postfix + item.data.id+"?access_token="+$rootScope.settings.auth.access_token
      }).then(function () {
        communicator.blocked = false;
        communicator.sendData();
      }, function () {
        communicator.blocked = false;
        communicator.sendData();
      });
    }
    else if(!(item.data.collected == true)){
      removeCircle(item.data);
      this.http({
        method: item.method,
        url: communicator.url + item.postfix+"?access_token="+$rootScope.settings.auth.access_token,
        data: item.data
      }).then(function successCallback(response) {
        console.log(response);
        item.onSuccess(response);
        communicator.blocked = false;
        communicator.sendData();
      }, function errorCallback(response) {
        console.log("Capitan communicator reported error :O");
      });
    }
    else communicator.sendData();
  }
  if(!this.blocked) this.slowSending = false;
};


function Request(objectToSend, postfix, method){
  this.data = objectToSend;
  this.postfix = postfix;
  this.method = method;
}
Request.prototype.action = function(communicator){
  switch(this.method) {
    case 'create':
      return 'POST';
      break;
    case 'edit':
      return 'PUT';
      break;
    case 'delete':
      return 'DELETE';
      break;
    default:
      return null;
  }
};
Request.prototype.onSuccess = function(update){
  switch(this.method) {
    case 'create':
    case 'edit':
      updateObject(this.data, update.data);
      break;
    case 'delete':
      this.data.delete();
      break;
    default:
      return null;
  }
};

function Category(collection, id, name){
  this.collection = collection;
  this.category_id = id;
  this.category_name = name;
}
Category.prototype.removeCategory = function(){
  this.collection.splice(this.collection.indexOf(this),1);
};

function Question(collection, id, category_id, type, question, level, code, image_url, language, options){
  this.collection = collection;
  this.id = id;
  this.category_id = category_id;
  this.question = question;
  this.type = type;
  this.level = level;
  this.code = code;
  this.image_url = image_url;
  this.language = language;
  this.options = options;
  for (var i = 0; i < this.options.length; i++) this.options[i].collection = this.options;
}
Question.prototype.removeQuestion = function(item){
  this.collection.splice(this.collection.indexOf(item),1);

};
Question.prototype.removeQuestion = function(item, communicator){
  this.collection.splice(this.collection.indexOf(item),1);

  communicator.addSlowRequest(item,'question/','DELETE');
  console.log(communicator);
};
Question.prototype.addOption = function(option){
  option.collection = this.options;
  this.options.push(option);
};
Question.prototype.addNewOption = function(){
  var newOption = new Option(null,'',false);
  newOption.collection = this.options;
  this.options.push(newOption);
};
Question.prototype.changeCheck = function(){
  if(this.type == 'checkbox')
    for(var x = 0; x < this.options.length; x++){
      this.options[x].truly = false;
    }
};

function Option(id, option, truly){
  this.collection = undefined;
  this.id = id;
  this.option = option;
  this.truly = truly;
}
Option.prototype.removeOption = function(item){
  this.collection.splice(this.collection.indexOf(item),1);
};

function Test(id, name, visible, position, info, user_id){
  this.id = id;
  this.name = name;
  this.visible = visible;
  this.position = position;
  this.info = info;
  this.user_id = user_id;
  this.questions = [];
}
Test.prototype.addQuestion =  function(question){
  this.questions.push(question);
};
Test.prototype.addNewQuestion =  function(question){
  var test = this;
  this.questions.push(new Question(test.questions,undefined,0,question,'',1,undefined,undefined,undefined,[]));
};
Option.prototype.delete = function(){
  if(this.collection) this.collection.splice(this.collection.indexOf(this),1);
};
Question.prototype.delete = function(){
  if(this.collection) this.collection.splice(this.collection.indexOf(this),1);
};
Test.prototype.delete = function(){
  if(this.collection) this.collection.splice(this.collection.indexOf(this),1);
};
Test.prototype.removeQuestion = function(question){
  this.questions.splice(this.questions.indexOf(question),1);
};
Test.prototype.copyQuestion = function(question){
  var options = [];
  for(x in question.options){
    options.push(new Option(null,question.options[x].option,question.options[x].truly));
  }
  this.addQuestion(
    new Question(this.questions,null,question.category_id,question.type,question.question,question.level,question.code,question.image_url,question.language,options)
  );
};

function removeCircle(obj){
  if(obj instanceof Option){
    delete obj.collection;
    return obj;
  }else if(obj instanceof Question){
    delete obj.collection;
    for(x in obj.options){
      obj.collected = true;
      removeCircle(obj.options[x]);
    }
    return obj;
  }else if(obj instanceof Test){
    for(x in obj.questions){
      removeCircle(obj.questions[x]);
    }
    console.log(obj);
    return obj;
  }
}
function parseTestForEdit(input){
  var test = new Test(input.id, input.name, input.visible, input.position, input.info, input.user_id);
  for(var x = 0; x < input.questions.length; x++){
    var options = [];
    for(var y = 0; y < input.questions[x].options.length; y++)
      options.push(new Option(input.questions[x].options[y].id, input.questions[x].options[y].option, input.questions[x].options[y].truly))
    var newQuestion = new Question(
      //collection, id, category_id, type, question, level, code, image_url, language, options
      test.questions,
      input.questions[x].id,
      input.questions[x].category_id,
      input.questions[x].type,
      input.questions[x].question,
      input.questions[x].level,
      input.questions[x].code,
      input.questions[x].image_url,
      input.questions[x].language,
      options
    );
    test.addQuestion(newQuestion);
  }
  return test;
}
function parseQuestions(input){
  var questions = [];
  for(x = 0; x <input.length; x++){
    var options = [];
    for(var y = 0; y < input[x].options.length; y++)
      options.push(new Option(input[x].options[y].id, input[x].options[y].option, input[x].options[y].truly))
    var newQuestion = new Question(
      questions,
      input[x].id,
      input[x].category_id,
      input[x].type,
      input[x].question,
      input[x].level,
      input[x].code,
      input[x].image_url,
      input[x].language,
      options
    );
    questions.push(newQuestion);
  }
  return questions;
}
/*
* JS Legend for dummies
* [] - pole
* {} - objekt
* blabla: - parameter objektu bla bla
* */

/*getTests - vracia iba public testy .../getTests/*/
var x = [{ //pole objektov
    id:1,
    name:"Test for Java Senior Dev",
    position:"Senior JavaDev",
    info:"Bla bla bla bla"
  },{
    id:2,
    name:"Test for ASP.NET Senior Dev",
    position:"Senior ASP.NET Dev",
    info:"Bla bla bla bla"
  }//...
];

/*getMyTests - vracia iba public testy .../getMyTests/ */
var x = [{ //pole objektov
  id:1,
  name:"Test for Java Senior Dev",
  position:"Senior JavaDev",
  info:"Bla bla bla bla"
},{
  id:2,
  name:"Test for ASP.NET Senior Dev",
  position:"Senior ASP.NET Dev",
  info:"Bla bla bla bla"
}//...
];

/*getTest?id=y - vracia iba public testy*/
var y;
y = {
  id: 1,
  name: "Test for Java Senior Dev",
  position: "Senior JavaDev",
  info: "Bla bla bla bla",
  user_id: 5,
  questions: [
    {
      id:1,
      category_id:1,
      question: 'Check question',
      type: 'checkbox',
      level:3,
      code:'',
      image_url:'',
      language:'java',
      options: [{
        id: 1,
        option: 'option 1'
      }, {
        id: 2,
        option: 'option 2'
      }]
    },
    {
      id:2,
      category_id:2,
      question: 'Combo question',
      type: 'combobox',
      level:2,
      code:'',
      image_url:'',
      language:'java',
      options: [{
        id: 3,
        option: 'option 1'
      }, {
        id: 4,
        option: 'option 2'
      }]
    }

  ]
};
/*processTest?id=y - vracia iba public testy*/
var z = {// este CVčko jak budeme posielať :/
  name:'Jožko',
  surname:'Skúšaný',
  email:'jozko.skusany@mail.com',
  date:'19-10-2015',
  total_time:'8945',//seconds
  result: {
    id: 1,
    name: "Test for Java Senior Dev",
    position: "Senior JavaDev",
    info: "Bla bla bla bla",
    questions: [ //pole objektov
      {//id, test_id, category_id, type, question, level, code, image_url, language
        id:1,
        test_id:1,
        category_id:1,
        question: 'Check question',
        type: 'checkbox',
        level:3,
        code:'',
        image_url:'',
        language:'java',
        time:54,
        google_time:33,
        options: [{
          id: 1,
          option: 'option 1',
          checked: false
        }, {
          id: 2,
          option: 'option 2',
          checked: true
        }],
        result: ''
      },
      {
        id:2,
        test_id:1,
        category_id:2,
        question: 'Combo question',
        type: 'combobox',
        level:2,
        code:'',
        image_url:'',
        language:'java',
        time:54,
        google_time:33,
        options: [{
          id: 3,
          option: 'option 1',
          checked: true
        }, {
          id: 4,
          option: 'option 2',
          checked: false
        }],
        result: ''
      },
      {
        id:3,
        test_id:1,
        category_id:2,
        question: 'dopln dačo',
        type: 'text', //abo code
        level:2,
        code:'',
        image_url:'',
        language:'java',
        time:64,//jak dlho bol na tej otazke
        google_time:33, //jak dlho bol mimo karty
        options: [],
        result: 'toto je vysledok bla bla bla'
      }

    ]
  }
};

/*post, put, delete - podla toho ci test vytvaram, editujem alebo mazem, preto mi netreba v teste flag :)*/
var p = {
  id: 1, //id nebude ked test vytvaram :)
  name: "Test for Java Senior Dev",
  position: "Senior JavaDev",
  info: "Bla bla bla bla",
  questions: [ //pole objektov
  {//id, test_id, category_id, type, question, level, code, image_url, language
    id:1, //idčko bude mat iba ak action (nižšie) nebude create
    category_id:1,
    question: 'Check question',
    type: 'checkbox',
    level:3,
    code:'',
    image_url:'',
    language:'java',
    action:'edit', //edit, create, delete
    options: [{
      id: 1, //idčko bude mat iba ak action (nižšie) nebude create
      option: 'option 1',
      truth: true,
      action:'edit' //edit, create, delete
    }, {
      id: 2, //idčko bude mat iba ak action (nižšie) nebude create
      option: 'option 2',
      truth: false,
      action: 'edit'
    }]
  },
  {
    id:2,//idčko bude mat iba ak action (nižšie) nebude create
    category_id:2,
    question: 'Combo question',
    type: 'combobox',
    level:2,
    code:'',
    image_url:'',
    language:'java',
    action:'edit', //edit, create, delete
    options: [{
      id: 3,//idčko bude mat iba ak action (nižšie) nebude create
      option: 'option 1',
      truth: false,
      action: 'edit'
    }, {
      id: 4,//idčko bude mat iba ak action (nižšie) nebude create
      option: 'option 2',
      truth: true,
      action: 'edit'
    }]
  }

]
};

// Questions klasicky REST - post, get, put, delete - .../questions/[<id>/]
var o = {
  id: 2,
  category_id: 2,
  question: 'Combo question',
  type: 'combobox',
  level: 2,
  code: '',
  image_url: '',
  language: 'java',
  options: [{
    id: 3,
    option: 'option 1',
    truth: false
  }, {
    id: 4,
    option: 'option 2',
    truth: true
  }]
};
// Categories klasicky REST - post, get, put, delete - .../questions/[<id>/]



//TODO: Prepinač visible dorobiť pre testy
angular.module('NESS-TCFA', ['ngCookies','ngAnimate','ngRoute','ngSanitize'])//App init
  .run(['$rootScope', '$http',function($rootScope,$http){
    $rootScope.settings = {
      logged: false
    };
    $rootScope.communicator = new Communicator($http,'http://jsonplaceholder.typicode.com/');
    var a = new Request(
      );

    $rootScope.deleteObjectPls = {
      "userId": 1,
      "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
      "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    };
    //$rootScope.communicator.sendData();


  }])
  .controller('homeController',['$scope','$rootScope','$window', function ($scope,$rootScope,$window) {//homeController
    /*$scope.$on('$locationChangeStart', function( event ) {
      var answer = confirm("Are you sure you want to leave this page?")
      if (!answer) {
        event.preventDefault();
      }
    });*/

    $rootScope.login = function(){
      if(true){//TODO: Authentication
        $rootScope.settings.user = {
          name: 'Tony',
          surname: 'Potato'
        };
        $rootScope.settings.logged = true;
        $('#loginModal').modal('hide');
        $window.location.href = '#/controlPanel/';
      }
    };

    $rootScope.logout = function(){//TODO: Logout
      delete $rootScope.settings.user;
      $rootScope.settings.logged = false;
      $window.location.href = '#/home/';
    };


  }])
  .controller('controlPanelController',['$scope','$rootScope', function ($scope,$rootScope) {

  }])
  .controller('editTestController',['$scope','$rootScope', function ($scope,$rootScope) {
    $scope.test ={
      name:'test name',
      info:'here are information about test',
      removeQuestion: function(item){
        this.questions.splice(this.questions.indexOf(item),1);
      },
      questions: [
        {
          question:'Check question',
          type:'checkbox',
          options: [{
            id: 1,
            option: 'option 1',
            truly: true
          },{
            id: 2,
            option: 'option 2',
            truly: true
          }],
          addOption: function(){
            this.options.push({
              id: 4,
              option: 'option 4'
            })
          },
          removeOption: function(){
            this.options.splice(this.options.indexOf(this),1);
          }
        },
        {
          question:'ComboQuestion',
          type:'combobox',
          options: [{
            id: 1,
            option: 'option 1'
          },{
            id: 2,
            option: 'option 2'
          },{
            id: 3,
            option: 'option 3'
          }],
          addOption: function(){
            this.options.push({
              id: 4,
              option: 'option 4'
            })
          },
          removeOption: function(){
            this.options.splice(this.options.indexOf(this),1);
          }
        }

      ]
    };
    console.log($scope);
    $scope.test.addQuestion = function(type){
      //TODO: priradovanie IDčiek
      if(type === 'checkbox'){
        $scope.test.questions.push({
          question:'',
          type:'checkbox',
          options:{}
        })
      }else if(type === 'combobox'){
        $scope.test.questions.push({
          question: '',
          type: 'combobox',
          options: {}
        });
      }else if(type === 'text'){
        $scope.test.questions.push({
          question: '',
          type: 'text'
        });
      }else if (type === 'code'){
        $scope.test.questions.push({
          question: '',
          type: 'code',
          code: "..."
        });
      }
    };
    $scope.test.questions.addOption = function(){
      console.log(this);
      /*this.options.push({//TODO: pridelenie ID
         id: 123,
        question:''
        });*/
    }
    $scope.temporary = function(){ console.log($scope.test);}
  }])
  .controller('questionsControlController',['$scope','$rootScope', function ($scope,$rootScope) {
    $scope.categories = [];
    $scope.categories.push(new Category($scope.categories,1,'General'), new Category($scope.categories,2,'Java'), new Category($scope.categories,3,'.NET'));

    $scope.questions = [];
    $scope.questions.push(new Question($scope.questions,1,1,'checkbox','Question one :P',3,undefined,undefined,undefined,[
      new Option(1,'Option one',true),
      new Option(2,'Option two',true)
    ]));
    $scope.advanceFilter = function(item){
      if($scope.selectedCategory) return item.category_id && $scope.selectedCategory.category_id;
      else return false;
    };
    $scope.selectedFilter = function(item){
      if(item && item.selected == true) return true;
      return false;
    };
    $scope.addQuestion = function(type){
      //TODO: priradovanie IDčiek
      if(type === 'checkbox'){
        $scope.questions.push({
          question:'',
          type:'checkbox',
          options:{}
        })
      }else if(type === 'combobox'){
        $scope.questions.push({
          question: '',
          type: 'combobox',
          options: {}
        });
      }else if(type === 'text'){
        $scope.questions.push({
          question: '',
          type: 'text'
        });
      }else if (type === 'code'){
        $scope.questions.push({
          question: '',
          type: 'code',
          code: "..."
        });
      }
    };
    $scope.addCategory = function(meno){
      $scope.categories.push(new Category($scope, 0, meno));
      $scope.selectedCategory = $scope.categories[$scope.categories.length-1];
      $scope.selectedCategory.action = 'create';
    };
    $scope.showme = function(){
      console.log($scope);
    };


  }])
  .controller('questionController',['$scope','$rootScope', function ($scope,$rootScope) {
    switch (Math.floor(Math.random() * 4)){
      case 0:
            $scope.question = {
              question:'ComboQuestion',
              type:'combobox',
              options: [{
                id: 1,
                option: 'option 1'
              },{
                id: 2,
                option: 'option 2'
              },{
                id: 3,
                option: 'option 3'
              }]
            };
            break;
      case 1:
            $scope.question = {
              question:'CheckQuestion',
              type:'checkbox',
              options: [{
                id: 1,
                option: 'option 1'
              },{
                id: 2,
                option: 'option 2'
              },{
                id: 3,
                option: 'option 3'
              }]
            };
            break;
      case 2:
            $scope.question = {
              question:'TextQuestion',
              type:'text'
            };
            break;
      case 3:
            $scope.question = {
              question:'TextQuestion',
              type:'code',
              code:'Code ....'
            };
            break;
    }

  }])
  .controller('guestController',['$scope','$rootScope', function ($scope,$rootScope) {

  }])
  .controller('usersController',['$scope','$rootScope', function ($scope,$rootScope) {
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
      $('#settingsModal').modal('hide');
      $('#permissionsModal').modal('hide');
    };
    $scope.savePermissions = function(){}; //TODO: save Permissions
    $scope.savePassword = function(){}; //TODO: save Password
    $scope.createUser = function(){}; //TODO: create User
    $scope.deleteUser = function(){};
  }])
  .controller('finishController',['$scope','$rootScope', function ($scope,$rootScope) {

  }])
  .controller('loginModalController',['$scope','$rootScope', function ($scope,$rootScope) {

  }])
  .controller('settingController',['$scope','$rootScope','$sce', function ($scope,$rootScope,$sce) {


  }])
  .controller('testsControlController',['$scope', function ($scope) {

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
      .when('/editTest', {
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
      .when('/guest', {
        templateUrl: 'partials/guest.html',
        controller: 'guestController'
      })
      .when('/question', {
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
    console.log(communicator.url + item.postfix);
    this.http({
      method: item.method,
      url: communicator.url + item.postfix,
      data: item.data
    }).then(function successCallback(response) {
      console.log(response);
      communicator.blocked = false;
      if(communicator.slowSending == true) communicator.sendData();
      else communicator.fastSend();
    }, function errorCallback(response) {
      console.log("Capitan communicator report error :O");
    });
  }

};

/*Communicator.prototype.addSlowRequest = function(request){
 this.slowQueue.push(request);
 };*/
Communicator.prototype.addSlowRequest = function(data, postfix, method){
  this.slowQueue.push(new Request(data,postfix,method));
};

Communicator.prototype.sendData = function(){
  this.slowSending = true;
  communicator = this;
  if((this.slowQueue.length > 0 || this.fastQueue.length > 0) && this.blocked == false){
    this.blocked = true;
    var item;
    if(this.fastQueue.length > 0) item = this.fastQueue.pop();
    else item = this.slowQueue.pop();
    console.log(communicator.url + item.postfix);
    this.http({
      method: item.method,
      url: communicator.url + item.postfix,
      data: item.data
    }).then(function successCallback(response) {
      console.log(response);
      communicator.blocked = false;
      communicator.sendData();
    }, function errorCallback(response) {
      console.log("Capitan communicator report error :O");
    });
  }
  if(!this.blocked) this.slowSending = false;
};


function Request(objectToSend, postfix, method){
  this.data = objectToSend;
  this.postfix = postfix;
  this.method = method;
}

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
  console.log(options);
  console.log(this);
  for (var i = 0; i < this.options.length; i++) this.options[i].collection = this.options;
}
Question.prototype.removeQuestion = function(item){
  this.collection.splice(this.collection.indexOf(item),1);
};
Question.prototype.addOption = function(option){
  option.collection = this.collection;
  this.collection.push(option);
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
Option.prototype.removeQuestion = function(item){
  this.collection.splice(this.collection.indexOf(item),1);
};
function Test(id, name, position, info, user_id){
  this.id = id;
  this.name = name;
  this.position = position;
  this.info = info;
  this.user_id = user_id;
  this.questions = [];
}
Test.prototype.addQuestion =  function(question){
  this.questions.push(question);
};


function parseTestForEdit(input){
  var test = new Test(input.id, input.name, input.position, input.info, input.user_id);
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
  id: 2,//idčko bude mat iba ak action (nižšie) nebude create
  category_id: 2,
  question: 'Combo question',
  type: 'combobox',
  level: 2,
  code: '',
  image_url: '',
  language: 'java',
  options: [{//ak bude type code alebo text tak toto pole bude prazdne tj. []
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
};
// Categories klasicky REST - post, get, put, delete - .../questions/[<id>/]



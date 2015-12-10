angular.module('NESS-TCFA', ['ngCookies','ngAnimate','ngRoute','ui.codemirror'])//App init
  .run(['$rootScope', '$http','$window',function($rootScope, $http, $window){
    $rootScope.setHeight = function(){
      $('background').css("min-height",$( window ).height());
      $('#appContainer').css("min-height",$( window ).height() - 400);

    };
    $( window ).resize(function() {
      $('background').css("min-height",$( window ).height());
      $('#appContainer').css("min-height",$( window ).height() - 400);
    });
    $rootScope.serverUrl = 'http://localhost:8080/rest/';
    $rootScope.settings = {
      logged: false,
      autentificationFailed: false
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
          $rootScope.settings.autentificationFailed = false;
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
                $rootScope.connProblem.problem = true;
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
              //pretoze expire token je niekedy mensi nez 300
              $http.get($rootScope.serverUrl+'oauth/token?grant_type=refresh_token&client_id=ness-candidates-review&refresh_token='+$rootScope.settings.auth.refresh_token).then(
                function(response){
                  $rootScope.settings.auth = response.data;
                  console.log(response.data);

                },
                function(){
                  console.log("Connecting problem!");
                  $rootScope.connProblem.problem = true;
                }
              );
            },
            function(){
              console.log("Connecting problem!");
              $rootScope.connProblem.problem = true;
            }
          );
          $rootScope.settings.logged = true;
          $('#loginModal').modal('hide');
          $window.location.href = '#/controlPanel/';
        },
        function(){
          console.log("Connecting problem!");
          //$rootScope.connProblem.problem = true;
          $rootScope.settings.autentificationFailed = true;
        }
      );


      /*if(true){//TODO: Authentication
        $rootScope.settings.user = {
          id:1,
          name: 'Tony',
          surname: 'Potato'
        };
        $rootScope.settings.logged = true;
        $('#loginModal').modal('hide');
        $window.location.href = '#/controlPanel/';
      }*/
    };
    //$rootScope.login('admin','admin');
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
    $rootScope.communicator = new Communicator($http,$rootScope.serverUrl,$rootScope);
    //Playground



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

function Communicator($http,url,rootScope){
  this.url = url;
  this.http = $http;
  this.rootScope = rootScope;
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
        url: communicator.url + item.postfix + item.data.id+"?access_token="+this.rootScope.settings.auth.access_token
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
        url: communicator.url + item.postfix+"?access_token="+this.rootScope.settings.auth.access_token,
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

  communicator.addSlowRequest(item,'admin/question/','DELETE');
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
Question.prototype.changeCheck = function(option){
  console.log(option);
  if(this.type == 'combobox')
    for(x in this.options){
      if(this.options[x].id != option.id) this.options[x].truly = false;
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
  this.questions.push(new Question(test.questions,undefined,null,question,'',1,undefined,undefined,undefined,[]));
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

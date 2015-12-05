/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('editTestController',['$scope','$rootScope','$routeParams','$http', function ($scope,$rootScope,$routeParams,$http) {
  $scope.categories = [];
  $scope.categoryQuestions = [];
  $http.get($rootScope.serverUrl+'admin/categories?access_token='+$rootScope.settings.auth.access_token).then(
    function(response){
      $scope.categories = response.data;
    },
    function(){
      console.log("Connecting problem!");
      $rootScope.connProblem.problem = true;
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
        $rootScope.connProblem.problem = true;
      }
    );
  }
  $scope.changeCategory = function(){
    if($scope.selectedCategory){
      //questions/category/1
      $http.get($rootScope.serverUrl+'admin/questions/category/' + $scope.selectedCategory.id+"?access_token="+$rootScope.settings.auth.access_token).then(
        function(response){
          $scope.categoryQuestions = response.data;
          console.log(response.data);
        },
        function(){
          console.log("Connecting problem!");
          $rootScope.connProblem.problem = true;
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
      $http.post($rootScope.serverUrl+'admin/test?access_token='+$rootScope.settings.auth.access_token,removeCircle($scope.test)).then(
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
          $rootScope.connProblem.problem = true;
        }
      );
    }
    else{
      //update
      removeCircle($scope.test);
      console.log($scope.test);
      $http.put($rootScope.serverUrl+'admin/test?access_token='+$rootScope.settings.auth.access_token,removeCircle($scope.test)).then(
        function(response){
          $scope.test = parseTestForEdit(response.data);
        },
        function(){
          console.log("Connecting problem!");
          $rootScope.connProblem.problem = true;
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

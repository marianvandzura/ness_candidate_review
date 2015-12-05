/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('testController',['$scope','$rootScope', '$routeParams', '$http', '$window', function ($scope,$rootScope,$routeParams,$http,$window) {
  $rootScope.currentTest = null;
  $http.get($rootScope.serverUrl+'test/'+$routeParams.testId).then(
    function(response){
      $rootScope.currentTest = response.data;
    },
    function(){
      console.log("Connecting problem!");
      $rootScope.connProblem.problem = true;
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

}]);

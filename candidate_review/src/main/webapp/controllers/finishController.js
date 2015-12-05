/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('finishController',['$scope','$rootScope', function ($scope,$rootScope) {
  $rootScope.currentTest.candidate.totalTime = 0;
  for(x in $rootScope.currentTest.questions){
    $rootScope.currentTest.candidate.totalTime += $rootScope.currentTest.questions[x].totalTime;

  }
  console.log($rootScope.currentTest);
}]);

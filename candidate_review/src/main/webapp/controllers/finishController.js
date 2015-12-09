/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('finishController',['$scope','$rootScope','$window', function ($scope,$rootScope,$window) {
  $rootScope.currentTest.candidate.totalTime = 0;
  for(x in $rootScope.currentTest.questions){
    $rootScope.currentTest.candidate.totalTime += $rootScope.currentTest.questions[x].totalTime;

  }
  $http.post($rootScope.serverUrl+'admin/report?access_token='+$rootScope.settings.auth.access_token,$rootScope.currentTest).then(
      function(response){
        setTimeout(function(){
            $window.location.href = '#/home';
        }, 5000);
      },
      function(){
        console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
      }
  );
}]);

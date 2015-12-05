/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('testsControlController',['$scope','$rootScope','$http', function ($scope,$rootScope,$http) {
  $scope.tests = [];
  $http.get($rootScope.serverUrl+'admin/mytests/'+$rootScope.settings.user.id+"?access_token="+$rootScope.settings.auth.access_token).then(
    function(response){
      $scope.tests = response.data;
    },
    function(){

      console.log("Connecting problem!");
      $rootScope.connProblem.problem = true;
    }
  );
  $scope.removeTest = function(test){
    $http.delete($rootScope.serverUrl+'test/'+test.id+"?access_token="+$rootScope.settings.auth.access_token).then(
      function(response){
        $scope.tests.splice($scope.tests.indexOf(test),1);
      },
      function(){
        console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
      }
    );
  };
  $scope.activeTests = function(){
    var number = 0;
    for(test in $scope.tests)
      if($scope.tests[test].visible == true) number ++;
    return number;
  }
}]);

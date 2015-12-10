/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('testsControlController',['$scope','$rootScope','$http','$window', function ($scope,$rootScope,$http,$window) {
  $scope.tests = [];
  $http.get($rootScope.serverUrl+'admin/mytests/'+$rootScope.settings.user.id+"?access_token="+$rootScope.settings.auth.access_token).then(
    function(response){
      $scope.tests = response.data;
    },
    function(){

      //DEBUG-console.log("Connecting problem!");
      $rootScope.connProblem.problem = true;
    }
  );
  $scope.deleting = false;
  $scope.removeTest = function(test){
   if($scope.deleting == false){
     $scope.deleting = true;
     $http.delete($rootScope.serverUrl+'admin/test/'+test.id+"?access_token="+$rootScope.settings.auth.access_token).then(
         function(response){
           $scope.deleting = false;
           $scope.tests.splice($scope.tests.indexOf(test),1);
         },
         function(){
           $scope.deleting = false;
         }
     );
   }
  };
  $scope.$on('$locationChangeStart', function( event ) {
     if ($scope.deleting == true) {
         event.preventDefault();
     }
   });
  $scope.activeTests = function(){
    var number = 0;
    for(test in $scope.tests)
      if($scope.tests[test].visible == true) number ++;
    return number;
  }
}]);

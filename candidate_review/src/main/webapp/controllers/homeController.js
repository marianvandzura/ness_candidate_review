/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('homeController',['$scope','$rootScope','$window', '$http', function ($scope,$rootScope, $window, $http) {
  $http.get($rootScope.serverUrl+'tests/').then(
    function(response){
      $scope.tests = response.data;
      //DEBUG-console.log(response.data);
    },
    function(){
      //DEBUG-console.log("Connecting problem!");
      $rootScope.connProblem.problem = true;
    }
  );

  //$('#appContainer').css('min-height') = $($0).css('height') - 301;
  /*$scope.$on('$locationChangeStart', function( event ) {
   var answer = confirm("Are you sure you want to leave this page?")
   if (!answer) {
   event.preventDefault();
   }
   });*/
}]);

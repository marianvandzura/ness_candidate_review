/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('loginModalController',['$scope','$rootScope', function ($scope,$rootScope) {
  $scope.enter = function(e){
    if(e.keyCode == 13) $rootScope.login($scope.email,$scope.password);
  }
}]);

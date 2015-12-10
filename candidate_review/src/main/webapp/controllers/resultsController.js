/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('resultsController',['$scope','$http','$rootScope', function ($scope, $http, $rootScope) {
  $scope.temp = {};
  $http.get($rootScope.serverUrl+'admin/report/getreports?access_token='+$rootScope.settings.auth.access_token).then(
      function(response){
        $scope.results = response.data;
        //DEBUG-console.log($scope.results);
        for(x in $scope.results.date)
          $scope.results.date = new Date($scope.results.date);
        //DEBUG-console.log($scope.results);

      },
      function(){
        //DEBUG-console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
      }
  );
  $scope.showModal = function(res){
    $scope.info = res;
    //DEBUG-console.log(res);
    $('#detailModal').modal('show');
  };

  $scope.hideModal = function(){
    $('#detailModal').modal('hide');
  };
  $scope.getPdf = function(id){
    $http.get($rootScope.serverUrl+'admin/report/download/'+ id +'?access_token='+$rootScope.settings.auth.access_token).then(
        function(response){
          window.open(response.config.url);
        },
        function(){
          //DEBUG-console.log("Connecting problem!");
          $rootScope.connProblem.problem = true;
        }
    );
  };
}]);

/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('resultsController',['$scope','$http','$rootScope', function ($scope, $http, $rootScope) {
  $http.get($rootScope.serverUrl+'admin/reports?access_token='+$rootScope.settings.auth.access_token).then(
      function(response){
        $scope.results = response.data;
        console.log(results);
      },
      function(){
        console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
      }
  );
  /*$scope.results = [{
    name: "Alfred",
    surname: "Stan",
    email: "alfred.stan@hotmail.com",
    testName: "Senior Java Dev",
    date: new Date(),
    ttime: "1Hour 54minutes 4seconds",
    result: 56.2,
    showModal : function(){
      $('#detailModal').modal('show');
      $scope.info = this;

    }
  },{
    name: "Adam",
    surname: "Lipka",
    email: "madamek@centrum.sk",
    testName: "Window cleaner",
    date: new Date(),
    ttime: "1Hour 54minutes 4seconds",
    result: 100,
    showModal : function(){
      $('#detailModal').modal('show');
      $scope.info = this;

    }
  },{
    name: "Jiži",
    surname: "Novák",
    email: "pokemon64@azet.sk",
    testName: "Junior Java Dev",
    date: new Date(),
    ttime: "1Hour 54minutes 4seconds",
    result: 68.2,
    showModal : function(){
      $('#detailModal').modal('show');
      $scope.info = this;

    }
  }
  ];*/

  $scope.hideModal = function(){
    $('#detailModal').modal('hide');
  };
  $scope.getPdf = function(id){
    $http.get($rootScope.serverUrl+'admin/getPdf/'+ id +'?access_token='+$rootScope.settings.auth.access_token).then(
        function(response){
          $scope.results = response.data;
          console.log(results);
        },
        function(){
          console.log("Connecting problem!");
          $rootScope.connProblem.problem = true;
        }
    );
  };
}]);

/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('usersController',['$scope','$rootScope','$http', function ($scope,$rootScope,$http) {
  $http.get($rootScope.serverUrl+'admin/users?access_token='+$rootScope.settings.auth.access_token).then(
    function(response){
      $rootScope.users = response.data;
    },
    function(){
      console.log("Connecting problem!");
      $rootScope.connProblem.problem = true;
    }
  );
  $scope.showEditModal = function(user){
    $('#detailModal').modal('show');
    $scope.action = 'edit';
    $scope.info = user;
    $scope.info.old_password = $scope.info.password;
  };
  $scope.showCreateModal = function(){
    $('#detailModal').modal('show');
    $scope.action = 'create';
    $scope.info = {
      user_id : null,
      old_password : null,
      email: "email@email.com",
      enabled: true,
      user_name: "new user",
      password: "",
      user_role: ["ROLE_ADMIN"]
    };
  };

  $scope.hideModal = function(){
    $('#detailModal').modal('hide');
  };
  $scope.editUser = function(user){
    if(user.password.length > 0)
      user.password = hex_md5(user.password);
    else
      user.password = user.old_password;
    $http.put($rootScope.serverUrl+'admin/user?access_token='+$rootScope.settings.auth.access_token,user).then(
      function(response){
        user = response.data;
        console.log("data edited :-*");
        $('#detailModal').modal('hide');
      },
      function(){
        console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
      }
    );
  };
  $scope.createUser = function(user){
    console.log(user);
    user.password = hex_md5(user.password);
    $http.post($rootScope.serverUrl+'admin/user?access_token='+$rootScope.settings.auth.access_token,user).then(
      function(response){
        console.log("vyšlo to");
        console.log(response.data);
        $scope.users.push(response.data);
        $('#detailModal').modal('hide');
      },
      function(){
        console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
      }
    );
  };
  $scope.deleteUser = function(user){
    console.log($rootScope.serverUrl+'admin/user/'+user.user_id+'?access_token='+$rootScope.settings.auth.access_token);
    $http.delete($rootScope.serverUrl+'admin/user/'+user.user_id+'?access_token='+$rootScope.settings.auth.access_token).then(
      function(){
        $scope.users.splice($scope.users.indexOf(user),1);
      },
      function(){
        console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
      }
    );
  };
}]);

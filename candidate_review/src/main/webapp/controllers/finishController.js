/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('finishController',['$scope','$rootScope','$window','$http', function ($scope,$rootScope,$window,$http) {
  $rootScope.currentTest.candidate.totalTime = 0;
    // petova srandovna magia
    //DEBUG-console.log($rootScope.currentTest);
    $rootScope.currentTest.markedAnswers = {};
  for(x in $rootScope.currentTest.questions){
    $rootScope.currentTest.questions[x].totalTime = $rootScope.currentTest.questions[x].totalTime / 1000;
    $rootScope.currentTest.questions[x].totalTime = parseInt($rootScope.currentTest.questions[x].totalTime);
      $rootScope.currentTest.questions[x].googleTime = $rootScope.currentTest.questions[x].googleTime / 1000;
      $rootScope.currentTest.questions[x].googleTime = parseInt($rootScope.currentTest.questions[x].googleTime);
    $rootScope.currentTest.candidate.totalTime += $rootScope.currentTest.questions[x].totalTime;
      if($rootScope.currentTest.questions[x].type == 'checkbox' || $rootScope.currentTest.questions[x].type == 'combobox'){
          var temp = [];
          for(y in $rootScope.currentTest.questions[x].options){
              if($rootScope.currentTest.questions[x].options[y].truly != true && $rootScope.currentTest.questions[x].options[y].truly != false)
                  $rootScope.currentTest.questions[x].options[y].truly = false;

              if($rootScope.currentTest.questions[x].options[y].truly == true)
                temp.push($rootScope.currentTest.questions[x].options[y].id);
          }
          $rootScope.currentTest.markedAnswers[$rootScope.currentTest.questions[x].id] = temp;
      }
  }

  //DEBUG-console.log($rootScope.currentTest);
  console.log(JSON.stringify($rootScope.currentTest));
  $http.post($rootScope.serverUrl+'report',$rootScope.currentTest).then(
      function(response){
        setTimeout(function(){
            $window.location.href = '#/home';
        }, 5000);
      },
      function(){
        //DEBUG-console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
      }
  );
}]);

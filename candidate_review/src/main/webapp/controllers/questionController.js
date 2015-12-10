/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('questionController',['$scope','$rootScope','$routeParams','$window', function ($scope,$rootScope,$routeParams,$window) {
  $scope.googleTime = 0;
  $scope.leaveTime = 0;
  $scope.startTime = new Date().getTime();
  $scope.question = $rootScope.currentTest.questions[$routeParams.questionId];
  $scope.questionId = parseInt($routeParams.questionId);
  //$scope.question = new Question(null,1,1,'code','toto',3,'kod',null,'javascript',[]);
  //Codemirror configuration
  if($scope.question.type == 'code')
    $scope.cmOption = {
      lineNumbers: true,
      theme: 'dracula',
      indentWithTabs: true,
      mode: $scope.question.language
    };

  //Time counting
  if(!$scope.question.totalTime) $scope.question.totalTime = 0;
  if(!$scope.question.googleTime) $scope.question.googleTime = 0;
  if(($scope.question.type == 'combobox' || $scope.question.type == 'checkbox') && $scope.question.totalTime == 0){
    console.log("mazem");
    for(x in $scope.question.options)
      $scope.question.options[x].truly = false;
  }

  $scope.$on('$locationChangeStart', function( event ) {
    $scope.question.totalTime += new Date().getTime() - $scope.startTime;
    $scope.question.googleTime += $scope.googleTime;
    $(document).unbind( "mouseenter" );
    $(document).unbind( "mouseleave" );
      console.log("tu som");
      if($scope.question.type == 'combobox'){
          console.log("tam som");
          for(x in $scope.question.options){
              if($scope.question.options[x].id != $scope.question.truly){
                  console.log("nasiel som zly");
                  $scope.question.options[x].truly = false;
              }
              else if($scope.question.options[x].id == $scope.question.truly){
                  console.log("nasiel som spravnu");
                  $scope.question.options[x].truly = true;
              }

          }
      delete $scope.question.truly;



      }
  });

  $(document)
    .mouseenter(function() {
      $scope.googleTime += new Date().getTime() - $scope.leaveTime;
      if($scope.googleTime > 999999999) $scope.googleTime = 0; //ked pri zacati otazky bola myska mimo obrazovky
      console.log(new Date().getTime() - $scope.leaveTime);
      console.log($scope.googleTime);
    })
    .mouseleave(function() {
      $scope.leaveTime = new Date().getTime();
    });


  $scope.isLast = function(){
    return $routeParams.questionId == $rootScope.currentTest.questions.length-1;
  };
  $scope.isFirst = function(){
    return $routeParams.questionId == 0;

  };
  $scope.showScopes = function(){
    console.log($scope);
    console.log($rootScope);
  };

}]);

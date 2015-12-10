/**
 * Created by sajaj on 05.12.2015.
 */
angular.module('NESS-TCFA').controller('questionsControlController',['$scope','$rootScope','$http', function ($scope,$rootScope,$http) {
  $scope.setEditTag = function(obj){
    $rootScope.communicator.addSlowRequest(obj,'admin/question/','PUT');
  };

  $scope.categories = [];
  //Na počiatku ziskam kategorie
  $http.get($rootScope.serverUrl+'admin/categories?access_token='+$rootScope.settings.auth.access_token).then(
    function(response){
      for(x in response.data)
        $scope.categories.push(new Category($scope.categories,response.data[x].id,response.data[x].category_name));
    },
    function(){
      //DEBUG-console.log("Connecting problem!");
      $rootScope.connProblem.problem = true;
    }
  );
  //na počiatku prazdne pole otazok, neskor cez getQuestions dostavam podla kategorie
  $scope.questions = [];
  $scope.getQuestions = function(){
    $rootScope.communicator.sendData();
    if($scope.selectedCategory != null)
      $http.get($rootScope.serverUrl+'admin/questions/category/' + $scope.selectedCategory.category_id+"?access_token="+$rootScope.settings.auth.access_token).then(
        function(response){
          $scope.questions = parseQuestions(response.data);
          for(x in $scope.questions)
            if($scope.questions[x].language == undefined || $scope.questions[x].language == null) {
              $scope.questions[x].cmOption = {
                lineNumbers: true,
                theme:'dracula',
                indentWithTabs: true,
                mode: '',
                onLoad : function(_cm){
                  $scope.questions[x].modeChanged = function(){
                    //DEBUG-console.log(this.language);
                    _cm.setOption("mode", this.language);
                  };
                }
              };
            }
            else{
              $scope.questions[x].cmOption = {
                lineNumbers: true,
                theme:'dracula',
                indentWithTabs: true,
                mode: $scope.questions[x].language,
                onLoad : function(_cm){
                  $scope.questions[x].modeChanged = function(){
                    //DEBUG-console.log(this.language);
                    _cm.setOption("mode", this.language);
                  };
                }
              };
            }
        },
        function(){
          //DEBUG-console.log("Connecting problem!");
          $rootScope.connProblem.problem = true;
        }
      );
    else $scope.questions = [];
  };
  $scope.selectedFilter = function(item){
    if(item.selected == true) return true;
    else return false;
  };
  $scope.addQuestion = function(type){
    var newQuestion;
    if(type === 'checkbox'){
      newQuestion = new Question($scope.questions,null,$scope.selectedCategory.category_id,'checkbox','',1,'','','',[]);
    }else if(type === 'combobox'){
      newQuestion = new Question($scope.questions,null,$scope.selectedCategory.category_id,'combobox','',1,'','','',[]);
    }else if(type === 'text'){
      newQuestion = new Question($scope.questions,null,$scope.selectedCategory.category_id,'text','',1,'','','',[]);
    }else if (type === 'code'){
      newQuestion = new Question($scope.questions,null,$scope.selectedCategory.category_id,'code','',1,'','','',[]);

    }
    newQuestion.selected = true;
    //DEBUG-console.log(newQuestion);
    $scope.questions.push(newQuestion);
    $rootScope.communicator.addSlowRequest(newQuestion,'admin/question/','POST');
  };
  $scope.initCodeQuestion = function(){
    $scope.addQuestion('code');
    var question = $scope.questions[$scope.questions.length-1];
    //DEBUG-console.log(question);
    question.cmOption = {
      lineNumbers: true,
      theme: 'dracula',
      indentWithTabs: true,
      mode: '',
      onLoad: function (_cm) {
        question.modeChanged = function (language) {
          _cm.setOption("mode", language);
        };
      }
    }
  };
  $scope.addCategory = function(){
    $http.post($rootScope.serverUrl+'admin/category?access_token='+$rootScope.settings.auth.access_token, {category_name:$scope.selectedCategory.category_name}).then(
      function(response){
        var cat = new Category($scope.categories,response.data.id,response.data.category_name);
        $scope.categories.push(cat);
        $scope.selectedCategory = cat;

      },
      function(){
        //DEBUG-console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
      }
    );
  };

  $scope.removeCategory = function(){
    //DEBUG-console.log($rootScope.serverUrl+'admin/category/'+$scope.selectedCategory.category_id+'?access_token='+$rootScope.settings.auth.access_token);
    $http.delete($rootScope.serverUrl+'admin/category/'+$scope.selectedCategory.category_id+'?access_token='+$rootScope.settings.auth.access_token).then(
      function(data){
        //DEBUG-console.log(data);
        $scope.selectedCategory.removeCategory();
        delete $scope.slectedCategory;
      },
      function(data){
        //TODO: WTF, preco to vyhadzuje error ani krstny nevie
        $scope.selectedCategory.removeCategory();
        delete $scope.slectedCategory;
        //DEBUG-console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
        //DEBUG-console.log(data);
      }
    );
  };
  $scope.copyCat = function(){
    $scope.selectedCategory = {category_id:$scope.selectedCategory.category_id,category_name:$scope.selectedCategory.category_name};
  };
  $scope.editCategory = function(){
    //DEBUG-console.log($scope.selectedCategory);
    $http.put($rootScope.serverUrl+'adminúcategory?access_token='+$rootScope.settings.auth.access_token,{id:$scope.selectedCategory.category_id,category_name:$scope.selectedCategory.category_name}).then(
      function(response){
        //DEBUG-console.log(response.data);
        for(x in $scope.categories){
          //DEBUG-console.log($scope.categories[x].category_id +' vs '+ response.data.id)
          if($scope.categories[x].category_id == response.data.id){
            $scope.categories[x].category_name = response.data.category_name;
            break;
          }
        }
      },
      function(data){
        //DEBUG-console.log("Connecting problem!");
        $rootScope.connProblem.problem = true;
      }
    );
  };

  $scope.showme = function(){
    //DEBUG-console.log($scope);
  };

}]);

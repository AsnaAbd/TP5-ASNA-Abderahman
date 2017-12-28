var myApp=angular.module("myApp", []);

myApp.config(['$qProvider', function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
}]);
/*myApp.config(function($stateProvider,$urlRouterProvider){
	
	$stateProvider.state('home',{
		url:'/home',
		templateUrl:'views/home.html',
		controller:'HomeController'
	});
	$stateProvider.state('chercherEtudiants',{
		url:'/chercher',
		templateUrl:'views/chercher.html',
		controller:'MyController'
	});
	$stateProvider.state('newEtudiant',{
		url:'/newEtudiant',
		templateUrl:'views/newEtudiant.html',
		controller:'newEtudiantController'
	});
});*/

myApp.controller('HomeController', function(){
	
});

myApp.controller('newEtudiantController', function(){
	
});

myApp.controller("etudiantController", function($scope,$http,$location) {
	$scope.pageEtudiants=null;
	$scope.etudiant={};
	$scope.motCle="";
	$scope.pageCourante=0;
	$scope.size=2;
	$scope.pages=[];
	
	$http.get("http://localhost:8092/listEtudiants")
		.then(function(data) {
			$scope.chercherEtudiants();
		}, function(err) {
			console.log(err)
		});	
	
	$scope.chercherEtudiants=function(){
		$http.get("http://localhost:8092/chercherEtudiants?mc="+$scope.motCle+"&page="+$scope.pageCourante+"&size="+$scope.size)
		.then(function(data) {
		$scope.pageEtudiants=data.data.content;
		$scope.pages=new Array(data.data.totalPages);
		console.log("chercherEtudiants",$scope.pageEtudiants);
		}, function(err) {
		console.log(err)
		});
	};
	$scope.getEtudiant=function(id){
		$http.get("etudiants/"+id)
		.then(function(data) {
			$scope.etudiant=data.data;
			console.log("myStudent",data.data)
		})
		$location.path("editProfil.html").replace();
	};
	$scope.deleteEtudiant=function(id){
		$http.delete("etudiants/"+id)
		.then(function(data) {
		if(!data.data.errors){
			$scope.etudiant=data.data;
			console.log('delete',data);
			$scope.errors=null;
			$scope.mode.value="confirm";
		}else {
			$scope.errors=data.data;
			$scope.etudiant=null;
		}		
		});
	};
	$scope.updateEtudiant=function(id){
		/*$http.put("/etudiants/"+id)
		.then(function(data){
			if(!data.data.errors){
				$scope.etudiant=data.data;
				$scope.errors=null;
				// $scope.mode.value="confirm";
			}else {
				$scope.errors=data.data;
				$scope.etudiant=null;
			}		
		})*/
	};
	
	$scope.getEtudiants = function(){
		$scope.pageCourante=0;
		$scope.chercherEtudiants();
	};
		
	$scope.gotoPage=function(p){
		$scope.pageCourante=p;
		$scope.chercherEtudiants();
	};
/*});*/


/*myApp.controller("InscriptionController", function($scope,$http){*/
	/*$scope.etudiant={};*/
	$scope.errors=null;
	$scope.mode={value:"form"}
	
	$scope.saveEtudiant=function(){
		$http.post("etudiants",$scope.etudiant)
		.then(function(data) {
		if(!data.data.errors){
			$scope.etudiant=data.data;
			$scope.errors=null;
			$scope.mode.value="confirm";
		}else {
			$scope.errors=data.data;
			$scope.etudiant=null;
		}		
		});
	};
});
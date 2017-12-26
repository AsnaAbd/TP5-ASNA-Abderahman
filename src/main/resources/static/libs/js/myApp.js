var myApp=angular.module("myApp", ['ui.router','ngRoute']);

myApp.config(['$locationProvider', function($locationProvider) {
	  $locationProvider.hashPrefix('');
	}]);

myApp.config(function($stateProvider,$urlRouterProvider){
	$stateProvider.hashPrefix('');
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
		templateUrl:'views/NewEtudiant.html',
		controller:'NewEtudiantController'
	});
});

myApp.controller('HomeController', function(){
	
});

myApp.controller('NewEtudiantController', function(){
	
});
myApp.controller("MyController", function($scope,$http) {
	$scope.pageEtudiants=null;
	$scope.motCle="";
	$scope.pageCourante=0;
	$scope.size=3;
	$scope.pages=[];
	
	/*	$http.get("http://localhost:8092/etudiants")
	.then(function(data) {
		$scope.pageEtudiants=data;
		console.log($scope.pageEtudiants);
	}, function(err) {
		console.log(err)
	});	*/
	
$scope.chercherEtudiants=function(){
	$http.get("http://localhost:8092/chercherEtudiants?mc="+$scope.motCle+"&page="+$scope.pageCourante+"&size="+$scope.size)
	.then(function(data) {
		$scope.pageEtudiants=data.data;
		$scope.pages=new Array(data.totalPages);
		console.log($scope.pageEtudiants);
	}, function(err) {
		console.log(err)
	});
};
	$scope.getEtudiants = function(){
		$scope.pageCourante=0;
		$scope.chercherEtudiants();
	}
	$scope.gotopage=function(p){
		$scope.pageCourante=p;
		$scope.chercherEtudiants();
	}
});

myApp.controller("listEtudiantController", function($scope,$http) {
	$scope.pageEtudiants=null;
	$scope.pageCourante=0;
	$scope.size=3;
	
	$scope.listEtudiants=function(){
		$http.get("etudiants?page="+$scope.pageCourante+"&size="+$scope.size)
			.then(function(value) {
				$scope.pageEtudiants=value;
				console.log("=======value1========"+value)
			});
	};
	$scope.listEtudiants();
});

myApp.controller("InscriptionController", function($scope,$http){
	$scope.etudiant={};
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
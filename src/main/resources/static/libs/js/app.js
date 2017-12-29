var app=angular.module("app",[]);

/*app.config(function($stateProvider){
	$qProvider.errorOnUnhandledRejections(false);
	$stateProvider.state('profiles',{
		url:'/profiles',
		templateUrl:'profiles.html',
		controller:'etudiantController'
	});
	$stateProvider.state('chercherEtudiants',{
		url:'/chercher',
		templateUrl:'views/chercher.html',
		controller:'etudiantController'
	});
	$stateProvider.state('diplomes',{
		url:'/diplomes',
		templateUrl:'diplomess.html',
		controller:'diplomeController'
	});
});*/

app.controller("diplomeController", function($scope,$http,$location) {
	$scope.pageDiplomes=null;
	$scope.diplome={};
	$scope.motCle="";
	$scope.pageCourante=0;
	$scope.size=3;
	$scope.pages=[];
	$scope.errors=null;
	$scope.mode={value:"form"};
	
	
	$http.get("http://localhost:8092/listDiplomes")
		.then(function(data) {
			$scope.chercherDiplomes();
		}, function(err) {
			console.log(err)
		});	
	
	$scope.chercherDiplomes=function(){
		$http.get("http://localhost:8092/chercherDiplomes?mc="+$scope.motCle+"&page="+$scope.pageCourante+"&size="+$scope.size)
		.then(function(data) {
		$scope.pageDiplomes=data.data.content;
		$scope.pages=new Array(data.data.totalPages);
		}, function(err) {
		console.log(err)
		});
	};
	$scope.getDiplome=function(id){
		$http.get("diplomes/"+id)
		.then(function(data) {
			$scope.diplome=data.data;
		})
		$location.path("editDiplomes.html").replace();
	};
	$scope.deleteDiplome=function(id){
		$http.delete("diplomes/"+id)
		.then(function(data) {
		if(!data.data.errors){
			$scope.diplome=data.data;
			$scope.errors=null;
			$scope.mode.value="confirm";
		}else {
			$scope.errors=data.data;
			$scope.diplome=null;
		}		
		});
	};
	$scope.updateDiplome=function(id){
		$http.put("/diplomes/"+id)
		.then(function(data){
			if(!data.data.errors){
				$scope.diplome=data.data;
				$scope.errors=null;
				// $scope.mode.value="confirm";
			}else {
				$scope.errors=data.data;
				$scope.diplome=null;
			}		
		})
	};
	
	$scope.getDiplomes = function(){
		$scope.pageCourante=0;
		$scope.chercherDiplomes();
	};
		
	$scope.gotoPage=function(p){
		$scope.pageCourante=p;
		$scope.chercherDiplomes();
	};
	
	$scope.saveDiplome=function(){
		$http.post("diplomes",$scope.diplome)
		.then(function(data) {
		if(!data.data.errors){
			$scope.diplome=data.data;
			$scope.errors=null;
			$scope.mode.value="confirm";
		}else {
			$scope.errors=data.data;
			$scope.diplome=null;
		}
		
		});
	};
});

app.controller("inscriptionController", function($scope,$http,$location) {
   $scope.diplome={};
   $scope.errors=null;
	$scope.mode={value:"form"}
	$scope.saveDiplome=function(){
		$http.post("diplomes",$scope.diplome)
		.then(function(data) {
		if(!data.data.errors){
			$scope.diplome=data.data;
			$scope.errors=null;
			$scope.mode.value="confirm";
			alert('diplôme est ajouté avec success');
		}else {
			$scope.errors=data.data;
			$scope.diplome=null;
		}		
		});
	};
	$scope.newDiplome=function(){
		$scope.diplome=null;
	};
});

app.controller("etudiantController", function($scope,$http,$location) {
	$scope.pageEtudiants=null;
	$scope.etudiant={};
	$scope.motCle="";
	$scope.pageCourante=0;
	$scope.size=4;
	$scope.pages=[];
	$scope.errors=null;
	$scope.mode={value:"form"}
	$scope.fruits = ['apple', 'orange', 'pear', 'naartjie'];
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
		}, function(err) {
		console.log(err)
		});
	};
	$scope.getEtudiant=function(id){
		$http.get("etudiants/"+id)
		.then(function(data) {
			$scope.etudiant=data.data;
		})
		$location.path("editProfil.html").replace();
	};
	$scope.deleteEtudiant=function(id){
		$http.delete("etudiants/"+id)
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
	$scope.updateEtudiant=function(id){
		$http.put("/etudiants/"+id)
		.then(function(data){
			if(!data.data.errors){
				$scope.etudiant=data.data;
				$scope.errors=null;
			}else {
				$scope.errors=data.data;
				$scope.etudiant=null;
			}		
		})
	};
	
	$scope.getEtudiants = function(){
		$scope.pageCourante=0;
		$scope.chercherEtudiants();
	};
		
	$scope.gotoPage=function(p){
		$scope.pageCourante=p;
		$scope.chercherEtudiants();
	};
	
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
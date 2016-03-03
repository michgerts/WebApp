// Front end js
app.controller('HomeController',function ($scope, $http, $window)
{

	$scope.currentUser = {ID:1,Name:'',Password:'',NickName:'',Description:'',Pic:''};
	var path = $window.location.href;
	$scope.$on("UpdateFromAll", function (event, args) {
		$scope.$broadcast("UpdateFromAllB");
		});
	$scope.$on("UpdateFromNew", function (event, args) {
		$scope.$broadcast("UpdateFromNewB");
		});
	$http(
	{
		method: 'POST',
		url: 'userservlet',
		headers: {'Content-Type': 'application/json'}
	}).success( function (response)// the userservlet reads the user id from the session by itself
	{
		$scope.currentUser = response;
	});
	
	$scope.signout = function ()
	{
		$http(
				{
					method: 'post',
					url: 'useridservlet',
					headers: {'Content-Type': 'application/json'},
					data:  ''
				}).success( function (response)
				{	
					$window.location = './#/';
				});
		//setCookie("id", "", 1);
		
	}

	
});


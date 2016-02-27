// Front end js
app.controller('HomeController',function ($scope, $http, $window)
{
	$scope.currentUser = {ID:1,Name:'',Password:'',NickName:'',Description:'',Pic:''};
	var path = $window.location.href;
	$scope.currentUser.Name = getCookie("id");
	$scope.signout = function ()
	{
		setCookie("id", "", 1);
		$window.location = './#/';
	}
	$http(
	{
		method: 'POST',
		url: 'userservlet',
		headers: {'Content-Type': 'application/json'},
		data:  JSON.stringify($scope.currentUser)
	}).success( function (response)
	{
		$scope.currentUser = response;
	});
});
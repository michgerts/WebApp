// Front end js
app.controller('QuestionSubmit',function ($scope, $http, $window)
{

	$scope.ask = function ()
	{
		$scope.question.Time= new Date();
		$scope.question.Asker = getCookie("id");
		$scope.question.ID=0;
		$scope.question.Likes=0;
		$scope.question.Answered=false;
		$http(
				{
					method: 'POST',
					url: 'qsubmitservlet',
					headers: {'Content-Type': 'application/json'},
					data:  JSON.stringify($scope.question)
				}).success( function (response)
				{
					$scope.question.ID = response.msg;
				});
	}
});
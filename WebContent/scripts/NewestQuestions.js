// Front end js
app.controller('NewestQuestions',function ($scope, $http, $window)
{

	$scope.answer = function ()//answers a question
	{
		$http(
				{
					method: 'POST',
					url: 'newestquestionsservlet',
					headers: {'Content-Type': 'application/json'},
					data:  JSON.stringify($scope.question)
				}).success( function (response)
				{
					
				});
	}
});


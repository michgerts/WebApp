//Front end js
app.controller('AnswerController',function ($scope, $http, $window)
{
	var path = $window.location.href;
	var QID=  path.split("/")[7];
	var currentUser= getCookie("id");
	$http(
	{
		method: 'POST',
		url: 'showquestionservlet',
		headers: {'Content-Type': 'application/json'},
		data:  JSON.stringify(QID)
	}).success( function (response)
	{
		$scope.question = response;
		if ($scope.question.ID == -1)
			{
			$window.location = './#/home';
			}
		else
			{
				var d= new Date($scope.question.Time);
				var options = {
					    weekday: "long", year: "numeric", month: "short",
					    day: "numeric", hour: "2-digit", minute: "2-digit"
					};
				$scope.question.Time = d.toLocaleTimeString("en-us", options);
			}
	});
});
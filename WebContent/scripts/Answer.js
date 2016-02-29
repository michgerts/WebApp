//Front end js
app.controller('AnswerController',function ($scope, $http, $window)
{
	var path = $window.location.href;
	var QID=  path.split("/")[7];
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
	$scope.ask = function ()
	{
		/*Handels answer submit*/
		$scope.answer.Time= new Date();
		$scope.answer.UID = $scope.currentUser.Name;
		$scope.answer.QID=QID
		$scope.answer.Likes=0;
		$scope.answer.Text = commaSep($scope.answer.Text);
		var m=0;
		if ($scope.answer.Text.length > 300)
			m++;
		if (m==0)
		{
			$http(
			{
				method: 'POST',
				url: 'asubmitservlet',
				headers: {'Content-Type': 'application/json'},
				data:  JSON.stringify($scope.answer)
			}).success( function (response)
			{	
				location.reload();
			});
		}
		else
		{	if (m !=0)
				{
				$('#answerLength h4').remove();
				$('#answerLength').append('<h4>Your answer is longer then 300 chars :(</h4>');
				}

		}	
	}
});
function commaSep (InputText)
{
	var Text =InputText.split("'");
	var res = "";
	for (var j=0; j <Text.length-1; j++)
		{
		Text[j]+="''";
		res+=Text[j];
		}
	res+=(Text[Text.length-1]);
	return res;
}

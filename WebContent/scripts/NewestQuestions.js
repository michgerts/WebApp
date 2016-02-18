// Front end js
app.controller('NewestQuestions',function ($scope, $http, $window)
{
	$scope.init = function ()//answers a question
	{
		var a = 1;
		$http(
		{
			method: 'get',
			url: 'newestquestionsservlet',
			headers: {'Content-Type': 'application/json'},
		}).success( function (response)
		{				
			for(var i=0; i<response.length; i++)
			{
			      var text = response[i].Text;
			      var time = response[i].Time;
			      var likes = response[i].Likes;
			      $("#newQuestionsTable").append('<tr><td>'+ text + '</td><td>'+time+'</td><td>' +'</td><td>'+likes+'</td></tr>');
			} 
		});
	}
});
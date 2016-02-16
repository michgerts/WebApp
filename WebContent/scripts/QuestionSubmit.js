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
					$scope.question.ID = response.data.records;
				});
		$http(
				{
					method: 'GET',
					url: 'qsubmitservlet',
					headers: {'Content-Type': 'application/json'}
				}).success( function (response)
				{
					debugger;
					
					
					for(var i=0; i<response.length; i++){
					      var text = response[i].Text;
					      var time = response[i].Time;
					      var likes = response[i].Likes;
					      $("#newQuestionsTable").append('<tr><td>'+ text + '</td><td>'+time+'</td><td>' +'</td><td>'+likes+'</td></tr>');
					   }
					
				    
				    
				});
	}
});
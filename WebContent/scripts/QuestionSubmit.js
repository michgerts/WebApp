// Front end js
app.controller('QuestionSubmit',function ($scope, $http, $window)
{

	$scope.ask = function ()
	{
		/*Handels questions submit*/
		$scope.question.Time= new Date();
		$scope.question.Asker = getCookie("id");
		$scope.question.ID=0;
		$scope.question.Likes=0;
		$scope.question.Answered=false;
		/*Handels topics submit*/
		var topicsArray = $scope.topic.Text.split(",");
		topicsArray.sort;
		var res = [];
		for (var i = 0; i < topicsArray.length; i++)
		{	var flag ="false"
				for (var m = 0; m < res.length; m++)
				{
					if (topicsArray[i].trim() === topicsArray[m].trim())
					{
						flag = "true"       	
					}
				}
				
			if (flag=="false")
				res.push(topicsArray[i]);
		}
		topicsArray = res;
		var j=0;
		$scope.errorArray=[];
		for (var k=0; k<topicsArray.length; k++)
		{
			if (topicsArray[k].length > 50)
			{
				$scope.errorArray[j]  = topicsArray[k];
				j++;				
			}
		}
		if (j == 0)
		{
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
		else
		{
				for (var l=0; l<$scope.errorArray.length; l++)
				{
					$('ul.error-list').append('<li>' + $scope.errorArray[l] + '</li>');				
				}

		}	
	}
});

	

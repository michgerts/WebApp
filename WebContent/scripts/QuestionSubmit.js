// Front end js
app.controller('QuestionSubmit',function ($scope, $http, $window)
{
	$scope.ask = function ()
	{
		var userIdFromSession;
		$http(
				{
					method: 'get',
					url: 'useridservlet',
					headers: {'Content-Type': 'application/json'}
				}).success( function (response)
				{			
					userIdFromSession = response;
					//$scope.userIDFromSession = userId;
				
		
		
		/*Handels questions submit*/
		$scope.question.Time= new Date();
		$scope.question.Asker = userIdFromSession;
		$scope.question.ID=0;
		$scope.question.Likes=0;
		$scope.question.Answered=false;
		$scope.question.Text = commaSep($scope.question.Text);
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
				res.push(commaSep(topicsArray[i]));
		}
		topicsArray = res;
		var j=0;
		var m=0;
		$scope.errorArray=[];
		for (var k=0; k<topicsArray.length; k++)
		{
			if (topicsArray[k].length > 50)
			{
				$scope.errorArray[j]  = topicsArray[k];
				j++;				
			}
		}
		if ($scope.question.Text.length > 300)
			m++;
		if (j == 0 && m==0)
		{
			$http(
			{
				method: 'POST',
				url: 'qsubmitservlet',
				headers: {'Content-Type': 'application/json'},
				data:  JSON.stringify($scope.question)
			}).success( function (response)
			{	
				for (var i=0; i<topicsArray.length; i++)
				{
					var Topic= {QID: response.msg, Topic: topicsArray[i]};
					$http(
						{
							method: 'POST',
							url: 'topicsservlet',
							headers: {'Content-Type': 'application/json'},
							data:  JSON.stringify(Topic)
						});
				}
				location.reload();
			
			});
		}
		else
		{	if (j != 0)
			{
				$('ul.error-list li').remove();
					for (var l=0; l<$scope.errorArray.length; l++)
					{
	
						$('ul.error-list').append('<li>' + $scope.errorArray[l] + '</li>');				
					}
			}
			else if (m !=0)
				{
				$('#questionLength h4').remove();
				$('#questionLength').append('<h4>Your question is longer then 300 chars :(</h4>');
				}

		}	
				});
	}
	
	$scope.goToLeaderboard  = function ($event) {
		$window.location = './#/leaderboard';
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

function formatDate(oldDate)
{
	oldDate= new Date(oldDate);
	var day=oldDate.getDate();
	day = leftPadZeros(day, 2);
	var month=oldDate.getMonth() + 1;
	month = leftPadZeros(month, 2);
	var year=oldDate.getFullYear();
	var hour=oldDate.getHours();
	hour = leftPadZeros(hour, 2);
	var minute=oldDate.getMinutes();
	minute = leftPadZeros(minute, 2);
	var second=oldDate.getSeconds();
	second = leftPadZeros(second, 2);
	var newDate= day +'/' + month + '/' + year + " " + hour + ":" + minute + ":" + second;
	return newDate;
}

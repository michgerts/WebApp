var app = angular.module('my-app',[]);

app.controller('RouteConroller',function ($scope, $http, $window)
{
	$scope.view = 'index';
	
	$scope.$on('$locationChangeStart', function(event,next)
	{
		var siteView = next.split('/#');
		siteView = siteView[1];
		var views = ["signup", "home", "leaderboard", "userprofile", "topics"];
		siteView = siteView.split('/')[1];
		var params = next.split('/#/')[1];
		$scope.username = params.split('/')[1];
		$scope.usernumber = params.split('/')[2];
		if (typeof siteView == 'undefined' || views.indexOf(siteView) == -1 )
		{
			siteView = 'index';
			$window.location = './#/';
		}
		$scope.view = siteView;
		if (siteView == "home") 
		{
			var x;
			$http(
					{
						method: 'get',
						url: 'useridservlet',
						headers: {'Content-Type': 'application/json'}
					}).success( function (response)
					{		
						x = response;

						//var x = getCookie("id");	
						if (x == "")
						{
						$window.location = './#/';
						}
						else
							{
								var homeView= next.split("/")[6];
								if (homeView == "questions")
									{
										$scope.view=homeView;
									}
							
							}
					});
		}
		else if (siteView == "topics")
		{
				$scope.view="allquestionsontopic";
		}
		else if(siteView == "leaderboard")
		{
			var userprofile= next.split("/")[6];
			if (userprofile == "userprofile")
			{
				$scope.view=userprofile;
			}
			else
				$scope.view=siteView;
		}
	});
	
	
});

app.factory('dataService', function()
{
	var savedData = {};
	function set(data)
    {
    	this.savedData = data;
    }
	function get()
	{
		return this.savedData;
	}
	return {
		set: set,
		get: get
	}
});

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

function leftPadZeros(number, targetLength) {
    var output = number + '';
    while (output.length < targetLength) {
        output = '0' + output;
    }
    return output;
}


var app = angular.module('my-app',[]);

app.controller('RouteConroller',function ($scope, $http, $window)
{
	$scope.view = 'index';
	
	$scope.$on('$locationChangeStart', function(event,next)
	{
		var siteView = next.split('/#');
		siteView = siteView[1];
		var views = ["signup", "home"];
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
			var x = getCookie("id");	
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

function setCookie(cname, cvalue, exdays)
{
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname)
{
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

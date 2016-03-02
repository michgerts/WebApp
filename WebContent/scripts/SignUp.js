// Front end js
app.controller('SignUpController',function ($scope, $http, $window)
{
	$scope.user = {ID:1,Name:'',Password:'',NickName:'',Description:'',Pic:''};
	$scope.signup = function ()
	{
		if ($('.required.has-success').length>2)
		{
			if ($scope.user.Pic.length==0)
				{
					$scope.user.Pic="http://4.bp.blogspot.com/-0ox-AuwXsJk/VWyZt0PB81I/AAAAAAAABE4/2-CJXNHD25c/s1600/tho%2Bcb.jpg";
				}
			$http(
			{
				method: 'POST',
				url: 'signupservlet',
				headers: {'Content-Type': 'application/json'},
				data:  JSON.stringify($scope.user)
			}).success( function (msg)
			{
				msg = msg.msg; 
				if (!msg.length)
				{
					$http(
							{
								method: 'post',
								url: 'useridservlet',
								headers: {'Content-Type': 'application/json'},
								data:  JSON.stringify($scope.user.Name)
							}).success( function (response)
							{		
								$window.location = './#/home';
							});
					//setCookie("id", $scope.user.Name, 1);
					
				}
				if ($('ul.error-list').children().length)
				{
					$('ul.error-list').children().remove();
				}
				for (var i=0;i<msg.length; i++)
				{					
					$('ul.error-list').append('<li>' + msg[i] + '</li>');
				}
			});
		}
		else
		{
			validateRequired ();
		}
	};
	
	angular.element(document).ready(function ()
	{
		$("input[name='user-name']").focus(function ()
		{	
			validate ($(this),10);
		});
		$("input[name='user-password']").focus(function ()
		{	
			validate ($(this),8);
		});
		$("input[name='user-nickname']").focus(function ()
		{	
			validate ($(this),20);
		});
		$("textarea[name='user-description']").focus(function ()
		{	
			validate ($(this),50);
		});
		$("input[name='user-pic']").focus(function ()
		{	
			validate ($(this),255);
		});		
	});
});

function validateRequired ()
{
	validate($("input[name='user-name']"),10);
	validate($("input[name='user-password']"),8);
	validate($("input[name='user-nickname']"),20);
}

function validateInner (input, maxLength)
{
	var value = input.val();
	inputLen = value.length; 
	if ((inputLen>maxLength) || (inputLen<1))
	{
		input.parent().removeClass('has-success');
		input.parent().addClass('has-error');
		input.next().removeClass('glyphicon-ok');
		input.next().addClass('glyphicon-remove');
	}
	else
	{
		input.parent().removeClass('has-error');
		input.parent().addClass('has-success');
		input.next().removeClass('glyphicon-remove');
		input.next().addClass('glyphicon-ok');
	}
};
function validate (input, maxLength)
{
	input.keyup(function (){validateInner(input, maxLength)});
	$(document).mousemove(function (){validateInner(input, maxLength)});
};
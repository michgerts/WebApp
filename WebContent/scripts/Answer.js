//Front end js
app.controller('AnswerController',function ($scope, $http, $window, $compile)
{
	var path = $window.location.href;
	var QID=  path.split("/")[7];
	var list;
	$scope.listLength=0;
	$scope.init = function ()
	{
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
						var d= formatDate(new Date($scope.question.Time));
						/*var options = {
							    weekday: "long", year: "numeric", month: "short",
							    day: "numeric", hour: "2-digit", minute: "2-digit"
							};*/
						$scope.question.Time = d;
					}
		});
	
		$http(
		{
			method: 'POST',
			url: 'showanswerservlet',
			headers: {'Content-Type': 'application/json'},
			data:  JSON.stringify(QID)
		}).success( function (response)
		{
			$scope.listLength= response.length;
			list = $("#answersList");
			list.empty();
			for(var i=0; i<response.length; i++)
			{
				list = $("#answersList");
				if(i == 0)
					listAnswerItem(response[i], list, $scope, "true");
				else
					listAnswerItem(response[i], list, $scope, "false");
									
			}
			$compile(list)($scope);				
		});
	}
	$scope.submitAnswer = function ()
	{
		/*Handels answer submit*/
		$scope.answer.AID= -1;
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

	$scope.expandAnswer = function($event)
	{
		var li=  angular.element($event.currentTarget).parent('li');
		var answerId = li.attr("id");
		var div= angular.element($event.currentTarget).siblings('div');
		div.addClass("visible");
		div.removeClass("hidden");
		var image = angular.element($event.currentTarget)
		image.remove();
		var image= document.createElement("img");
		image.setAttribute('src', "./images/Minus-48.png");
		//li.prepend(image);
		image.setAttribute('ng-click', "collapseAnswer($event)");
		$compile(image)($scope)
		li.prepend(image);
		
	}
	
	$scope.collapseAnswer = function($event)
	{
		var li=  angular.element($event.currentTarget).parent('li');
		var div= angular.element($event.currentTarget).siblings('div');
		div.addClass("hidden");
		div.removeClass("visible");
		var image = angular.element($event.currentTarget)
		image.remove();
		var image= document.createElement("img");
		image.setAttribute('src', "./images/plus-48.png");
		//li.prepend(image);
		image.setAttribute('ng-click', "expandAnswer($event)");
		$compile(image)($scope)
		li.prepend(image);
	}
	
	$scope.voteUpAnswer = function ($event) {	
		var li=  angular.element($event.currentTarget).parent().parent('li');
		var answerId = li.attr("id");
	    var data =  JSON.stringify("1,"+ answerId);
	    var replier =li.attr("class");
	    $scope.openListItems=[];
	    for(var j=0; j<$('li div.visible').length; j++)
	    {
	    	var temp= $('li div.visible')[j];
	    	$scope.openListItems.push(parseInt(temp.parentElement.getAttribute("id")));
	    }
	    	
	    var userId;
	    $http(
				{
					method: 'get',
					url: 'useridservlet',
					headers: {'Content-Type': 'application/json'}
				}).success( function (response)
				{			
					userId = response;
				});
	    
	    //if (getCookie("id") != replier)
	    if (userId != replier)
	    {
	    	$http(
	    			{
	    				method: 'POST',
	    				url: 'voteanswerservlet',
	    				headers: {'Content-Type': 'application/json'},
	    				data:  JSON.stringify(data)
	    			}).success( function (response)
	    			{	
	    				$scope.listLength= response.length;
	    				list = $("#answersList");
	    				list.empty();
	    				for(var i=0; i<response.length; i++)
	    				{
	    					list = $("#answersList");
	    					if ($scope.openListItems.indexOf(response[i].AID) == -1)
	    					{
	    						listAnswerItem(response[i], list, $scope, "false");   						
	    					}
	    					else
	    					{
	    						listAnswerItem(response[i], list, $scope, "true");
	    					}
	    				}
	    				$compile(list)($scope);
	    			});
	    }
	 }
	$scope.voteDownAnswer = function ($event) {
		var li=  angular.element($event.currentTarget).parent().parent('li');
		var answerId = li.attr("id");
	    var data =  JSON.stringify("0,"+ answerId);
	    var replier =li.attr("class");
	    $scope.openListItems=[];
	    for(var j=0; j<$('li div.visible').length; j++)
	    {
	    	var temp= $('li div.visible')[j];
	    	$scope.openListItems.push(parseInt(temp.parentElement.getAttribute("id")));
	    }
	    	    
	    var userId;
	    $http(
				{
					method: 'get',
					url: 'useridservlet',
					headers: {'Content-Type': 'application/json'}
				}).success( function (response)
				{			
					userId = response;
				});
	    
	    //if (getCookie("id") != replier)
	    if (userId != replier)
	    {
	    	$http(
	    			{
	    				method: 'POST',
	    				url: 'voteanswerservlet',
	    				headers: {'Content-Type': 'application/json'},
	    				data:  JSON.stringify(data)
	    			}).success( function (response)
	    			{	
	    				$scope.listLength= response.length;
	    				list = $("#answersList");
	    				list.empty();
	    				for(var i=0; i<response.length; i++)
	    				{
	    					list = $("#answersList");
	    					if ($scope.openListItems.indexOf(response[i].AID) == -1)
	    					{
	    						listAnswerItem(response[i], list, $scope, "false");   						
	    					}
	    					else
	    					{
	    						listAnswerItem(response[i], list, $scope, "true");
	    					}
	    				}
	    				$compile(list)($scope);
	    			});
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

function listAnswerItem(ans, list, $scope, show)
{
	
    var text = ans.Text;
    var aid = ans.AID;
	var time = formatDate(ans.Time);	      
    var likes = ans.Likes;
    var uid = ans.UID;  
    var li= document.createElement("li");
    li.setAttribute('id', aid);
    li.setAttribute("class", uid);
    var image = document.createElement("img");
    var div= document.createElement("div");
    div.appendChild(document.createTextNode(text));
    var button = document.createElement("button");
    button.setAttribute("type", "buton");
    button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-down move");
    button.setAttribute("data-loading-text", " ... ");
    button.setAttribute("ng-click", "voteDownAnswer($event)");
    div.appendChild(button);  
    var button = document.createElement("button");
    button.setAttribute("type", "buton");
    button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-up");
    button.setAttribute("data-loading-text", " ... ");
    button.setAttribute("ng-click", "voteUpAnswer($event)");
    
    div.appendChild(button);  
    if (show == "false")
    {
	    div.setAttribute('class', "hidden");
	    image.setAttribute('src', "./images/plus-48.png");
	    image.setAttribute('ng-click', "expandAnswer($event)");
    }
    else
    {
    	div.setAttribute('class', "visible");
 	    image.setAttribute('src', "./images/Minus-48.png");
 	    image.setAttribute('ng-click', "collapseAnswer($event)");
    	
    }
    li.appendChild(image);
    li.appendChild(document.createTextNode(uid + " answered this question at " + time + " and got " + likes + " likes"));
    li.appendChild(div);
    list.append(li);
}
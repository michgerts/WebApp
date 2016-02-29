//Front end js
app.controller('LeaderboardController',function ($scope, $http, $window, $compile)
{
	var path = $window.location.href;
	var UID=  path.split("/")[7];
	
	var table = $("#leaderboardTable > tbody");
	$scope.init = function ()
	{
		$http(
		{
			method: 'get',
			url: 'leaderboardservlet',
			headers: {'Content-Type': 'application/json'}
		}).success( function (response)
		{			
			localStorage.setItem('leaderboard', JSON.stringify(response));
			for(var i=0; i<20 && i<response.length; i++)
			{
				table = $("#leaderboardTable > tbody:last-child");
				listItem(response[i], table);
			}
			$compile(table)($scope);		
		});
	}
	
	function listItem(response, table)
	{
	    var pic = response.user.Pic;
	    var nick = response.user.NickName;
	    var rating = response.rating;

	    var tr = document.createElement("tr");
	    var td = document.createElement("td");
	    var img = document.createElement("img");
	    img.setAttribute("class", "profilePics img-circle");
	    img.setAttribute("src", pic);
	    td.appendChild(img);
	    tr.appendChild(td);
	    
	    var td = document.createElement("td");
	    //td.appendChild(document.createTextNode(nick));
	    var a = document.createElement("a");
	    a.textContent = nick;
	    a.setAttribute('href', "./#/leaderboard/userprofile/" + response.user.Name);
	    td.appendChild(a);
	    tr.appendChild(td);
	    
	    var td = document.createElement("td");
	    td.appendChild(document.createTextNode(rating));
	    tr.appendChild(td);
	    
	    var td = document.createElement("td");
	    var i=0;
	    while(i<response.fiveTopTopics.length)
    	{
	    	var span = document.createElement("span");
		    span.appendChild(document.createTextNode(response.fiveTopTopics[i]));
		    span.setAttribute("class", "label label-default");
		    td.appendChild(span);
		    i++;
    	}
	    
	    
	    tr.appendChild(td);
	    
	    table.append(tr);
	}
	
	$scope.getUserProfile = function ()
	{
		
		var leaderboardUsers = localStorage.getItem('leaderboard');
		var leaderboardUsersJs = JSON.parse(leaderboardUsers);
		//find this user
		var userToPresent;
		for(var i=0;i<leaderboardUsersJs.length; i++)
		{//UID
			if(leaderboardUsersJs[i].user.Name == UID)
				userToPresent = leaderboardUsersJs[i];
		}
		
		$scope.thisUser = {Pic: userToPresent.user.Pic,Nickname: userToPresent.user.Nickname,Description: userToPresent.user.Description,Rating: userToPresent.rating,Expertise: userToPresent.fiveTopTopics,FiveLastQuestions: userToPresent.askedQuestions, Name: userToPresent.user.Name};
		
		
	}
	
	
});
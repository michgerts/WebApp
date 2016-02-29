//Front end js
app.controller('LeaderboardController',function ($scope, $http, $window, $compile)
{
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
			//localStorage.setItem('response', JSON.stringify(response));
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
	    td.appendChild(document.createTextNode(nick));
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
	
});
//Front end js
app.controller('AnswerController',function ($scope, $http, $window, $compile)
{
	var table = $("#leaderboardTable > tbody");
	$scope.init = function ()
	{
		$http(
		{
			method: 'get',
			url: 'newestquestionsservlet',
			headers: {'Content-Type': 'application/json'}
		}).success( function (response)
		{			
			//localStorage.setItem('response', JSON.stringify(response));
			for(var i=0; i<20; i++)
			{
				table = $("#leaderboardTable > tbody:last-child");
				listItem(response[i], table);
			}
			$compile(table)($scope);		
		});
	}
	
	function listItem(response, table)
	{
	    var pic = response.user.pic;
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
	    td.appendChild(nick);
	    tr.appendChild(td);
	    
	    var td = document.createElement("td");
	    td.appendChild(rating);
	    tr.appendChild(td);
	    
	    var td = document.createElement("td");
	    var span = document.createElement("span");
	    span.appendChild('1');
	    span.setAttribute("class", "label label-default");
	    td.appendChild(span);
	    tr.appendChild(td);
	    
	    table.append(tr);
	}
	
});
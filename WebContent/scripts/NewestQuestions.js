// Front end js
app.controller('NewestQuestions',function ($scope, $http, $window)
{
	$scope.init = function ()
	{
		$http(
		{
			method: 'post',
			url: 'newestquestionsservlet',
			headers: {'Content-Type': 'application/json'},
			data:  JSON.stringify('0')
		}).success( function (response)
		{	
		    var options = 
		    {
				    weekday: "long", year: "numeric", month: "short",
				    day: "numeric", hour: "2-digit", minute: "2-digit"
		    };
			for(var i=0; i<response.length; i++)
			{
			      var text = response[i].Text;
			      var d= new Date(response[i].Time);
				  var time = d.toLocaleTimeString("en-us", options);
			      var likes = response[i].Likes;
			      var id = response[i].ID;
			      
			      var a = document.createElement("a");
			      var ul = document.getElementById("newQuestionsList");
			      var li = document.createElement("li");
			      a.textContent = text + ' ' + time;
			      a.setAttribute('href', "./#/home/questions/" + id);
			      li.appendChild(a);
			      ul.appendChild(li);
			      li.setAttribute("class", "list-group-item");
			      var span = document.createElement("span");
			      span.appendChild(document.createTextNode(likes));
			      span.setAttribute("class", "badge");
			      li.appendChild(span);
			      ul.appendChild(li);
			      //alert(li.class);
			      
			     // $("#newQuestionsTable").append('<li>'+ text + '</li><li>'+time+'</li><li>'+likes+'</li>');
			      //$("#newQuestionsTable").append('<tr><td>'+ text + '</td><td>'+time+'</td><td>' +'</td><td>'+likes+'</td></tr>');
			} 
		});
	}
	
	$scope.next = function()
	{
		$http(
				{
					method: 'post',
					url: 'newestquestionsservlet',
					headers: {'Content-Type': 'application/json'},
					cache: false,
					data:  JSON.stringify('1')
				}).success( function (response)
				{				
					var ul = document.getElementById("newQuestionsList");
			        ul.innerHTML = "";
					for(var i=0; i<response.length; i++)
					{
					      var text = response[i].Text;
					      var time = response[i].Time;
					      var likes = response[i].Likes;
					      
					      
					      var li = document.createElement("li");
					      li.appendChild(document.createTextNode(text + ' ' + time ));
					      li.setAttribute("class", "list-group-item");
					      var span = document.createElement("span");
					      span.appendChild(document.createTextNode(likes));
					      span.setAttribute("class", "badge");
					      li.appendChild(span);
					      ul.appendChild(li);
					      //alert(li.class);
					      
					     // $("#newQuestionsTable").append('<li>'+ text + '</li><li>'+time+'</li><li>'+likes+'</li>');
					      //$("#newQuestionsTable").append('<tr><td>'+ text + '</td><td>'+time+'</td><td>' +'</td><td>'+likes+'</td></tr>');
					} 
				});
	}
	
	$scope.prev = function()
	{
		$http(
				{
					method: 'post',
					url: 'newestquestionsservlet',
					headers: {'Content-Type': 'application/json'},
					cache: false,
					data:  JSON.stringify('0')
				}).success( function (response)
				{			
					var ul = document.getElementById("newQuestionsList");
					ul.innerHTML = "";	
					for(var i=0; i<response.length; i++)
					{
					      var text = response[i].Text;
					      var time = response[i].Time;
					      var likes = response[i].Likes;
					      
					      
					      var li = document.createElement("li");
					      li.appendChild(document.createTextNode(text + ' ' + time ));
					      li.setAttribute("class", "list-group-item");
					      var span = document.createElement("span");
					      span.appendChild(document.createTextNode(likes));
					      span.setAttribute("class", "badge");
					      li.appendChild(span);
					      ul.appendChild(li);
					      //alert(li.class);
					      
					     // $("#newQuestionsTable").append('<li>'+ text + '</li><li>'+time+'</li><li>'+likes+'</li>');
					      //$("#newQuestionsTable").append('<tr><td>'+ text + '</td><td>'+time+'</td><td>' +'</td><td>'+likes+'</td></tr>');
					} 
				});
	}
});
// Front end js
app.controller('NewestQuestions',function ($scope, $http, $window, $compile)
{
	$scope.init = function ()
	{
		$http(
		{
			method: 'get',
			url: 'newestquestionsservlet',
			headers: {'Content-Type': 'application/json'}
		}).success( function (response)
		{			
			var options = 
		    {
				    weekday: "long", year: "numeric", month: "short",
				    day: "numeric", hour: "2-digit", minute: "2-digit"
		    };

			var pageNum = { "pageNumber": 0 };
			// Put the object into storage
			localStorage.setItem('pageNum', JSON.stringify(pageNum));
			localStorage.setItem('response', JSON.stringify(response));

			var ul = document.getElementById("newQuestionsList");
			
			for(var i=0; i<20; i++)
			{
			      var text = response[i].Text;
				var d= new Date(response[i].Time);
				  var time = d.toLocaleTimeString("en-us", options);
			      
			      var likes = response[i].Likes;
				 var id = response[i].ID;
			      
			      var a = document.createElement("a");
			      
			      
			      
			      
			      var li = document.createElement("li");
			      a.textContent = text + ' ' + time;
			      a.setAttribute('href', "./#/home/questions/" + id);
			      li.appendChild(a);
			      
			      li.setAttribute("class", "list-group-item");
			      li.setAttribute("id", response[i].ID);
			      
			      var button = document.createElement("button");
			      button.setAttribute("type", "buton");
			      button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-down");
			      button.setAttribute("data-loading-text", " ... ");
			      button.setAttribute("ng-click", "voteDown($event)");
			      li.appendChild(button);
			      
			      var button = document.createElement("button");
			      button.setAttribute("type", "buton");
			      button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-up");
			      button.setAttribute("data-loading-text", " ... ");
			      button.setAttribute("ng-click", "voteUp($event)");
			      li.appendChild(button);
			      
			      var span = document.createElement("span");
			      span.appendChild(document.createTextNode(likes));
			      span.setAttribute("class", "badge");
			      li.appendChild(span);
			      li.appendChild(document.createTextNode(text + ' ' + time ));
			      ul.appendChild(li);
			}
			$compile(ul)($scope);
			
		});
	}
	
	$scope.next = function()
	{
		var retrievedPage = localStorage.getItem('pageNum');
		var retrievedResponse = localStorage.getItem('response');
		
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		pageNummberInt++;
		var response = JSON.parse(retrievedResponse);
		
		var ul = document.getElementById("newQuestionsList");
		ul.innerHTML = "";	
		for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
		{
		      var text = response[i].Text;
				var d= new Date(response[i].Time);
				  var time = d.toLocaleTimeString("en-us", options);
			      
			      var likes = response[i].Likes;
				 var id = response[i].ID;
			      
			      var a = document.createElement("a");
			      
			      
			      
			      
			      var li = document.createElement("li");
			      a.textContent = text + ' ' + time;
			      a.setAttribute('href', "./#/home/questions/" + id);
			      li.appendChild(a);
		      li.setAttribute("class", "list-group-item");
		      li.setAttribute("id", response[i].ID);
		      
		      var button = document.createElement("button");
		      button.setAttribute("type", "buton");
		      button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-down");
		      button.setAttribute("data-loading-text", " ... ");
		      button.setAttribute("ng-click", "voteDown($event)");
		      li.appendChild(button);
		      
		      var button = document.createElement("button");
		      button.setAttribute("type", "buton");
		      button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-up");
		      button.setAttribute("data-loading-text", " ... ");
		      button.setAttribute("ng-click", "voteUp($event)");
		      li.appendChild(button);
		      
		      var span = document.createElement("span");
		      span.appendChild(document.createTextNode(likes));
		      span.setAttribute("class", "badge");
		      li.appendChild(span);
		      li.appendChild(document.createTextNode(text + ' ' + time ));
		      ul.appendChild(li);
		      
		}
		$compile(ul)($scope);
		
		var pageNum = { "pageNumber": pageNummberInt };
		// Put the object into storage
		localStorage.setItem('pageNum', JSON.stringify(pageNum));
	}
	
	$scope.prev = function()
	{
		var retrievedPage = localStorage.getItem('pageNum');
		var retrievedResponse = localStorage.getItem('response');
		
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		pageNummberInt--;
		var response = JSON.parse(retrievedResponse);
		
		var ul = document.getElementById("newQuestionsList");
		ul.innerHTML = "";	
		for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
		{
		      var text = response[i].Text;
				var d= new Date(response[i].Time);
				  var time = d.toLocaleTimeString("en-us", options);
			      
			      var likes = response[i].Likes;
				 var id = response[i].ID;
			      
			      var a = document.createElement("a");
			      
			      
			      
			      
			      var li = document.createElement("li");
			      a.textContent = text + ' ' + time;
			      a.setAttribute('href', "./#/home/questions/" + id);
			      li.appendChild(a);
		      li.setAttribute("class", "list-group-item");
		      li.setAttribute("id", response[i].ID);

		      var button = document.createElement("button");
		      button.setAttribute("type", "buton");
		      button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-down");
		      button.setAttribute("data-loading-text", " ... ");
		      button.setAttribute("ng-click", "voteDown($event)");
		      li.appendChild(button);
		      
		      var button = document.createElement("button");
		      button.setAttribute("type", "buton");
		      button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-up");
		      button.setAttribute("data-loading-text", " ... ");
		      button.setAttribute("ng-click", "voteUp($event)");
		      li.appendChild(button);
		      
		      var span = document.createElement("span");
		      span.appendChild(document.createTextNode(likes));
		      span.setAttribute("class", "badge");
		      li.appendChild(span);
		      li.appendChild(document.createTextNode(text + ' ' + time ));
		      ul.appendChild(li);
		      
		}
		$compile(ul)($scope);
		
		var pageNum = { "pageNumber": pageNummberInt };
		// Put the object into storage
		localStorage.setItem('pageNum', JSON.stringify(pageNum));
	}
	
	$scope.voteUp = function ($event) {
	    
	    var retrievedPage = localStorage.getItem('pageNum');
		
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		
		var questionId = angular.element($event.currentTarget).parent('li').attr("id");//$(this );
	    var data =  JSON.stringify("1,"+ questionId);
	    
	    	$http(
	    			{
	    				method: 'POST',
	    				url: 'newestquestionsservlet',
	    				headers: {'Content-Type': 'application/json'},
	    				data:  JSON.stringify(data)
	    			}).success( function (response)
	    			{	
	    				localStorage.setItem('response', JSON.stringify(response));
	    				
	    				var ul = document.getElementById("newQuestionsList");
	    				ul.innerHTML = "";	
	    				for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
	    				{
	    				      var text = response[i].Text;
				var d= new Date(response[i].Time);
				  var time = d.toLocaleTimeString("en-us", options);
			      
			      var likes = response[i].Likes;
				 var id = response[i].ID;
			      
			      var a = document.createElement("a");
			      
			      
			      
			      
			      var li = document.createElement("li");
			      a.textContent = text + ' ' + time;
			      a.setAttribute('href', "./#/home/questions/" + id);
			      li.appendChild(a);
	    				      
	    				      li.setAttribute("class", "list-group-item");
	    				      li.setAttribute("id", response[i].ID);

	    				      var button = document.createElement("button");
	    				      button.setAttribute("type", "buton");
	    				      button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-down");
	    				      button.setAttribute("data-loading-text", " ... ");
	    				      button.setAttribute("ng-click", "voteDown($event)");
	    				      li.appendChild(button);
	    				      
	    				      var button = document.createElement("button");
	    				      button.setAttribute("type", "buton");
	    				      button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-up");
	    				      button.setAttribute("data-loading-text", " ... ");
	    				      button.setAttribute("ng-click", "voteUp($event)");
	    				      li.appendChild(button);
	    				      
	    				      var span = document.createElement("span");
	    				      span.appendChild(document.createTextNode(likes));
	    				      span.setAttribute("class", "badge");
	    				      li.appendChild(span);
	    				      li.appendChild(document.createTextNode(text + ' ' + time ));
	    				      ul.appendChild(li);
	    				      
	    				}
	    				$compile(ul)($scope);
	    			});
	    
	    
	 }

	$scope.voteDown = function ($event) {
		var retrievedPage = localStorage.getItem('pageNum');
		
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		
		var questionId = angular.element($event.currentTarget).parent('li').attr("id");//$(this ).parent().attr("id");
	    //var data = { "upVote":0 , "questionID" : questionId};
		var data =  JSON.stringify("0,"+ questionId);
		
	    	$http(
	    			{
	    				method: 'POST',
	    				url: 'newestquestionsservlet',
	    				headers: {'Content-Type': 'application/json'},
	    				data:  JSON.stringify(data)
	    			}).success( function (response)
	    			{	
	    				localStorage.setItem('response', JSON.stringify(response));
	    				
	    				var ul = document.getElementById("newQuestionsList");
	    				ul.innerHTML = "";	
	    				for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
	    				{
	    				      var text = response[i].Text;
				var d= new Date(response[i].Time);
				  var time = d.toLocaleTimeString("en-us", options);
			      
			      var likes = response[i].Likes;
				 var id = response[i].ID;
			      
			      var a = document.createElement("a");
			      
			      
			      
			      
			      var li = document.createElement("li");
			      a.textContent = text + ' ' + time;
			      a.setAttribute('href', "./#/home/questions/" + id);
			      li.appendChild(a);
	    				      li.setAttribute("class", "list-group-item");
	    				      li.setAttribute("id", response[i].ID);

	    				      var button = document.createElement("button");
	    				      button.setAttribute("type", "buton");
	    				      button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-down");
	    				      button.setAttribute("data-loading-text", " ... ");
	    				      button.setAttribute("ng-click", "voteDown($event)");
	    				      li.appendChild(button);
	    				      
	    				      var button = document.createElement("button");
	    				      button.setAttribute("type", "buton");
	    				      button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-up");
	    				      button.setAttribute("data-loading-text", " ... ");
	    				      button.setAttribute("ng-click", "voteUp($event)");
	    				      li.appendChild(button);
	    				      
	    				      var span = document.createElement("span");
	    				      span.appendChild(document.createTextNode(likes));
	    				      span.setAttribute("class", "badge");
	    				      li.appendChild(span);
	    				      li.appendChild(document.createTextNode(text + ' ' + time ));
	    				      ul.appendChild(li);
	    				      
	    				}
	    				$compile(ul)($scope);
	    			});
	    
	    
	 }
	
	
});


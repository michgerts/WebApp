// Front end js
app.controller('NewestQuestions',function ($scope, $http, $window, $compile)
{
	var table = $("#newQuestionsList > tbody");
	$scope.initNewest = function ()
	{
		$http(
		{
			method: 'get',
			url: 'newestquestionsservlet',
			headers: {'Content-Type': 'application/json'}
		}).success( function (response)
		{			
			var pageNum = { "pageNumber": 0 };
			// Put the object into storage
			localStorage.setItem('pageNum', JSON.stringify(pageNum));
			localStorage.setItem('response', JSON.stringify(response));
			for(var i=0; i<20 && i<response.length; i++)
			{
				table = $("#newQuestionsList > tbody:last-child");
				listItem(response[i], table);
			}
			$compile(table)($scope);		
		});
	}
	
	$scope.next = function()
	{
		table = $("#newQuestionsList > tbody");
		var retrievedPage = localStorage.getItem('pageNum');
		var retrievedResponse = localStorage.getItem('response');
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		pageNummberInt++;
		var response = JSON.parse(retrievedResponse);
		table.empty();
		table = $("#newQuestionsList > tbody:last-child");
		tableHeaders(table);
		for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
		{
			table = $("#newQuestionsList > tbody:last-child");
			listItem(response[i], table);
		}
		$compile(table)($scope);
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
		table.empty();
		table = $("#newQuestionsList > tbody:last-child");
		tableHeaders(table);
		for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
		{
			table = $("#newQuestionsList > tbody:last-child");
			listItem(response[i], table);
		}
		$compile(table)($scope);
		var pageNum = { "pageNumber": pageNummberInt };
		// Put the object into storage
		localStorage.setItem('pageNum', JSON.stringify(pageNum));
	}
	
	$scope.voteUp = function ($event) {
	    var retrievedPage = localStorage.getItem('pageNum');		
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;	
		var questionId = angular.element($event.currentTarget).parent().parent('tr').attr("id");
	    var data =  JSON.stringify("1,"+ questionId);
	    var questionAsker =angular.element($event.currentTarget).parent().parent('tr').attr("class");
	    
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
				    //if (getCookie("id") != questionAsker)
				    if(userIdFromSession != questionAsker)
				    {
				    	$http(
				    			{
				    				method: 'POST',
				    				url: 'newestquestionsservlet',
				    				headers: {'Content-Type': 'application/json'},
				    				data:  JSON.stringify(data)
				    			}).success( function (response)
				    			{	
				    				localStorage.setItem('response', JSON.stringify(response));
				    				table.empty();
				    				table = $("#newQuestionsList > tbody:last-child");
				    				tableHeaders(table);
				    				for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
				    				{
				    					table = $("#newQuestionsList > tbody:last-child");
				    					listItem(response[i], table);
				    				}
				    				$compile(table)($scope);
				    				$scope.$emit("UpdateFromNew");
				    			});
				    }
				});
	 }
	$scope.voteDown = function ($event) {
		var retrievedPage = localStorage.getItem('pageNum');
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		var questionId = angular.element($event.currentTarget).parent().parent('tr').attr("id");
		var data =  JSON.stringify("0,"+ questionId);
		var questionAsker =angular.element($event.currentTarget).parent().parent('tr').attr("class");
		
		var userIDFROMSession;
		$http(
				{
					method: 'get',
					url: 'useridservlet',
					headers: {'Content-Type': 'application/json'}
				}).success( function (response)
				{			
					userIDFROMSession = response;
		
					//if (getCookie("id") != questionAsker)
					if (userIDFROMSession != questionAsker)
					{
				    	$http(
				    			{
				    				method: 'POST',
				    				url: 'newestquestionsservlet',
				    				headers: {'Content-Type': 'application/json'},
				    				data:  JSON.stringify(data)
				    			}).success( function (response)
				    			{	
				    				localStorage.setItem('response', JSON.stringify(response));
				    				table.empty();
				    				table = $("#newQuestionsList > tbody:last-child");
				    				tableHeaders(table);
				    				for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
				    				{
				    					table = $("#newQuestionsList > tbody:last-child");
				    					listItem(response[i], table);
				    				}
				    				$compile(table)($scope);
				    				$scope.$emit("UpdateFromNew");
				    			});  
					}
				});
	}
	$scope.$on("UpdateFromAllB", function (event, args)
	{
		table = $("#newQuestionsList > tbody"); 
		table.empty();
		$scope.initNewest();
	});
	
});

function listItem(response, table)
{
    var text = response.Text;
	var time = formatDate(response.Time);	      
    var likes = response.Rating;
    var id = response.ID;   
    var a = document.createElement("a");
    var tr = document.createElement("tr");
    var td = document.createElement("td");
    a.textContent = text;
    a.setAttribute('href', "./#/home/questions/" + id);
    td.appendChild(a);
    //td.setAttribute("class", "list-group-item");
    tr.setAttribute("id", response.ID);
    tr.setAttribute("class", response.Asker);
    tr.appendChild(td);
    td = document.createElement("td");
    td.appendChild(document.createTextNode(' ' + time ));
    tr.appendChild(td);
    td = document.createElement("td");
    var span = document.createElement("span");
    span.appendChild(document.createTextNode(likes));
    span.setAttribute("class", "badge");
    td.appendChild(span);
    tr.appendChild(td);
    var button = document.createElement("button");
    button.setAttribute("type", "buton");
    button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-down");
    button.setAttribute("data-loading-text", " ... ");
    button.setAttribute("ng-click", "voteDown($event)");
    td = document.createElement("td");
    td.appendChild(button);  
    tr.appendChild(td);
    var button = document.createElement("button");
    button.setAttribute("type", "buton");
    button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-up");
    button.setAttribute("data-loading-text", " ... ");
    button.setAttribute("ng-click", "voteUp($event)");
    td.appendChild(button);  
    tr.appendChild(td);
    table.append(tr);
}

function tableHeaders(table)
{
	 table.append( "<th>Question's text</th><th>Time of submission</th><th>Rating</th><th></th>");
}


// Front end js
app.controller('AllQuestionsOnTopic',function ($scope, $http, $window, $compile)
{
	
	var Topic=  path.split("/")[7];
	var table = $("#allQuestionsontopicList > tbody");
	$scope.init = function ()
	{
		$http(
		{
			method: 'post',
			url: 'allquestionsontopicservlet',
			headers: {'Content-Type': 'application/json'},
			data:  JSON.stringify(Topic)
		}).success( function (response)
		{		
			
			var pageNum = { "pageNumber": 0 };
			// Put the object into storage
			
			$scope.pageNumS = JSON.stringify(pageNum);
			$scope.responseS = JSON.stringify(response);
			for(var i=0; i<20 && i<response.length; i++)
			{
				table = $("#allQuestionsontopicList > tbody:last-child");
				listItemAll(response[i], table);
			}
			$compile(table)($scope);		
		});
	}
	
	$scope.nextAll = function()
	{
		table = $("#allQuestionsontopicList > tbody");
		var retrievedPage = $scope.pageNumS;
		var retrievedResponse = $scope.responseS;
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		pageNummberInt++;
		var response = JSON.parse(retrievedResponse);
		table.empty();
		table = $("#allQuestionsontopicList > tbody:last-child");
		tableHeaders(table);
		for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
		{
			table = $("#allQuestionsontopicList > tbody:last-child");
			listItemAll(response[i], table);
		}
		$compile(table)($scope);
		var pageNum = { "pageNumber": pageNummberInt };
		$scope.pageNumS = JSON.stringify(pageNum);
	}
	$scope.prevAll = function()
	{
		var retrievedPage = $scope.pageNumS;
		var retrievedResponse = $scope.responseS; 
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		pageNummberInt--;
		var response = JSON.parse(retrievedResponse);
		table.empty();
		table = $("#allQuestionsontopicList > tbody:last-child");
		tableHeaders(table);
		for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
		{
			table = $("##allQuestionsontopicList > tbody:last-child");
			listItemAll(response[i], table);
		}
		$compile(table)($scope);
		var pageNum = { "pageNumber": pageNummberInt };
		// Put the object into storage
		$scope.pageNumS = JSON.stringify(pageNum);
	}
	
	$scope.voteUpAllQuestionsOnTopic = function ($event) {
		var retrievedPage = $scope.pageNumS;		
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
		    				url: 'allquestionsontopicservlet',
		    				headers: {'Content-Type': 'application/json'},
		    				data:  JSON.stringify(data)
		    			}).success( function (response)
		    			{	
		    				$scope.responseS = JSON.stringify(response);
		    				table.empty();
		    				table = $("#allQuestionsList > tbody:last-child");
		    				tableHeaders(table);
		    				for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
		    				{
		    					table = $("#allQuestionsList > tbody:last-child");
		    					listItemAll(response[i], table);
		    				}
		    				$compile(table)($scope);
		    				$scope.$emit("UpdateFromAll");
		    			});
		    }
		});
	 }
	$scope.voteDownAllQuestionsOnTopic = function ($event) {
		var retrievedPage = $scope.pageNumS;
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		var questionId = angular.element($event.currentTarget).parent().parent('tr').attr("id");
		var data =  JSON.stringify("0,"+ questionId);
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
		    				url: 'allquestionsontopicservlet',
		    				headers: {'Content-Type': 'application/json'},
		    				data:  JSON.stringify(data)
		    			}).success( function (response)
		    			{	
		    				$scope.responseS = JSON.stringify(response);
		    				table.empty();
		    				table = $("#allQuestionsOnTopicList > tbody:last-child");
		    				tableHeaders(table);
		    				for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
		    				{
		    					table = $("#allQuestionsOnTopicList > tbody:last-child");
		    					listItemAll(response[i], table);
		    				}
		    				$compile(table)($scope);
		    				$scope.$emit("UpdateFromAll");
		    			});
			}

		});
	}
    $scope.$on("UpdateFromNewB", function (event, args)
    		{
    			table = $("#allQuestionsOnTopicList > tbody"); 
    			table.empty();
    			$scope.init();
    		});
});

function listItemAllQuestionsOnTopic(response, table)
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
    button.setAttribute("ng-click", "voteDownAll($event)");
    td = document.createElement("td");
    td.appendChild(button);  
    tr.appendChild(td);
    var button = document.createElement("button");
    button.setAttribute("type", "buton");
    button.setAttribute("class", "btn btn-success glyphicon glyphicon-thumbs-up");
    button.setAttribute("data-loading-text", " ... ");
    button.setAttribute("ng-click", "voteUpAll($event)");
    td.appendChild(button);  
    tr.appendChild(td);
    table.append(tr);
}

function tableHeadersAllQuestionsOnTopic(table)
{
	 table.append( "<th>Question's text</th><th>Time of submission</th><th>Rating</th><th></th>");
}

// Front end js
app.controller('AllTopics',function ($scope, $http, $window, $compile)
{
	var table = $("#allTopicssList > tbody");
	$scope.initTopics = function ()
	{
		$http(
		{
			method: 'get',
			url: 'alltopicsservlet',
			headers: {'Content-Type': 'application/json'}
		}).success( function (response)
		{			
			var pageNum = { "pageNumber": 0 };
			// Put the object into storage
			$scope.pageNumS = JSON.stringify(pageNum);//localStorage.setItem('pageNum', JSON.stringify(pageNum));
			$scope.responseTopicsS = JSON.stringify(response);// = localStorage.setItem('responseTopics', JSON.stringify(response));
			for(var i=0; i<20 && i<response.length; i++)
			{
				table = $("#allTopicsList > tbody:last-child");
				listItemAllTopics(response[i], table);
			}
			$compile(table)($scope);		
		});
	}
	
	$scope.nextAllTopics = function()
	{
		table = $("#allTopicsList > tbody");
		var retrievedPage = $scope.pageNumS;//= localStorage.getItem('pageNum');
		var retrievedResponse = $scope.responseTopicsS;//= localStorage.getItem('responseTopics');
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		pageNummberInt++;
		var response = JSON.parse(retrievedResponse);
		if(pageNummberInt*20< response.length)
		{
			table.empty();
			table = $("#allTopicsList > tbody:last-child");
			tableHeaders(table);
			for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
			{
				table = $("#allTopicsList > tbody:last-child");
				listItemAllTopics(response[i], table);
			}
		}
		else
			pageNummberInt--;
		$compile(table)($scope);
		var pageNum = { "pageNumber": pageNummberInt };
		// Put the object into storage
		$scope.pageNumS = JSON.stringify(pageNum);//localStorage.setItem('pageNum', JSON.stringify(pageNum));
	}
	$scope.prevAllTopics = function()
	{
		var retrievedPage = $scope.pageNumS;//= localStorage.getItem('pageNum');
		var retrievedResponse = $scope.responseTopicsS;//= localStorage.getItem('responseTopics');
		var pageNumberStr = JSON.parse(retrievedPage);
		var pageNummberInt = pageNumberStr.pageNumber;
		pageNummberInt--;
		var response = JSON.parse(retrievedResponse);
		if(pageNummberInt>=0)
		{
			table.empty();
			table = $("#allTopicsList > tbody:last-child");
			tableHeaders(table);
			for(var i=pageNummberInt*20; i<pageNummberInt*20+20 && i<response.length; i++)
			{
				table = $("#allTopicsList > tbody:last-child");
				listItemAllTopics(response[i], table);
			}
		}
		else
			pageNummberInt++;
		$compile(table)($scope);
		var pageNum = { "pageNumber": pageNummberInt };
		// Put the object into storage
		$scope.pageNumS = JSON.stringify(pageNum);// localStorage.setItem('pageNum', JSON.stringify(pageNum));
	}
	$scope.$on("UpdateFromAllB", function (event, args)
			{
				table = $("#allTopicsList > tbody:last-child");
				table.empty();
				$scope.initTopics();
			});
	$scope.$on("UpdateFromAllB", function (event, args)
			{
				table = $("#allTopicsList > tbody:last-child");
				table.empty();
				$scope.initTopics();
			});
});
function listItemAllTopics(response, table)
{
    var topic = response.Topic;	      
    var rating= response.Rating;
    var a = document.createElement("a");
    var tr = document.createElement("tr");
    var td = document.createElement("td");
    a.textContent = topic;
    a.setAttribute('href', "./#/home/topics/" + topic);
    td.appendChild(a);
    tr.setAttribute("id", topic);
    tr.appendChild(td);
    td = document.createElement("td");
    var span = document.createElement("span");
    span.appendChild(document.createTextNode(rating));
    span.setAttribute("class", "badge");
    td.appendChild(span);
    tr.appendChild(td);
    table.append(tr);
}

function tableHeadersAllTopics(table)
{
	 table.append( "<th>Topic</th><th>Rating</th><th></th>");
}

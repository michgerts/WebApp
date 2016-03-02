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
		    span.setAttribute("class", "label label-default topicLabels");
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
		
		$scope.thisUser = {Pic: userToPresent.user.Pic,Nickname: userToPresent.user.NickName,Description: userToPresent.user.Description,Rating: userToPresent.rating,Expertise: userToPresent.fiveTopTopics,FiveLastQuestions: userToPresent.askedQuestions, Name: userToPresent.user.Name};
		//UserLastFiveQuestions
		
		var fiveQuestionTable = $("#UserLastFiveQuestions");
		
		
	    //var id = response.ID;   
	    
	    for(var j=0; j<userToPresent.askedQuestions.length; j++)
		{
	    	var text = userToPresent.askedQuestions[j].Text;
			var time = formatDate(userToPresent.askedQuestions[j].Time);	      
		    var likes = userToPresent.askedQuestions[j].Likes;
		    var topics = userToPresent.askedQuestions[j].Topics;
		    
	    	var tr = document.createElement("tr");
		    var td = document.createElement("td");
		    td.appendChild(document.createTextNode(' ' + time ));
		    tr.appendChild(td);
		    td = document.createElement("td");
		    td.appendChild(document.createTextNode(' ' + text ));
		    tr.appendChild(td);
		    
		    td = document.createElement("td");
		    for(var h=0; h<topics.length; h++)
			{
		    	var span = document.createElement("span");
			    span.appendChild(document.createTextNode(topics[h]));
			    span.setAttribute("class", "label label-default topicLabels");
			    td.appendChild(span);
			}
		    tr.appendChild(td);
		    
		    td = document.createElement("td");
		    td.appendChild(document.createTextNode(' '+ likes));
		    tr.appendChild(td);
		    fiveQuestionTable.append(tr);
		}
	    
	    
	    var userExpertise = $("#userExpertise");
	    
	    
	    for(var k=0; k<userToPresent.fiveTopTopics.length; k++)
		{
		    var span = document.createElement("span");
		    span.appendChild(document.createTextNode(userToPresent.fiveTopTopics[k]));
		    span.setAttribute("class", "label label-default topicLabels");
		    userExpertise.append(span);
		}
	    
	    
	    var thisUserFiveLastAnswers = userToPresent.userAnsweredQuestions;
	    
	    var userAnsTable = $("#userAnswersTable");
		
	    //var id = response.ID;   
	    
	    for(var p=0; p<thisUserFiveLastAnswers.length; p++)
		{
	    	var question = thisUserFiveLastAnswers[p].Question;
	    	var Qtext = question.Text;
	    	var Qtime = formatDate(question.Time);
	    	var Qrating = question.Likes;
	    	var Qtopics = thisUserFiveLastAnswers[p].Topics;
	    	var Atext = thisUserFiveLastAnswers[p].UserAnswerText;
	    	var Arating = thisUserFiveLastAnswers[p].UserAnswerRating;
	    	
	    	var tr = document.createElement("tr");
		    var td = document.createElement("td");
		    td.appendChild(document.createTextNode(' ' + Qtime ));
		    tr.appendChild(td);
		    td = document.createElement("td");
		    td.appendChild(document.createTextNode(' ' + Qtext ));
		    tr.appendChild(td);
		    
		    td = document.createElement("td");
		    for(var h=0; h<topics.length; h++)
			{
		    	var span = document.createElement("span");
			    span.appendChild(document.createTextNode(Qtopics[h]));
			    span.setAttribute("class", "label label-default topicLabels");
			    td.appendChild(span);
			}
		    tr.appendChild(td);
		    
		    td = document.createElement("td");
		    td.appendChild(document.createTextNode(' '+ Qrating));
		    tr.appendChild(td);
		    userAnsTable.append(tr);
		    
		    //setting user's answer details
		    var tr = document.createElement("tr");
		    var td = document.createElement("td");
		    td.setAttribute("colspan", "3");
		    td.appendChild(document.createTextNode("User's answer: " + Atext ));
		    tr.appendChild(td);
		    var td = document.createElement("td");
		    td.setAttribute("colspan", "1");
		    td.appendChild(document.createTextNode("Answer rating " + Arating ));
		    tr.appendChild(td);
		    
		    userAnsTable.append(tr);
		}
	}
	
	
});
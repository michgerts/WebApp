package WebAppPkg;
 
import java.io.BufferedReader;
import java.io.IOException;

// SQL
import WebAppPkg.WebAppDB;
//import jdk.nashorn.internal.parser.JSONParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

// Servlet
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

// JSON
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Data types
import WebAppPkg.Question;
 
public class LeaderboardServlet extends HttpServlet
{//this will submit an answer -- need to change the name
	private static final long serialVersionUID = 1L;
    private String QTableName = "QUESTIONS";
    private String UTableName = "USERS";
    private String TTableName = "TOPICS ";
    private String ATableName = "ANSWERS ";
    private int page=0;
    @SuppressWarnings({ "deprecation", "unchecked" })
	@Override
   
   public void doGet (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {    	
    	try
    	{
    		WebAppDB db = new WebAppDB();
    		db.createConnection(); 
    		ArrayList<UserProfile> usersProfiles = new ArrayList<UserProfile>();
    	

    		ResultSet avgLikesPerUser =  db.executeQuery("select QUES.Name, ANS.AVGALIKES, QUES.AVGQLIKES from (SELECT Avg( Cast (Q.Likes AS DOUBLE PRECISION)) as AVGQLIKES,  U.Name  FROM QUESTIONS as Q, USERS as U where U.Name = Q.Asker group by U.Name order by avgQLikes) as QUES left outer join (SELECT Avg(A.Likes) as AVGALIKES,  A.UID FROM ANSWERS as A, USERS as U where U.Name = A.UID group by A.UID order by AVGALIKES) as ANS on ANS.UID = QUES.Name");
    				
    		while (avgLikesPerUser.next())
    		{
    			UserProfile userP = new UserProfile();
    			String avgA = avgLikesPerUser.getString("AVGALIKES");//Average likes of answers this user answered
    			String avgQ = avgLikesPerUser.getString("AVGQLIKES");//Average likes of Questions this user asked
            	String userName = avgLikesPerUser.getString("Name");
            	
            	float avgAFloat = 0;
            	if(!(avgA == null || avgA.equals("")))
            		avgAFloat = Float.parseFloat(avgA);
            	float avgQFloat = Float.parseFloat(avgQ);

            	float rating = (float) (0.2*avgQFloat +0.8*avgAFloat);
            	
            	userP.setRating(rating);
            	
            	ResultSet userInfo = db.executeQuery("select Pic, Nickname, Description from USERS where Name = '"+userName+"'");//get user's picture and nickname
        		String pic = "";
        		String nick = "";
        		String desc = "";
        		if(userInfo.next())
        		{
        			pic = userInfo.getString("Pic");
        			nick = userInfo.getString("Nickname");
        			desc = userInfo.getString("Description");
        		}
        			
        		User user = new User();
            	user.setName(userName);
            	user.setNickName(nick);
            	user.setPic(pic);
            	user.setDescription(desc);
            	userP.setUser(user);
            	
            	//setting the expertise
            	ResultSet userExpertise = db.executeQuery("select A.QID, A.UID, A.Likes, T.Topic from ANSWERS as A, TOPICS as T where A.QID = T.QID and A.UID = '"+userName+"' order by Likes desc");
            	String topic;
            	List<String> fiveTopTopics = new ArrayList<String>(); 
            	int j=0;
            	while(userExpertise.next() && j<5)
        		{
            		topic = userExpertise.getString("Topic");
            		if(!topicExistsInExpertise(topic, (ArrayList<String>)fiveTopTopics))
            		{
            			fiveTopTopics.add(topic);
            			j++;
            		}
            			
            		
        		}
            	
            	userP.setTopFiveTopics(fiveTopTopics);
            	
            	
            	//setting 5 last asked questions
            	ResultSet userAskedQuestions = db.executeQuery("select Q.ID, Q.Time, Q.Text, Q.Likes from QUESTIONS as Q where Q.Asker   = '"+userName+"' order by Time desc");
            	List<Question> askedQuestions = new ArrayList<Question>(); 

            	String time, text, likes;
            	int k=0;
            	while(userAskedQuestions.next() && k<5)
        		{
            		Question question = new Question();
            		time = userAskedQuestions.getString("Time");
            		text = userAskedQuestions.getString("Text");
            		likes = userAskedQuestions.getString("Likes");
            		int qid = Integer.parseInt(userAskedQuestions.getString("ID"));
            		question.setText(text);
            		question.setTime(time);
            		question.setLikes(Integer.parseInt(likes));
            		
            		
            		//get all of this question's topics
            		ResultSet questionTopics = db.executeQuery("select * from TOPICS as T where T.QID = "+qid);
                	List<String> qTopics = new ArrayList<String>(); 
                	String qTopic;
                	while(questionTopics.next())
            		{
                		qTopic = questionTopics.getString("Topic");
                		qTopics.add(qTopic);
            		}
                	
                	question.setTopics(qTopics);
                	askedQuestions.add(question);
            		k++;
        		}
            	
            	userP.setAskedQuestions(askedQuestions);
            	     	
            	
            	//setting 5 last answers and their questions
            	ResultSet userAnsweredQuestionsSQL = db.executeQuery("select A.Time as ATIME, A.Text as ATEXT, A.Likes as ALIKES, Q.Time as QTIME, Q.Text as QTEXT, Q.Likes as QLIKES, Q.ID as QID from ANSWERS as A join QUESTIONS as Q on A.QID = Q.ID where A.UID  = '"+userName+"' order by A.Time desc");
            	List<UserAnsweredQuestion> userAnsweredQuestions = new ArrayList<UserAnsweredQuestion>();
            	Question userAnsweredQ;
            	int t=0;
            	while(userAnsweredQuestionsSQL.next() && t<5)
        		{
            		UserAnsweredQuestion userAnsweredQInfo = new UserAnsweredQuestion();
            		userAnsweredQInfo.setUserAnswerText(userAnsweredQuestionsSQL.getString("ATEXT"));
            		userAnsweredQInfo.setUserAnswerRating(Integer.parseInt(userAnsweredQuestionsSQL.getString("ALIKES")));
            		userAnsweredQ = new Question();
            		userAnsweredQ.setTime(userAnsweredQuestionsSQL.getString("QTIME"));
            		userAnsweredQ.setText(userAnsweredQuestionsSQL.getString("QTEXT"));
            		userAnsweredQ.setLikes(Integer.parseInt(userAnsweredQuestionsSQL.getString("QLIKES")));
            		userAnsweredQ.setID(Integer.parseInt(userAnsweredQuestionsSQL.getString("QID")));
            		userAnsweredQInfo.setQuestion(userAnsweredQ);
            		
            		ResultSet uAQtopics = db.executeQuery("select * from TOPICS as T where T.QID = "+userAnsweredQ.getID());
            		List<String> uAQtopicsList = new ArrayList<String>(); 
                	String uAQtopic;
                	while(uAQtopics.next())
            		{
                		uAQtopic = uAQtopics.getString("Topic");
                		uAQtopicsList.add(uAQtopic);
            		}
                	userAnsweredQInfo.setTopics(uAQtopicsList);
                	
            		userAnsweredQuestions.add(userAnsweredQInfo);
            		t++;
        		}
            	
            	userP.setUserAnsweredQuestions(userAnsweredQuestions);
            	
            	usersProfiles.add(userP);
    		}
    		
    		//add all other users that have no rating (didn't answer or ask anything)
    		ResultSet allUsers = db.executeQuery("select * from USERS");
    		while (allUsers.next())
    		{
    			String userName = allUsers.getString("Name");
    			if(nameExistsInUP(userName, usersProfiles))
    			{
    				String picture = allUsers.getString("Pic");
            		String nickname = allUsers.getString("Nickname");
            		String description = allUsers.getString("Description");

    				UserProfile userProfile = new UserProfile();
    				User user = new User();
                	user.setName(userName);
                	user.setNickName(nickname);
                	user.setPic(picture);
                	user.setDescription(description);
                	userProfile.setUser(user);
                	usersProfiles.add(userProfile);
    			}
    		}
    		
            Collections.sort(usersProfiles);
    		
            String categoriesJson = new Gson().toJson(usersProfiles);
            response.setContentType("application/json");
	    	response.setCharacterEncoding("UTF-8");
            response.getWriter().write(categoriesJson);
			response.getWriter().close();
			//db.closeConnection();
			}
    	catch (IOException | NumberFormatException | SQLException e)
    	{
			e.printStackTrace();
		} 
    }
    
    private boolean nameExistsInUP(String name, ArrayList<UserProfile> usersProfiles)
    {
    	Iterator<UserProfile> iterator = usersProfiles.iterator();
        while (iterator.hasNext()) {
        	UserProfile currUP = iterator.next();
            if (currUP.getUser().getName().equals(name)) {
            	return false;
        	}
        }
		return true;
    	
    }
    
    private boolean topicExistsInExpertise(String name, ArrayList<String> topics)
    {
    	Iterator<String> iterator = topics.iterator();
        while (iterator.hasNext()) {
        	String currTopic = iterator.next();
            if (currTopic.equals(name)) {
            	return true;
        	}
        }
		return false;
    	
    }
    

}
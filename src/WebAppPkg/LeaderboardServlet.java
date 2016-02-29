package WebAppPkg;
 
import java.io.BufferedReader;
import java.io.IOException;

// SQL
import WebAppPkg.WebAppDB;
//import jdk.nashorn.internal.parser.JSONParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Servlet
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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
    @SuppressWarnings("deprecation")
	@Override
   
   public void doGet (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {    	
    	try
    	{
    		WebAppDB db = new WebAppDB();
    		db.createConnection(); 
    		ArrayList<UserProfile> usersProfiles = new ArrayList<UserProfile>();
    	

    		ResultSet avgLikesPerUser =  db.executeQuery("select ANS.UID, ANS.AVGALIKES, QUES.AVGQLIKES from "
    				+ "(SELECT Avg(Q.Likes) as AVGQLIKES,  U.Name  FROM QUESTIONS as Q, USERS as U where U.Name = Q.Asker group by U.Name order by avgQLikes) as QUES "
    				+ "left outer join (SELECT Avg(A.Likes) as AVGALIKES,  A.UID FROM ANSWERS as A, USERS as U where U.Name = A.UID group by A.UID order by AVGALIKES)"
    				+ " as ANS on ANS.UID = QUES.Name;");
    		
    		while (avgLikesPerUser.next())
    		{
    			UserProfile userP = new UserProfile();
    			int avgA = Integer.parseInt(avgLikesPerUser.getString("AVGALIKES"));//Average likes of answers this user answered
    			int avgQ = Integer.parseInt(avgLikesPerUser.getString("AVGQLIKES"));//Average likes of Questions this user asked
            	String userName = avgLikesPerUser.getString("UID");
            	
            	float rating = (float) (0.2*avgQ +0.8*avgA);
            	
            	userP.setRating(rating);
            	
            	ResultSet userInfo = db.executeQuery("select Pic, Nickname from USERS where Name = "+userName);//get user's picture and nickname
        		String pic = "";
        		String nick = "";
        		if(userInfo.next())
        		{
        			pic = userInfo.getString("Pic");
        			nick = userInfo.getString("Nickname");
        		}
        			
        		User user = new User();
            	user.setName(userName);
            	user.setNickName(nick);
            	user.setPic(pic);
            	userP.setUser(user);
            	
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

    				UserProfile userProfile = new UserProfile();
    				User user = new User();
                	user.setName(userName);
                	user.setNickName(nickname);
                	user.setPic(picture);
                	userProfile.setUser(user);
                	usersProfiles.add(userProfile);
    			}
    		}
    		
            
            String categoriesJson = new Gson().toJson(usersProfiles);
            response.setContentType("application/json");
	    	response.setCharacterEncoding("UTF-8");
            response.getWriter().write(categoriesJson);
			response.getWriter().close();
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
    
	@Override
	   
	   public void doPost (HttpServletRequest request, HttpServletResponse response)
	 		   throws IOException, ServletException
	    {    	
	    	try
	    	{
	    		WebAppDB db = new WebAppDB();
	    		ResultSet questions;
	    		db.createConnection(); 
	    		List<Question> questionsToPresent = new ArrayList<Question>();
	    	
	    		StringBuilder sb = new StringBuilder();
	            BufferedReader br = request.getReader();
	            String str;
	            String upVotestr = null;
	            String idstr = null;
	            while ((str = br.readLine()) != null)
	            	sb.append(str);
	            
	            
	            String data = new Gson().fromJson(sb.toString(),String.class);
	            upVotestr = data.substring(1, 2);
	            idstr = data.substring(3, data.length()-1);
	            
	            Integer id = Integer.parseInt(idstr);
	            Integer upVote = Integer.parseInt(upVotestr);
	            
	            ResultSet numOfLikesSet = db.executeQuery("select Likes as A from QUESTIONS where ID = "+ id.intValue());
	    		String numOfLikesStr = "";
	    		if(numOfLikesSet.next())
	    			numOfLikesStr = numOfLikesSet.getString("A");
	    		int numOfLikesInt = Integer.parseInt(numOfLikesStr);//numOfLikesInt contains the number of likes at the current moment
	    		
	    		if(upVote == 1)
	    			numOfLikesInt++;
	    		else if(upVote == 0)
	    			numOfLikesInt--;
	    		
    			if(upVote != null && id != null)
    			{
    				db.executeUpdate("update QUESTIONS set likes ="+ numOfLikesInt +"where id = " + id.intValue());
    			}
            
    			
    			questions = db.executeQuery("SELECT * FROM "+ "tableName"+ " where answered=false order by time desc");
                
                while (questions.next())
        		{
                		Question question = new Question( Integer.parseInt(questions.getString("ID")), questions.getString("Text"),
                        		questions.getString("Time"), questions.getString("Asker"), Integer.parseInt(questions.getString("Likes")) , 
                        		Boolean.parseBoolean(questions.getString("Answered")) );
                        questionsToPresent.add(question);
        		}
	                
	            String categoriesJson = new Gson().toJson(questionsToPresent);
	            response.setContentType("application/json");
		    	response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(categoriesJson);
				response.getWriter().close();
				}
	    	catch (IOException | SQLException e)
	    	{
				e.printStackTrace();
			} 
	    }
}

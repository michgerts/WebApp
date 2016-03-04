package WebAppPkg;
 
import java.io.BufferedReader;
import java.io.IOException;

// SQL
import WebAppPkg.WebAppDB;
//import jdk.nashorn.internal.parser.JSONParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
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
 
/** This is a servlet class that handles the newest questions information
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class NewestQuestionsServlet extends HttpServlet
{//this will submit an answer -- need to change the name
	private static final long serialVersionUID = 1L;
    private String tableName = "QUESTIONS";

	@Override
   
	/** This method generates and returns information about the unanswered questions in the system
	 * this will be displayed in the table presenting the newest questions.
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
   public void doGet (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {    	
    	try
    	{
    		WebAppDB db = new WebAppDB();
    		ResultSet questions1;
    		db.createConnection(); 
    		db.setAutoCommit();
    		List<Question> questionsToPresent = new ArrayList<Question>();
    	
            StringBuilder sb = new StringBuilder();
            BufferedReader br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }

    		   
			questions1 = db.executeQuery("SELECT * FROM "+ tableName+ " where answered=false order by time desc");
            
            
            while (questions1.next())
    		{
            		Question question = new Question( Integer.parseInt(questions1.getString("ID")), questions1.getString("Text"),
                    		questions1.getString("Time"), questions1.getString("Asker"), Integer.parseInt(questions1.getString("Likes")) , 
                    		Boolean.parseBoolean(questions1.getString("Answered")) );
	            	float rating = (float) 0.2*question.getLikes();
	            	question.setRating(rating);
	            	questionsToPresent.add(question);
    		}
            String categoriesJson = new Gson().toJson(questionsToPresent);
            response.setContentType("application/json");
	    	response.setCharacterEncoding("UTF-8");
            response.getWriter().write(categoriesJson);
            questions1.close();
			response.getWriter().close();
        	//db.closeConnection();
			}
    	catch (IOException | SQLException e)
    	{
			e.printStackTrace();
		} 
    }
    
		@Override
		/** This method is called when upVoting or downVoting a question in the 'newest questions' table.
		 * It updates the "like" number in the DB of this question and returns to the user the details of the questions in order
		 * to present them after the like update.
		 * @param  request  request from user
		 * @param  response response back send to the user
		 */
	   public void doPost (HttpServletRequest request, HttpServletResponse response)
	 		   throws IOException, ServletException
	    {    	
	    	try
	    	{
	    		WebAppDB db = new WebAppDB();
	    		ResultSet questions1;
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
            
    			
    			questions1 = db.executeQuery("SELECT * FROM "+ tableName+ " where answered=false order by time desc");
                
                while (questions1.next())
        		{
                		Question question = new Question( Integer.parseInt(questions1.getString("ID")), questions1.getString("Text"),
                        		questions1.getString("Time"), questions1.getString("Asker"), Integer.parseInt(questions1.getString("Likes")) , 
                        		Boolean.parseBoolean(questions1.getString("Answered")) );
    	            	float rating = (float) 0.2*question.getLikes();
    	            	question.setRating(rating);
                		questionsToPresent.add(question);
        		}
	                
	            String categoriesJson = new Gson().toJson(questionsToPresent);
	            response.setContentType("application/json");
		    	response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(categoriesJson);
	            questions1.close();
				response.getWriter().close();
				//db.closeConnection();
				}
	    	catch (IOException | SQLException e)
	    	{
				e.printStackTrace();
			} 
	    }
}
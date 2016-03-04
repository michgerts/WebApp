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
import java.util.List;

// Servlet
import javax.servlet.*;
import javax.servlet.http.*;
// JSON
import com.google.gson.Gson;
// Data types
import WebAppPkg.Question;
 
/** This class is a servlet class that handles the details of the questions (answered and unanswered)
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class AllQuestionsServlet extends HttpServlet
{//this will submit an answer -- need to change the name
	private static final long serialVersionUID = 1L;
    private String tableName = "QUESTIONS";


	
	@Override
	/** This method generates and returns  information about all of the questions in the system
	 *  (answered and unanswered).
	 * this will be displayed in the table presenting all of the questions.
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
   public void doGet (HttpServletRequest request, HttpServletResponse response)
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
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }

    		   
			questions = db.executeQuery("SELECT * FROM "+ tableName+ "  order by time desc");
            
            
            while (questions.next())
    		{
            		Question question = new Question( Integer.parseInt(questions.getString("ID")), questions.getString("Text"),
                    		questions.getString("Time"), questions.getString("Asker"), Integer.parseInt(questions.getString("Likes")) , 
                    		Boolean.parseBoolean(questions.getString("Answered")) );
            		ResultSet likesSet= db.executeQuery("SELECT AVG(Likes) AS Likes FROM Answers Where QID="+question.getID());
	            	int likes = 0;
	            	String numLikesStr = null;
	            	if(likesSet.next())
	            		numLikesStr = likesSet.getString("Likes");
	            		if  (numLikesStr!=null)
	            			likes = Integer.parseInt(likesSet.getString("Likes"));
	            	float rating = (float) (0.2*question.getLikes()+0.8*likes);
	            	question.setRating(rating);
            		questionsToPresent.add(question);
    		}
            Collections.sort(questionsToPresent);
            String categoriesJson = new Gson().toJson(questionsToPresent);
            response.setContentType("application/json");
	    	response.setCharacterEncoding("UTF-8");
            response.getWriter().write(categoriesJson);
			response.getWriter().close();
        	//db.closeConnection();
			}
    	catch (IOException | SQLException e)
    	{
			e.printStackTrace();
		} 
    }
    
	
	@Override
	/** This method is called when upVoting or downVoting a question in the 'all questions' table.
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
            
    			
    			questions = db.executeQuery("SELECT * FROM "+ tableName+ " order by time desc");
                
                while (questions.next())
        		{
                		Question question = new Question( Integer.parseInt(questions.getString("ID")), questions.getString("Text"),
                        		questions.getString("Time"), questions.getString("Asker"), Integer.parseInt(questions.getString("Likes")) , 
                        		Boolean.parseBoolean(questions.getString("Answered")) );
                		ResultSet likesSet= db.executeQuery("SELECT AVG(Likes) AS Likes FROM Answers Where QID="+question.getID());
    	            	int likes = 0;
    	            	String numLikesStr = null;
    	            	if(likesSet.next())
    	            		numLikesStr = likesSet.getString("Likes");
    	            		if  (numLikesStr!=null)
    	            			likes = Integer.parseInt(likesSet.getString("Likes"));
    	            	float rating = (float) (0.2*question.getLikes()+0.8*likes);
    	            	question.setRating(rating);
                		questionsToPresent.add(question);
        		}
                Collections.sort(questionsToPresent);
	            String categoriesJson = new Gson().toJson(questionsToPresent);
	            response.setContentType("application/json");
		    	response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(categoriesJson);
				response.getWriter().close();
				//db.closeConnection();
				}
	    	catch (IOException | SQLException e)
	    	{
				e.printStackTrace();
			} 
	    }
}
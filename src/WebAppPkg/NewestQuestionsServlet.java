package WebAppPkg;
 
import java.io.BufferedReader;
import java.io.IOException;

// SQL
import WebAppPkg.WebAppDB;
import jdk.nashorn.internal.parser.JSONParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
 
public class NewestQuestionsServlet extends HttpServlet
{//this will submit an answer -- need to change the name
	private static final long serialVersionUID = 1L;
    private String tableName = "QUESTIONS";
    private int page=0;
    @SuppressWarnings("deprecation")
	@Override
   
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
            StringBuffer requestURL = request.getRequestURL();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }

    		   
			questions = db.executeQuery("SELECT * FROM "+ tableName+ " where answered=false order by time desc");
            
            
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
            
    			
    			questions = db.executeQuery("SELECT * FROM "+ tableName+ " where answered=false order by time desc");
                
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

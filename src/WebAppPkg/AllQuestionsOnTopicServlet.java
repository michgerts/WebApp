package WebAppPkg;
 
import java.io.BufferedReader;
import java.io.IOException;

// SQL
import WebAppPkg.WebAppDB;
import WebAppPkg.TopicRating;
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
 
public class AllQuestionsOnTopicServlet extends HttpServlet
{//this will submit an answer -- need to change the name
	private static final long serialVersionUID = 1L;
    private String tableName = "TOPICS";
 
	@Override
	   
	   public void doPost (HttpServletRequest request, HttpServletResponse response)
	 		   throws IOException, ServletException
	    { 
			WebAppDB db = new WebAppDB();
			ResultSet questions = null;
			ResultSet questionsID = null;
	    	try
	    	{
	    		
	    		db.createConnection(); 
	    		List<Question> questionsToPresent = new ArrayList<Question>();	    	
	            StringBuilder sb = new StringBuilder();
	            BufferedReader br = request.getReader();
	            String str;
	            while ((str = br.readLine()) != null)
	            {
	                sb.append(str);
	            }
	            
	            String topic = new Gson().fromJson(sb.toString(),String.class);  
	            questionsID= db.executeQuery("SELECT QID AS ID FROM "+tableName+" Where topic='"+topic+"'");
    			while (questionsID.next())
        		{
        			questions= db.executeQuery("SELECT * FROM QUESTIONS WHERE ID="+questionsID.getInt("ID"));
        			questions.next();
        			Question question = new Question( Integer.parseInt(questions.getString("ID")), questions.getString("Text"),
            		questions.getString("Time"), questions.getString("Asker"), Integer.parseInt(questions.getString("Likes")) , 
            		Boolean.parseBoolean(questions.getString("Answered")) );
            		ResultSet likesSet= db.executeQuery("SELECT AVG(Likes) AS Likes FROM Answers Where QID="+question.getID());
	            	int likes = 0;
	            	String numLikesStr = null;
	            	if(likesSet.next())
	            		numLikesStr = likesSet.getString("Likes");
            		if  (numLikesStr!=null)
            			likes = Integer.parseInt(numLikesStr);
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
				db.closeConnection();
				}
	    	catch (IOException | SQLException e)
	    	{
				e.printStackTrace();
			} 
	    }
}
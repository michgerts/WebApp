package WebAppPkg;
 
import java.io.BufferedReader;
import java.io.IOException;
// SQL
import WebAppPkg.WebAppDB;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Data types
import WebAppPkg.Question;
 
public class NewestQuestionsServlet extends HttpServlet
{//this will submit an answer -- need to change the name
	private static final long serialVersionUID = 1L;
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> 7f0458c5508fba08238d6819c48e37a67b2b7af6
=======
>>>>>>> 7f0458c5508fba08238d6819c48e37a67b2b7af6
    private String tableName = "QUESTIONS";
    private int page=0;
    @SuppressWarnings("deprecation")
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
            StringBuffer requestURL = request.getRequestURL();
            String str;
        	page = (int) request.getSession().getValue("numOfPage");
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }

    		String nextOrPrev = new Gson().fromJson(sb.toString(),String.class);
    		
    		if(nextOrPrev != null && !nextOrPrev.isEmpty())
    		{
    			if(nextOrPrev.equals("0") && page>0)
    				request.getSession().putValue("numOfPage", --page);
        		else
        			if(nextOrPrev.equals("1"))
        				request.getSession().putValue("numOfPage", ++page);
    		}
    		
    		ResultSet numOfRowsSet = db.executeQuery("select count(*) as A from QUESTIONS");
    		String numOfRows = "";
    		if(numOfRowsSet.next())
    			numOfRows = numOfRowsSet.getString("A");
    		int numOfRowsInt = Integer.parseInt(numOfRows);//this is for the descending order later
            
			questions = db.executeQuery("SELECT * FROM "+ tableName+ " where answered=false order by time asc "+" offset " + page*20 +" rows"+" FETCH FIRST 20 ROWS ONLY ");
            
            
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

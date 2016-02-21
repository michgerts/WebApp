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
	private String dbURL =  "jdbc:derby://localhost:1527/C:/Program Files/Derby/db-derby-10.12.1.1-bin/bin/MyDbTest";
    private String tableName = "QUESTIONS";
    private int page=0;
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {    	
    	try
    	{
    		WebAppDB db = new WebAppDB();
    		ResultSet questions;
    		db.createConnection(dbURL); 
    		List<Question> questionsToPresent = new ArrayList<Question>();
    	
            StringBuilder sb = new StringBuilder();
            BufferedReader br = request.getReader();
        	page = (int) request.getSession().getValue("numOfPage");
            String str = null;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }

    		String nextOrPrev = new Gson().fromJson(sb.toString(),String.class);
    		
    		if(nextOrPrev != null && !nextOrPrev.isEmpty())
    		{
    			if(nextOrPrev.equals(0) && page>0)
    				request.getSession().putValue("numOfPage", --page);
        		else
        			if(nextOrPrev.equals(1))
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
            response.getWriter().write(categoriesJson);
	    	response.setContentType("application/json");
	    	response.setCharacterEncoding("UTF-8");
			response.getWriter().close();
			}
    	catch (IOException | SQLException e)
    	{
			e.printStackTrace();
		} 
    }
}

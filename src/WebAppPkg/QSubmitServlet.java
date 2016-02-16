package WebAppPkg;
 
import java.io.BufferedReader;
import java.io.IOException;
// SQL
import WebAppPkg.WebAppDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Servlet
import javax.servlet.*;
import javax.servlet.http.*;
// JSON
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Data types
import WebAppPkg.Question;
 
public class QSubmitServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String dbURL = "jdbc:derby://localhost:1527/C:/Program Files/Derby/db-derby-10.12.1.1-bin/bin/MyDbTest";
    private String tableName = "QUESTIONS";
    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	WebAppDB db = new WebAppDB();
		ResultSet questions;
		db.createConnection(dbURL); 
	
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str);
        }
		Question questionData = new Gson().fromJson(sb.toString(), Question.class);
        questions = db.executeQuery("select max(ID) AS ID from " + tableName);
        try
        {
        	if (!questions.next())
    		{
            	// this is the 1st question
        		questionData.setID(1);
    		}
        	else
        	{
        		int newID=questions.getInt(1) + 1;
        		questionData.setID(newID);
        	}
    		db.executeUpdate("INSERT INTO " + tableName +
					 "(ID, TEXT, TIME, ASKER, LIKES, ANSWERED) " +
					 "VALUES ("+questionData.getID()+",'"+questionData.getText()+
					 "','"+questionData.getTime()+"','"+questionData.getAsker()+
					 "',"+questionData.getLikes()+",'"+questionData.getAnswered()+"')");
        	JsonParser parser = new JsonParser();
        	JsonObject o = parser.parse("{\"msg\":"+questionData.getID() +"}").getAsJsonObject();
        	String json = new Gson().toJson(o);
        	response.setContentType("application/json");
        	response.setCharacterEncoding("UTF-8");
        	response.getWriter().write(json);
        	response.getWriter().close();
		}
        catch (SQLException e)
        {
			e.printStackTrace();
		}
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
    	
    	try {
    		WebAppDB db = new WebAppDB();
    		ResultSet questions;
    		db.createConnection(dbURL); 
    		List<Question> questionsToPresent = new ArrayList<Question>();
    	
            StringBuilder sb = new StringBuilder();
            BufferedReader br = request.getReader();
            String str = null;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            questions = db.executeQuery("SELECT * FROM "+ tableName+ " where answered=false order by time desc FETCH FIRST 20 ROWS ONLY ");
    		
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
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
    }
}

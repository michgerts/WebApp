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
 
/** This class is a servlet class that submits a new user question
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class QSubmitServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "QUESTIONS";
    @Override
    /** This method inserts the new questions into the DB
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	WebAppDB db = new WebAppDB();
		ResultSet questions;
		db.createConnection(); 
	
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
        	questions.next();
        	String temp= questions.getString("ID");
        	if (temp == null)
    		{
            	// this is the 1st question
        		
        		questionData.setID(1);
    		}
        	else
        	{
        		int newID=Integer.parseInt(temp) + 1;
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
        	//db.closeConnection();
		}
        catch (SQLException e)
        {
			e.printStackTrace();
		}
    }
}

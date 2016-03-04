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
import WebAppPkg.Answer;
 
/** This class is a servlet class that submits a user's answer to a certain question 
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class AnswerSubmitServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "ANSWERS";
    @Override
    /** This method inserts the new answer submitted to the DB
	 * and updates the questions' table field 'answered' to true
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	WebAppDB db = new WebAppDB();
		db.createConnection(); 
	
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str);
        }
		Answer answerData = new Gson().fromJson(sb.toString(), Answer.class);
		ResultSet answers;
		answers = db.executeQuery("select max(AID) AS AID from " + tableName);
		try
        {
        	answers.next();
        	String temp= answers.getString("AID");
        	if (temp == null)
    		{
            	// this is the 1st answer
        		answerData.setAID(1);
    		}
        	else
        	{
        		
        		int newAID=Integer.parseInt(temp) + 1;
        		answerData.setAID(newAID);
        	}
        	db.executeUpdate("INSERT INTO " + tableName +
				 "(AID, QID, UID, TEXT, TIME, LIKES) " +
				 "VALUES ("+answerData.getAID() +"," +answerData.getQID()+",'"+answerData.getUID()+
				 "','"+answerData.getText()+"','"+answerData.getTime()+
				 "',"+answerData.getLikes()+")");
		//db.commit();
		db.executeUpdate("UPDATE QUESTIONS SET " +
				 "ANSWERED='TRUE' " +
				 "WHERE ID="+answerData.getQID());
		//db.commit();
    	JsonParser parser = new JsonParser();
    	JsonObject o = parser.parse("{\"msg\":"+answerData.getQID() +"}").getAsJsonObject();
    	String json = new Gson().toJson(o);
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().write(json);
    	response.getWriter().close();
    	//db.closeConnection();
	}
		catch(Exception e)
		{
			
		}
}
}


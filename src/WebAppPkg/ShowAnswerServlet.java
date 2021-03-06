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

// Data types
import WebAppPkg.Question;
 
/** This class is servlet class that returns the details of a certain answer to a certain question
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class ShowAnswerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "ANSWERS";
    @Override
    /** This method returns answer's details.
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	WebAppDB db = new WebAppDB();
		ResultSet answer= null;
	
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str);
        }

		try
        {
        	db.createConnection();
        	db.setAutoCommit();
		int qid = new Gson().fromJson(sb.toString(), int.class);
		List<Answer> answersToPresent = new ArrayList<Answer>();
        answer = db.executeQuery("select * from " + tableName + " WHERE QID=" + qid + " order by Likes desc, Time asc");
        
        	while (answer.next())
    		{
            	// there is such an answer
        		Answer A = new Answer(-1,-1,"","");
        		A.setQID(qid);
        		A.setText(answer.getString("Text"));
        		A.setTime(answer.getString("Time"));
        		A.setUID(answer.getString("UID"));
        		A.setAID(answer.getInt("AID"));
        		A.setLikes(answer.getInt("Likes"));    
        		answersToPresent.add(A);
    		}
        	String json = new Gson().toJson(answersToPresent);
        	response.setContentType("application/json");
        	response.setCharacterEncoding("UTF-8");
        	response.getWriter().write(json);
        	response.getWriter().close();
        	answer.close(); 
		}
        catch (SQLException e)
        {
			e.printStackTrace();
		}
	finally{
			if(db!=null)
				db.closeConnection();
		}
    }
}

package WebAppPkg;
 
import java.io.BufferedReader;
import java.io.IOException;
// SQL
import WebAppPkg.WebAppDB;
import java.sql.ResultSet;
import java.sql.SQLException;
// Servlet
import javax.servlet.*;
import javax.servlet.http.*;
// JSON
import com.google.gson.Gson;

// Data types
import WebAppPkg.Question;
 
/** This class is a servlet class that handles a certain question's details.
 * The details are then presented to the user.
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class ShowQuestionServlet extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
    private String tableName = "QUESTIONS";
    @Override
    /** This method returns a certain question's details
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	WebAppDB db = new WebAppDB();
		ResultSet question;
		db.createConnection(); 
	
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str);
        }
		int qid = new Gson().fromJson(sb.toString(), int.class);
        question = db.executeQuery("select * from " + tableName + " WHERE ID=" + qid);
        try
        {
        	Question Q = new Question(-1,"","","",0,false);
        	if (question.next())
    		{
            	// there is such a question
        		Q.setAnswered(question.getBoolean("Answered"));
        		Q.setID(qid);
        		Q.setText(question.getString("Text"));
        		Q.setTime(question.getString("Time"));
        		Q.setAsker(question.getString("Asker"));
        		Q.setLikes(question.getInt("Likes"));      		       		
    		}
        	String json = new Gson().toJson(Q);
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

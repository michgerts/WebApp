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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Data types
import WebAppPkg.User;

/** This class is a servlet class that handles the the user ID, that is stored and extracted from a session
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class UserIDServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "USERS";
    @SuppressWarnings("deprecation")

	@Override
	/** This method gets the user id (name) from a session
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
    public void doGet (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	HttpSession sessions = request.getSession();
    	Object userID = sessions.getAttribute("userID");
    	
    		String json = null;
        	json = new Gson().toJson(userID);
        	response.setContentType("application/json");
        	response.setCharacterEncoding("UTF-8");
        	response.getWriter().write(json);
        	response.getWriter().close();
    }
    
    @Override
    /** This method sets the user id (name) to a session
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
  		   throws IOException, ServletException
  		   {
	    		StringBuilder sb = new StringBuilder();
		    	BufferedReader br = request.getReader();
		        String str = null;
		        while ((str = br.readLine()) != null)
		        {
		            sb.append(str);
		        }
				String userID = new Gson().fromJson(sb.toString(), String.class);
		    	HttpSession sessions = request.getSession();
		    	sessions.setAttribute("userID", userID);
  		   }
}

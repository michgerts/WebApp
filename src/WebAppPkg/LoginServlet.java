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
/** This is a servlet class that handles the system login
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "USERS";
    @SuppressWarnings("deprecation")

	@Override
	/** This method returns the details of the user that logged into the system
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	HttpSession sessions = request.getSession();
    	
    	WebAppDB db = new WebAppDB();
		ResultSet users;
		db.createConnection();
		try {
			db.setAutoCommit();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str);
        }
		User userData = new Gson().fromJson(sb.toString(), User.class);
		sessions.setAttribute("userID", userData.getName());
		
        users = db.executeQuery("select * from " + tableName + " WHERE NAME='"+userData.getName()+"'");
        try
        {
            String msg = "[]";
            String json = null;
            boolean loggedIn = false;
            if (!users.next())
    		{
            	// no such user exists
        		msg = "[\"No such user exists\"]";
    		}
            else if (!userData.getPassword().equals(users.getString(3))) 
    		{
            	// password wrong
            	msg = "[\"Wrong password\"]";
    		}
            else
            {
            	userData = new User(0,users.getString(2),"","", "", "");
            	loggedIn = true;
            }
            if (loggedIn)
            {
            	json = new Gson().toJson(userData);
            }
            else
            {
            	JsonParser parser = new JsonParser();
            	JsonObject o = parser.parse("{\"msg\":"+ msg +"}").getAsJsonObject();
            	json = new Gson().toJson(o);
            }
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
        finally{
			db.closeConnection();
		}
    }
}

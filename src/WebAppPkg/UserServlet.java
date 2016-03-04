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
import WebAppPkg.User;
 
/** This class is a servlet class that returns details that belong to a certain user
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class UserServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "USERS";
    @Override
    /** This method returns to the user the details of a certain user
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	try
        {
    	WebAppDB db = new WebAppDB();
		ResultSet users;
		db.createConnection(); 
	
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str);
        }
        HttpSession sessions = request.getSession();
    	Object userID = sessions.getAttribute("userID");
    	
		//User userData = new Gson().fromJson(sb.toString(), User.class);
    	User userData = new User();
    	userData.setName((String)userID);
        users = db.executeQuery("select * from " + tableName + " WHERE NAME='"+userData.getName()+"'");
        
            String json = null;
            users.next();
            userData = new User(users.getInt(1),users.getString(2),"",
            						users.getString(4), users.getString(5), users.getString(6));

            json = new Gson().toJson(userData);
        	response.setContentType("application/json");
        	response.setCharacterEncoding("UTF-8");
        	response.getWriter().write(json);
        	response.getWriter().close();
        	if(db!=null)
        		db.closeConnection();
		}
        catch (SQLException e)
        {
			e.printStackTrace();
		}
    }

}

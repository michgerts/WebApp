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
 
/** This class is a servlet class that handles user's sign up
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class SignUpServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "USERS";
    @Override
    /** This method handles a user sign up. It inserts the new user's detail into the DB.
	 * @param  request  request from user
	 * @param  response response back send to the user
	 */
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	WebAppDB db = new WebAppDB();
		ResultSet users;
		db.createConnection();
		try {
			db.setAutoCommit();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}; 
	
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str);
        }
		User userData = new Gson().fromJson(sb.toString(), User.class);
        users = db.executeQuery("select * from " + tableName + " WHERE NAME='"+userData.getName()+"'");
        try
        {
            String msg = "[]";
        	if (users.next())
    		{
            	// such a user already exists
        		msg = "[\"This name is taken please choose another\"]";
    		}
        	else
        	{
        		db.executeUpdate("INSERT INTO " + tableName +
        						 "(NAME, PASSWORD, NICKNAME, DESCRIPTION, PIC) " +
        						 "VALUES ('"+userData.getName()+"','"+userData.getPassword()+
        						 "','"+userData.getNickName()+"','"+userData.getDescription()+
        						 "','"+userData.getPic()+"')");
        	}
        	JsonParser parser = new JsonParser();
        	JsonObject o = parser.parse("{\"msg\":"+ msg +"}").getAsJsonObject();
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
        finally{
			db.closeConnection();
		}
    }
}

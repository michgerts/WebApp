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
import WebAppPkg.Topic;
 
/** This class is a servlet class that handles the insertion of a new topic to the db
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class TopicsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "TOPICS";
    @Override
    /** This method inserts a new topic into the DB
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
		Topic topicData = new Gson().fromJson(sb.toString(), Topic.class);
		String stripppedtopicData = topicData.getTopic().replaceAll("(^ )|( $)", "");
		db.executeUpdate("INSERT INTO " + tableName + "(QID, TOPIC) VALUES ("+topicData.getQID() +",'"+stripppedtopicData+"')");
		db.closeConnection();
    }
}

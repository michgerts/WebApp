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
 
public class TopicsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String dbURL = "jdbc:derby://localhost:1527/c:/Users/koganmic/Documents/DB/MyDB";
    private String tableName = "TOPICS";
    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	WebAppDB db = new WebAppDB();
		db.createConnection(dbURL); 
		StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str);
        }
		Topic topicData = new Gson().fromJson(sb.toString(), Topic.class);
		db.executeUpdate("INSERT INTO " + tableName + "(QID, TOPIC) VALUES ("+topicData.getQID() +",'"+topicData.getTopic()+"')");    
    }
}

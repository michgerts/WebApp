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
 
public class ShowAnswerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "ANSWERS";
    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response)
 		   throws IOException, ServletException
    {
    	WebAppDB db = new WebAppDB();
		ResultSet answer;
		db.createConnection(); 
	
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str);
        }
		int qid = new Gson().fromJson(sb.toString(), int.class);
        answer = db.executeQuery("select * from " + tableName + " WHERE QID=" + qid);
        try
        {
        	Answer Q = new Answer(-1,"","");
        	if (answer.next())
    		{
            	// there is such an answer
        		Q.setQID(qid);
        		Q.setText(answer.getString("Text"));
        		Q.setTime(answer.getString("Time"));
        		Q.setUID(answer.getString("UID"));
        		Q.setLikes(answer.getInt("Likes"));      		       		
    		}
        	String json = new Gson().toJson(Q);
        	response.setContentType("application/json");
        	response.setCharacterEncoding("UTF-8");
        	response.getWriter().write(json);
        	response.getWriter().close();
        	db.closeConnection();
		}
        catch (SQLException e)
        {
			e.printStackTrace();
		}
    }
}

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
 
public class ShowQuestionServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "QUESTIONS";
    @Override
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
		}
        catch (SQLException e)
        {
			e.printStackTrace();
		}
    }
}

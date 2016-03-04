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

/** This is a servlet class that handles the voting (up and down) on a certain answer
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class VoteAnswerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private String tableName = "ANSWERS";
    @Override
    /** This method handles the voting on answers
   	 * @param  request  request from user
   	 * @param  response response back send to the user
   	 */
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
        String data = new Gson().fromJson(sb.toString(),String.class);
        String upVotestr = data.substring(1, 2);
        String idstr = data.substring(3, data.length()-1);
        Integer id = Integer.parseInt(idstr);
        Integer upVote = Integer.parseInt(upVotestr);
        ResultSet numOfLikesSet = db.executeQuery("select Likes as A from ANSWERS where AID = "+ id.intValue());
        ResultSet QIDSet = db.executeQuery("select QID as A from ANSWERS where AID = "+ id.intValue());
		String numOfLikesStr = "";
		String QIDStr = "";
		try
		{
			if(numOfLikesSet.next())
				numOfLikesStr = numOfLikesSet.getString("A");
			if(QIDSet.next())
				QIDStr = QIDSet.getString("A");
			int numOfLikesInt = Integer.parseInt(numOfLikesStr);//numOfLikesInt contains the number of likes at the current moment
			if(upVote == 1)
				numOfLikesInt++;
			else if(upVote == 0)
				numOfLikesInt--;
			if(upVote != null && id != null)
			{
				db.executeUpdate("update ANSWERS set likes ="+ numOfLikesInt +"where AID = " + id.intValue());
			}
			List<Answer> answersToPresent = new ArrayList<Answer>();
	        answer = db.executeQuery("select * from " + tableName + " WHERE QID=" + QIDStr + " order by Likes desc, Time asc");
	       
        	while (answer.next())
    		{
            	// there is such an answer
        		Answer A = new Answer(-1,-1,"","");
        		A.setQID(Integer.parseInt(QIDStr));
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
        	db.closeConnection();
		}
        catch (SQLException e)
        {
			e.printStackTrace();
		}
    }
}

package WebAppPkg;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

/** This class handles the connection to the DB 
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class WebAppDB
{
    private Connection conn;
    
	public WebAppDB ()
	{
		conn = null;
	}
	
	/** This method creates a connection to the DB
   	 */
	public void createConnection()
    {
		
    	try
    	{
    		//obtain CustomerDB data source from Tomcat's context
    		Context context = new InitialContext();
    		BasicDataSource ds =(BasicDataSource)context.lookup("java:comp/env/jdbc/webAppDataSource");
    		if(conn == null || conn.isClosed())
    			conn = ds.getConnection();
    		//use connection as you wish…but close after usage! (this
    		//is important for correct connection pool management
    		//within Tomcat
    	}
    	catch (Exception except)
    	{
    		except.printStackTrace();
    	}
    }
	
	/** This method executes a given query
	 * @param SQLQuuery 	the query to be executed
   	 */
    public ResultSet executeQuery(String SQLQuuery)
    {
    	Statement stmt = null;
    	ResultSet results = null;
    	try
    	{
    		stmt = conn.createStatement();
    		results = stmt.executeQuery(SQLQuuery);
    	}
    	catch (SQLException sqlExcept)
    	{
    		sqlExcept.printStackTrace();
    	}
		return results;
    }
    
    /** This method executes a given query - that updates the DB
	 * @param SQLQuuery 	the query to be executed
   	 */
    public void executeUpdate(String SQLQuuery)
    {
    	Statement stmt = null;
    	try
    	{
    		stmt = conn.createStatement();
    		stmt.executeUpdate(SQLQuuery);
    		this.commit();
    	}
    	catch (SQLException sqlExcept)
    	{
    		sqlExcept.printStackTrace();
    	}    	
    }
    /** This method closes the connection to the DB
   	 */
    public void closeConnection()
    {
    	try {
    		if(conn != null && !conn.isClosed())
    			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    /** This method commits to the DB
   	 */
    public void commit()
    {
    	try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public void setAutoCommit() throws SQLException
    {
    	conn.setAutoCommit(false);
    };
    
	
}
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


public class WebAppDB
{
    private static Connection conn;
    
	public WebAppDB ()
	{
		conn = null;
	}
	
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
    public void closeConnection()
    {
    	try {
    		if(conn != null && !conn.isClosed())
    			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void commit()
    {
    	try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	
}
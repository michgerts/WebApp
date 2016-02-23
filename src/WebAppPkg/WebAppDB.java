package WebAppPkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    		String dbURL= "jdbc:derby://localhost:1527/C:/Program Files/Derby/db-derby-10.12.1.1-bin/bin/MyDbTest";
    		Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
    		//Get a connection
    		conn = DriverManager.getConnection(dbURL); 
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
    	}
    	catch (SQLException sqlExcept)
    	{
    		sqlExcept.printStackTrace();
    	}
    }
    
	
}

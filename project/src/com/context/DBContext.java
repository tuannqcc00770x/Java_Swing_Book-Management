package com.context;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {

	 public Connection getConnection()throws Exception {
	        String url = "jdbc:sqlserver://"+serverName+":"+portNumber +";databaseName="+dbName;
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        return DriverManager.getConnection(url, userID, password);
	    }
	 
	 //change information to connect depend on your settings
	 private final String serverName = "localhost";
	    private final String dbName = "BookDB";
	    private final String portNumber = "1433";
	    private final String userID = "sa";
	    private final String password = "1234";
}

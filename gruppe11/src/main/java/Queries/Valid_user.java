package Queries;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db_connection.QueryImporter;

public class Valid_user extends QueryImporter{
	static Connection con;
	static Statement stmt;
	public static void getConnected() throws InstantiationException, IllegalAccessException {
		 System.out.println("Howwowo");
		try {
			//instance of the driver
		  Class.forName("com.mysql.jdbc.Driver").newInstance();
		  
		  //Setting database path
		  String url = "jdbc:mysql://mysqladmin.stud.ntnu.no/sandeb_dbProsjekt";
		  String user = "simonnb";
		  String pw = "acerSwift";
		  
		  // Connecting with the given information
		  Connection init = DriverManager.getConnection(url,user,pw);
		  
		  //Checking The connection
		  System.out.println(init);
		  System.out.println("Tilkoblingen fungerte.");
		  Statement message = init.createStatement() ;
		  
		  //Setting the variables
		  stmt = message;
		  con = init;
		  
		  //Catches exceptions
		  } catch (SQLException ex) {
			  System.out.println("H22222");
		    System.out.println("Tilkobling feilet: "+ex.getMessage());
		  } catch (ClassNotFoundException other) {
			  System.out.println("H3333");
		    System.out.println("Feilet under driverlasting: "+other.getMessage());
		  } finally {
		    try {
		      if (con !=  null) con.close();
		    } catch (SQLException ex) {
		      System.out.println("Epic fail: "+ex.getMessage());
		    }
		  }

	}
	
	 
	public static Boolean validUser (String userID) {
		
		//Makes the query
		String query_String = "SELECT COUNT(*) FROM bruker WHERE id = "+ userID + "";
		
		//The variable to get the results from the query
		ResultSet rs = null;
		
		try {
			//Sends the query and saves the result
			rs = stmt.executeQuery(query_String);
			
			System.out.println(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Checks if the result of the query is a number bigger than 0, then there exists such a user.
		if(Integer.parseInt(rs.toString())>0) {
			return true;
		}
		else {
			return false;
		}
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		getConnected();
		System.out.println(validUser("1"));
	}
}

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
		  Class.forName("com.mysql.jdbc.Driver").newInstance();
		  String url = "jdbc:mysql://mysqladmin.stud.ntnu.no/sandeb_dbProsjekt";
		  String user = "simonnb";
		  String pw = "acerSwift";
		  Connection init = DriverManager.getConnection(url,user,pw);
		  System.out.println(init);
		  System.out.println("Tilkoblingen fungerte.");
		  Statement message = init.createStatement() ;
		  stmt = message;
		  con = init;
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
		String query_String = "SELECT COUNT(*) FROM bruker WHERE id = "+ userID + "";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query_String);
			System.out.println(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

package db_connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class getInfo extends ConnectService {
	private Statement stmt = null;
	
	private Boolean getValidUser (String user) throws SQLException {
		
		Connection c = getConnection();
		stmt = c.createStatement();
		ResultSet rs = null;
		String query_String = "SELECT COUNT(*) FROM bruker WHERE id = "+ user + "";
		
		try {
			//Sends the query and saves the result
			rs = stmt.executeQuery(query_String);
			
			System.out.println(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Check if user exists
		if(Integer.parseInt(rs.toString())>0) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public String getUser(String user) throws SQLException {
		if(getValidUser(user)== true) {
			return user;
		}
		else {
			return "User is not in the database";
		}
	}

	
	
}

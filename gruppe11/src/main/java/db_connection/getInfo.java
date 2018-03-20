package db_connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ui.Alerter;

public class getInfo{

	public static ResultSet getWorkouts (Integer limit){
	
		try {
			Statement stmt = null;
			ResultSet rs = null;
			ConnectService cs = new ConnectService(); 
			Connection c = cs.getConnection();
			stmt =c.createStatement();
			
			
			///////
			String queryString = "SELECT treningsøkt.dato_tidspunkt, treningsøkt.varighet, treningsøkt.form, treningsøkt.prestasjon, treningsøkt.notat FROM treningsøkt	ORDER BY treningsøkt.dato_tidspunkt LIMIT "+limit;
			rs = stmt.executeQuery(queryString);
			return rs;
		} catch(SQLException e) {
			e.printStackTrace();
		}
	
	return null;
	
	
	}
	
}

package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db_connection.ConnectService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class ResultController {
	
	private Statement stm;
	private ConnectService cs = new ConnectService();
	
	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	private Button submit;

	public String getDate() throws SQLException {
		System.out.println(fromDate.getValue());
		return fromDate.getValue().toString();
	}
	public void getData() throws SQLException {
		String query = "SELECT * FROM øvelse;";
		Connection c = cs.getConnection();
		stm = c.createStatement();
		ResultSet rs = stm.executeQuery(query);
		while (rs.next()) {
			String navn = rs.getString("navn");
			System.out.println(navn);
		}
		
	}
	
	

}

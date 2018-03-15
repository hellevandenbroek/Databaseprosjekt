package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import db_connection.ConnectService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class ResultController {

	private Statement stm;
	private PreparedStatement pstm;
	private ConnectService cs = new ConnectService();

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	private Button submit;
	

	public String getDate(DatePicker date) throws SQLException {
		return date.getValue().toString();
	}

	public void getData() throws SQLException {
		String query = "SELECT * FROM `øvelse` NATURAL JOIN `treningsøkt` WHERE dato_tidspunkt > (?) < (?)";
		Connection c = cs.getConnection();
		PreparedStatement pstm = c.prepareStatement(query);
		String fDate = getDate(fromDate);
		String tDate = getDate(toDate);
		pstm.setString(1, fDate);
		pstm.setString(2, tDate);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			System.out.println("Id: " + rs.getInt("id"));
			System.out.println("Navn: " + rs.getString("navn"));
			System.out.println("Øvelse type: " + rs.getString("øvelse_type"));
			System.out.println("Dato: " + rs.getDate("dato_tidspunkt"));
			System.out.println("Varighet: " + rs.getTime("varighet"));
			System.out.println("Form: " + rs.getInt("form"));
			System.out.println("Prestasjon: " + rs.getInt("prestasjon"));
			System.out.println("Notat: " + rs.getString("notat"));
		}
	}

}

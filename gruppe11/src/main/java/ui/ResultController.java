package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import db_connection.ConnectService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ResultController{

	private Statement stm;
	private PreparedStatement pstm;
	private ConnectService cs = new ConnectService();

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	private TextArea data;

	@FXML
	private Button submit;
	
	@FXML
	private ChoiceBox<String> ex;
	
	public void initialize() throws SQLException {
		ObservableList<String> exercises = FXCollections.observableArrayList(); 
		String query = "SELECT navn FROM `øvelse`";
		Connection c = cs.getConnection();
		stm = c.createStatement();
		ResultSet rs = stm.executeQuery(query);
		while (rs.next()) {
			exercises.add(rs.getString("navn"));
		}
		ex.setItems(exercises);
	}

	public String getDate(DatePicker date) throws SQLException {
		return date.getValue().toString();
	}

	public void getData() throws SQLException {
		String query = "SELECT * FROM `øvelse` NATURAL JOIN `treningsøkt` WHERE dato_tidspunkt > (?) < (?) and navn = (?)";
		Connection c = cs.getConnection();
		PreparedStatement pstm = c.prepareStatement(query);
		String fDate = getDate(fromDate);
		String tDate = getDate(toDate);
		String exercise = ex.getSelectionModel().getSelectedItem();
		pstm.setString(1, fDate);
		pstm.setString(2, tDate);
		pstm.setString(3, exercise);
		ResultSet rs = pstm.executeQuery();
		String str = "";
		while (rs.next()) {

			str += "Id: " + rs.getInt("id") + "\n";
			str += "Navn: " + rs.getString("navn") + "\n";
			str += "Øvelse type: " + rs.getString("øvelse_type") + "\n";
			str += "Dato: " + rs.getDate("dato_tidspunkt") + "\n";
			str += "Varighet: " + rs.getTime("varighet") + "\n";
			str += "Form: " + rs.getInt("form") + "\n";
			str += "Prestasjon: " + rs.getInt("prestasjon") + "\n";
			str += "Notat: " + rs.getString("notat") + "\n";
			str += "-----------------------------------\n";
		}
		data.setText(str);
	}

	public void toBack() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

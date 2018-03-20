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

public class ResultController {

	private Statement stm;
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
	private Button back;

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
		ex.setValue(ex.getItems().get(0));
	}

	public String getDate(DatePicker date) {
		return date.getValue().toString();
	}

	public void getData() {
		String query = "SELECT * FROM `øvelse` NATURAL JOIN `treningsøkt` WHERE dato_tidspunkt > (?) < (?) and navn = (?)";
		String query2 = "SELECT COUNT(*) AS `Antall` from treningsøkt WHERE dato_tidspunkt > (?) < (?);";
		Connection c;
		try {
			c = cs.getConnection();
			PreparedStatement pstm = c.prepareStatement(query);
			PreparedStatement pstm2 = c.prepareStatement(query2);
			String fDate = getDate(fromDate);
			String tDate = getDate(toDate);
			String exercise = ex.getSelectionModel().getSelectedItem();
			pstm.setString(1, fDate);
			pstm.setString(2, tDate);
			pstm.setString(3, exercise);
			pstm2.setString(1, fDate);
			pstm2.setString(2, tDate);
			ResultSet rs = pstm.executeQuery();
			ResultSet rs2 = pstm2.executeQuery();
			String str = "";
			while (rs2.next()) {
				str += "Antall økter i perioden: " + rs2.getInt("Antall") + "\n";
				str += "===================================\n";
			}
			while (rs.next()) {
				str += "Id: " + rs.getInt("øvelse_id") + "\n";
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

		} catch (SQLException e) {
			Alerter.error("Ugyldig valg", "Vennligst sjekk dato og valg av øvelse");
			e.printStackTrace();
		} catch (NullPointerException n) {
			Alerter.error("Ugyldig valg", "Vennligst sjekk dato og valg av øvelse");
			n.printStackTrace();
		}

	}

	public void toBack() {
		try {
			Stage stag = (Stage) back.getScene().getWindow();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
			stag.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

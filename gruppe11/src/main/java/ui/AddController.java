package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db_connection.ConnectService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddController {
	@FXML
	private TextField inputApp;

	@FXML
	private Button btnAddApp;

	@FXML
	private TextField inputEx;

	@FXML
	private TextArea desc;

	@FXML
	private Button btnAddEx;

	@FXML
	private ChoiceBox<String> type;

	private ConnectService cs = new ConnectService();
	
	private enum Types{friøvelse, apparatøvelse};

	public void initialize() {
		type.setItems(FXCollections.observableArrayList(String.valueOf(Types.apparatøvelse), String.valueOf(Types.friøvelse)));
	}

	public void addApp() throws SQLException {
		String appName = inputApp.getText();
		String description = desc.getText();
		if (description == null) {
			description = null;
		}
		String query = "INSERT INTO apparat(navn, bruksmåte) VALUES (?,?)";
		try (Connection conn = cs.getConnection();
				PreparedStatement pstm = cs.getConnection().prepareStatement(query)){
			pstm.setString(1, appName);
			pstm.setString(2, description);
			pstm.executeUpdate();
			Alerter.info("Success!", appName + " is now added to database");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

	public void addEx() throws SQLException {
		String exName = inputEx.getText();
		String typeName = type.getValue();
		String query = "INSERT INTO øvelse(navn, øvelse_type) VALUES (?,?)";
		try (Connection conn = cs.getConnection(); 
				PreparedStatement pstm = conn.prepareStatement(query)) {
			pstm.setString(1, exName);
			pstm.setString(2, typeName);
			pstm.executeUpdate();
			inputEx.clear();
			Alerter.info("Success!", exName + " is now added to database");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// pstm.executeUpdate();
	}

}

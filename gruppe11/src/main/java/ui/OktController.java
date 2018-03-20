package ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import db_connection.getInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OktController {

	@FXML
	Button ReadButton;
	@FXML 
	Button backButton;
	@FXML
	TextField input;
	@FXML
	TextArea rsText;
	
	public void runRead() throws SQLException{
		ResultSet rs = getInfo.getWorkouts(Integer.parseInt(input.getText()));
		String str = "";
		rsText.clear();
		input.clear();
		while (rs.next()) {
			String notat = rs.getString("notat");
			if (notat == null) {
				notat = "Ingen notat";
			}
			str += "Dato: " + rs.getString("dato_tidspunkt") + "\n";
			str += "Varighet: " + rs.getString("varighet") + "\n";
			str += "Din form: " + rs.getString("form") + "\n";
			str += "Din prestasjon: " + rs.getString("prestasjon") + "\n";
			str += "Notat: " + notat + "\n";
			str += "================================\n";
		}
		rsText.setText(str);
	}
	
	public void toBack() {
		try {
			Stage stag = (Stage) backButton.getScene().getWindow();
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
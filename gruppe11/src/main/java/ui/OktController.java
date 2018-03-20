package ui;

import java.io.IOException;
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
	Button BackButton;
	@FXML
	TextField Input;
	@FXML
	TextArea rsText;
	
	public void runBack() throws IOException{

		try {
			Stage stag = (Stage) BackButton.getScene().getWindow();
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1));  
	        stage.show();          
	        stag.close();
	    }
	    catch(Exception e) {
	       e.printStackTrace();
	    }
	}
	
	public void runRead() throws SQLException{
		getInfo testing = new getInfo();
		ResultSet rs = getInfo.getUsers(Integer.parseInt(Input.getText()));
		String str = null;
		rsText.clear();
		Input.clear();
		while (rs.next()) {
			str += "Dato: " + rs.getString("dato_tidspunkt") + "\n";
			str += "Varighet: " + rs.getString("varighet") + "\n";
			str += "Din form: " + rs.getString("form") + "\n";
			str += "Din prestasjon: " + rs.getString("prestasjon") + "\n";
			str += "Notat: " + rs.getString("notat") + "\n";
			str += "================================\n";
		}
		rsText.setText(str);
	}
}
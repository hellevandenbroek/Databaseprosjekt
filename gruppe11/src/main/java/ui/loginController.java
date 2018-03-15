package ui;

import java.sql.SQLException;

import db_connection.getInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {
	
		@FXML Button LoginButton;
		@FXML TextField loginInput;
		
		public void runLogin() throws SQLException {
			
			//Initialize the validation class
			final getInfo info = new getInfo();
			
			//Get input Info
			String input = loginInput.getText();
			
			//Check if the input is a real user
			if (info.checkUser(input)!= null) {
				try {
					
					//Switch to the menu if login is succsesful
					Stage stag = (Stage) LoginButton.getScene().getWindow();
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
			} else
				//Shameful else
			{
			}
			
			
		}
}

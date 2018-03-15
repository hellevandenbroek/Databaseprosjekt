package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ExerciseGroupController {

	
	@FXML Button back;
	@FXML Button see;
	@FXML Button add;
	
	public void toBack() {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1));  
	        stage.show();          
	    }
	        
	    catch(Exception e) {
	       e.printStackTrace();
	    }
	}
	public void toSee() {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("See.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1));  
	        stage.show();          
	    }
	        
	    catch(Exception e) {
	       e.printStackTrace();
	    }
	}
	public void toAdd() {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(".fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1));  
	        stage.show();          
	    }
	        
	    catch(Exception e) {
	       e.printStackTrace();
	    }
	}
}

package ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

	
	@FXML Button exercise;
	@FXML Button logs;
	@FXML Button addGroup;
	@FXML Button addExApp;
	@FXML Button number;
	@FXML private Button exitBtn;
	
	
	public void toExercise() {
		try {
			Stage stage = (Stage) exercise.getScene().getWindow();
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ExerciseGroups.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        stage.setScene(new Scene(root1));
		}
	        
	    catch(Exception e) {
	       e.printStackTrace();
	    }
	}
	
	public void toResult() {
		try {
			Stage stage = (Stage) logs.getScene().getWindow();
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Result.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        stage.setScene(new Scene(root1));
	    }
	        
	    catch(Exception e) {
	       e.printStackTrace();
	    }
	}
	public void toAddExApp() {
		try {
			Stage stage = (Stage) addGroup.getScene().getWindow();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root1));
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void toAddGroup() {
		try {
			Stage stage = (Stage) addGroup.getScene().getWindow();
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addWorkout.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        stage.setScene(new Scene(root1));
	    }
	        
	    catch(Exception e) {
	       e.printStackTrace();
	    }
	}
	public void exit() {
		Stage stage = (Stage) addGroup.getScene().getWindow();
		stage.close();
		Platform.exit();
	}
	
	public void toOverview() {
		try {
			Stage stage = (Stage) addGroup.getScene().getWindow();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Okter.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root1));
	    }
	        
	    catch(Exception e) {
	       e.printStackTrace();
	    }
	}
}

	

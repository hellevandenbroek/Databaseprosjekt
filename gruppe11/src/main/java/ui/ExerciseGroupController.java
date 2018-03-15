package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

import db_connection.ConnectService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ExerciseGroupController {

	
	@FXML Button back;
	@FXML Button add;
	@FXML Button one;
	@FXML Button two;
	@FXML TextField name;
	@FXML ListView groups;
	@FXML ListView exercises;
	@FXML ListView allExercises;
	
	
	private ConnectService cs = new ConnectService();
	private Statement stm;

	

	public void start() throws SQLException {
		String query = "SELECT navn FROM øvelsesgruppe";
		Connection c = cs.getConnection();
		stm = c.createStatement();
		ResultSet rs = stm.executeQuery(query);
		while (rs.next()) {
			((ResultSet) groups).getString("name");
		}
	}
	
	
	public static void main(String[] args) {

	}
	
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
	
	//Legger til en ny øvelsesgruppe i databasen
	public void toAdd() throws SQLException {
		String groupName = name.getText();
		String query = "INSERT INTO øvelsesgruppe (navn) VALUES (?)";
		Connection c = cs.getConnection();
		PreparedStatement pstm = c.prepareStatement(query);
		pstm.setString(1, groupName);
		pstm.executeUpdate();
	}
	
	public void toOne() {
		
		
	}
	
	public void toTwo() {
		
	}
	
	
}

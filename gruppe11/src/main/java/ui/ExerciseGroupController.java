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
	@FXML ListView<String> groups;
	@FXML ListView<Integer> exercises;
	@FXML ListView<String> allExercises;
	
	private ConnectService cs = new ConnectService();
	private Statement stm;

	public void initialize() throws SQLException {
		String query = "SELECT navn FROM øvelsesgruppe";
		Connection c = cs.getConnection();
		stm = c.createStatement();
		ResultSet rs = stm.executeQuery(query);
		while (rs.next()) {
			groups.getItems().add(rs.getString("navn"));
		}
		 groups.setOnMouseClicked(new ListViewHandler(){
		        @Override
		        public void handle(javafx.scene.input.MouseEvent event) {
		            rowSelected(groups.getSelectionModel().getSelectedIndex());
		        }
		 });
		String query2 = "SELECT navn FROM øvelse";
		Connection c2 = cs.getConnection();
		stm = c2.createStatement();
		ResultSet rs2 = stm.executeQuery(query2);
		while (rs2.next()) {
			allExercises.getItems().add(rs2.getString("navn"));
			}
	}
	
	public void rowSelected(int k) {
		for (int i = 0; i < exercises.getItems().size(); i++) {
			exercises.getItems().remove(i);
		}
		exercises.getItems().add(k);
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
		initialize();
	}
	
	public void toOne() {
		
		
	}
	
	public void toTwo() {
		
	}
	
	
}

package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

import db_connection.ConnectService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ExerciseGroupController {

	
	@FXML Button back;
	@FXML Button add;
	@FXML Button one;
	@FXML Button two;
	@FXML TextField name;
	@FXML ListView<String> groups;
	@FXML ListView<String> exercises;
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
		    try {
				rowSelected((groups.getSelectionModel().getSelectedIndex()) + 1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}}
		);
		 
		String query2 = "SELECT navn FROM øvelse";
		Connection c2 = cs.getConnection();
		stm = c2.createStatement();
		ResultSet rs2 = stm.executeQuery(query2);
		while (rs2.next()) {
			allExercises.getItems().add(rs2.getString("navn"));
			}
	}
	
	
	//This does not work
	public void rowSelected(int k) throws SQLException {
		for (int i = 0; i < exercises.getItems().size(); i++) {
			exercises.getItems().remove(i);
		}
		String query2 = "SELECT DISTINCT øvelse.navn FROM øvelse JOIN medlem_av_gruppe WHERE øvelse.id = øvelse_id";
		Connection c2 = cs.getConnection();
		stm = c2.createStatement();
		PreparedStatement pstm = c2.prepareStatement(query2);
		pstm.setInt(1, k);
		System.out.println(pstm);
		
		ResultSet rs2 = pstm.executeQuery(query2);
		while (rs2.next()) {
			exercises.getItems().add(rs2.getString("navn"));
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

package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
	@FXML Button show;
	@FXML TextField name;
	@FXML ListView<String> groups;
	@FXML ListView<String> exercises = null;
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
		String query2 = "SELECT navn FROM øvelse";
		Connection c2 = cs.getConnection();
		stm = c2.createStatement();
		ResultSet rs2 = stm.executeQuery(query2);
		while (rs2.next()) {
			allExercises.getItems().add(rs2.getString("navn"));
			}
		
	}
	
	
	//Håndterer tilbakeknappen
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
		if(groupName.length() > 0 && isfree(groupName)){
			groups.getItems().clear(); //sletter tidligere innhold
			String query = "INSERT INTO øvelsesgruppe (navn) VALUES (?)";
			Connection c = cs.getConnection();
			PreparedStatement pstm = c.prepareStatement(query);
			pstm.setString(1, groupName);
			pstm.executeUpdate();
			//oppdaterer
			String query1 = "SELECT navn FROM øvelsesgruppe";
			Connection c1 = cs.getConnection();
			stm = c1.createStatement();
			ResultSet rs = stm.executeQuery(query1);
			while (rs.next()) {
				groups.getItems().add(rs.getString("navn"));
			}
			name.clear();
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	//sletter markert
	public void toOne() throws SQLException {
		Connection c = cs.getConnection();
		String selectedGroup = groupSelected();
		String selectedEx = exSelectedOne();
		String getID = "SELECT DISTINCT øvelsesgruppe.øvelsesgruppe_id, øvelse.øvelse_ID FROM øvelsesgruppe NATURAL JOIN medlem_av_gruppe JOIN øvelse WHERE øvelsesgruppe.navn = ? AND øvelse.navn = ?";
		PreparedStatement pstm1 = c.prepareStatement(getID);
		pstm1.setString(1, selectedGroup);
		pstm1.setString(2, selectedEx);
		ResultSet rs = pstm1.executeQuery();
		Integer groupId = null, exId = null;
		while (rs.next()) {
			groupId = rs.getInt(1);
			exId = rs.getInt(2);
		}
		String query = "DELETE FROM medlem_av_gruppe WHERE øvelsesgruppe_id = ? AND øvelse_id = ?";
		PreparedStatement pstm = c.prepareStatement(query);
		pstm.setInt(1, groupId);
		pstm.setInt(2, exId);
		pstm.executeUpdate();
		toShow();
	}
	
	
	//legger til markert
	public void toTwo() throws SQLException {
		Connection c = cs.getConnection();
		String selectedGroup = groupSelected();
		String selectedEx = exSelectedTwo();
		String getID = "SELECT DISTINCT øvelsesgruppe.øvelsesgruppe_id, øvelse.øvelse_ID FROM øvelsesgruppe JOIN øvelse WHERE øvelsesgruppe.navn = ? AND øvelse.navn = ?";
		PreparedStatement pstm1 = c.prepareStatement(getID);
		pstm1.setString(1, selectedGroup);
		pstm1.setString(2, selectedEx);
		ResultSet rs = pstm1.executeQuery();
		Integer groupId = null, exId = null;
		while (rs.next()) {
			groupId = rs.getInt(1);
			exId = rs.getInt(2);
		}
					
		if (!isInGroup(selectedGroup, selectedEx)) {
			String query = "INSERT INTO medlem_av_gruppe (øvelse_id, øvelsesgruppe_id) VALUES (?, ?)";
			PreparedStatement pstm = c.prepareStatement(query);
			pstm.setInt(1, exId);
			pstm.setInt(2, groupId);
			pstm.executeUpdate();
		}
		toShow();
	}
	
	private boolean isInGroup(String selectedGroup, String selectedEx) {
		return false;
	}


	public void toShow() throws SQLException {
		exercises.getItems().clear();
		String query = "SELECT øvelse.navn FROM øvelse NATURAL JOIN medlem_av_gruppe JOIN øvelsesgruppe WHERE øvelsesgruppe.navn = (?) AND øvelsesgruppe.øvelsesgruppe_id = medlem_av_gruppe.øvelsesgruppe_id";
		Connection c = cs.getConnection();
		PreparedStatement pstm = c.prepareStatement(query);
		pstm.setString(1, groupSelected());	 	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			exercises.getItems().add(rs.getString("navn"));
		}
	}	
	
	public String groupSelected() {
		return groups.getSelectionModel().getSelectedItem().toString();	
	}
	
	public String exSelectedOne() {
		return exercises.getSelectionModel().getSelectedItem().toString();
	}

	public String exSelectedTwo() {
		return allExercises.getSelectionModel().getSelectedItem().toString();
	}
	
	//checks if name is already taken
	public boolean isfree(String name) throws SQLException {
		String query = "SELECT øvelsesgruppe.navn FROM øvelsesgruppe";
		List<String> all = new ArrayList<String>();

		Connection c = cs.getConnection();
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			all.add(rs.getString("navn"));
		}
		if(all.contains(name)) {
			return false;
		}
		return true;
	}
}

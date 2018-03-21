package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import db_connection.ConnectService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ExerciseGroupController {

	@FXML
	Button back;
	@FXML
	Button add;
	@FXML
	Button one;
	@FXML
	Button two;
	@FXML
	TextField name;
	@FXML
	ListView<String> groups;
	@FXML
	ListView<String> exercises = null;
	@FXML
	ListView<String> allExercises;

	private ConnectService cs = new ConnectService();
	private Statement stm;

	public void initialize() throws SQLException {
		// henter alle øvelsesgrupper
		try {
			String query = "SELECT navn FROM øvelsesgruppe";
			stm = cs.getConnection().createStatement();
			ResultSet rs = stm.executeQuery(query);
			while (rs.next()) {
				groups.getItems().add(rs.getString("navn"));
			}
			// henter alle øvelser
			String query2 = "SELECT navn FROM øvelse";
			stm = cs.getConnection().createStatement();
			ResultSet rs2 = stm.executeQuery(query2);
			while (rs2.next()) {
				allExercises.getItems().add(rs2.getString("navn"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Alerter.error("Feil ved innlasting av database", "Sjekk at du er koblet til VPN");
		}

	}

	// Legger til en ny øvelsesgruppe i databasen
	public void toAdd() throws SQLException {
		try {
			String groupName = name.getText();
			if (groupName.length() > 0 && isfree(groupName)) {
				groups.getItems().clear(); // sletter tidligere innhold
				String query = "INSERT INTO øvelsesgruppe (navn) VALUES (?)";
				PreparedStatement pstm = cs.getConnection().prepareStatement(query);
				pstm.setString(1, groupName);
				pstm.executeUpdate();

				// oppdaterer øvelsesgrupper
				String query1 = "SELECT navn FROM øvelsesgruppe";
				stm = cs.getConnection().createStatement();
				ResultSet rs1 = stm.executeQuery(query1);
				while (rs1.next()) {
					groups.getItems().add(rs1.getString("navn"));
				}
				name.clear();
			} else {
				name.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Alerter.error("Feil ved innsetting", "Sjekk navnet i feltet.");
		}

	}

	public void removeGroup() {
		try {
			String groupName = groups.getSelectionModel().getSelectedItem();
			if (groupName != null) {
				String query = "DELETE FROM øvelsesgruppe WHERE navn = ?";
				Connection c = cs.getConnection();
				PreparedStatement pstm = c.prepareStatement(query);
				pstm.setString(1, groupName);
				groups.getItems().clear();
				pstm.executeUpdate();
				String query1 = "SELECT navn FROM øvelsesgruppe";
				stm = cs.getConnection().createStatement();
				ResultSet rs1 = stm.executeQuery(query1);
				while (rs1.next()) {
					groups.getItems().add(rs1.getString("navn"));
				}
				name.clear();
			} else {
				throw new Exception();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			Alerter.error("Feil ved sletting av gruppe", "Sjekk at du har markert gruppe.");
		}
	}

	// sletter markert
	public void removeEx() throws SQLException {
		try {
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
			update();
		} catch (Exception e) {
			e.printStackTrace();
			Alerter.error("Feil ved sletting", "Sjekk at du har markert rett.");
		}
	}

	// legger til markert
	public void addEx() throws SQLException {
		try {

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
			update();
		} catch (Exception e) {
			e.printStackTrace();
			Alerter.error("Feil ved innsetting", "Sjekk at markeringene dine er rett.");
		}
	}

	private boolean isInGroup(String selectedGroup, String selectedEx) {
		return false;
	}

	public void toShow() throws SQLException {
		update();
	}

	// oppdaterer exercises
	public void update() throws SQLException {
		exercises.getItems().clear();
		String query = "SELECT øvelse.navn FROM øvelse NATURAL JOIN medlem_av_gruppe JOIN øvelsesgruppe WHERE øvelsesgruppe.navn = (?) AND øvelsesgruppe.øvelsesgruppe_id = medlem_av_gruppe.øvelsesgruppe_id";
		PreparedStatement pstm = cs.getConnection().prepareStatement(query);
		pstm.setString(1, groupSelected());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			exercises.getItems().add(rs.getString("navn"));
		}
		if (exercises.getItems().isEmpty()) {
			exercises.setPlaceholder(new Label("Inneholder ingen øvelser."));
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

	// checks if name is already taken
	public boolean isfree(String name) throws SQLException {
		String query = "SELECT øvelsesgruppe.navn FROM øvelsesgruppe";
		List<String> all = new ArrayList<String>();

		Connection c = cs.getConnection();
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			all.add(rs.getString("navn"));
		}
		if (all.contains(name)) {
			Alerter.error("Gruppe finnes allerede", "Vennligst velg et annet navn!");
			return false;
		}
		return true;
	}

	// Håndterer tilbakeknappen
	public void back() {
		try {
			Stage stage = (Stage) back.getScene().getWindow();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

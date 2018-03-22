package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db_connection.ConnectService;
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
	
	private ConnectService cs = new ConnectService();
	
	public void runRead() throws SQLException{
		String query = "SELECT treningsøkt.dato_tidspunkt, treningsøkt.varighet, treningsøkt.form, treningsøkt.prestasjon, treningsøkt.notat FROM treningsøkt ORDER BY treningsøkt.dato_tidspunkt DESC LIMIT ?";
		try (Connection conn = cs.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)){
			pstm.setInt(1, Integer.parseInt(input.getText()));
			System.out.println(pstm);
			ResultSet rs = pstm.executeQuery();
			String str = "";
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
		} catch (Exception e) {
			e.printStackTrace();
			Alerter.error("Feil i antall", "Sjekk at du skrev et tall.");
		}
	}
	
	public void toBack() {
		try {
			Stage stage = (Stage) backButton.getScene().getWindow();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
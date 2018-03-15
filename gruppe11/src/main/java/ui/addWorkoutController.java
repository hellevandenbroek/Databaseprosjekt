package ui;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.InputStream;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import com.mysql.jdbc.ExceptionInterceptor;
import com.mysql.jdbc.PingTarget;
import com.mysql.jdbc.ResultSetInternalMethods;

import db_connection.Apparat;
import db_connection.ApparatExercise;
import db_connection.ConnectService;
import db_connection.Exercise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class addWorkoutController {

	//dato, tid, duration
	@FXML DatePicker date;
	@FXML TextField hour, minute, duration; // may not use duration
	
	//form/prestasjon
	@FXML ChoiceBox<Integer> form;
	@FXML ChoiceBox<Integer> prestasjon;
	// slider
	@FXML Label timerLabel, minuttLabel;
	@FXML Slider timerSlider, minuttSlider;
	
	@FXML Button addExercise, addSelected, addSelectedAndClose;
	@FXML ListView<Exercise> listViewExercises, addedExercises;
	
	// notat
	@FXML TextField notat;
	
	//apparat
	@FXML Label apparatLabelName, kiloLabel, settLabel;
	@FXML ChoiceBox<Integer> kilo, sett;
	@FXML Pane apparatPane;
	
	private ConnectService cs = new ConnectService(); 
	private Statement stmt = null;
	
	public void initialize() throws SQLException {
		
		Connection c = cs.getConnection();
		
		stmt = c.createStatement();
		
		ResultSet rs = null;
		
		String query = "SELECT * FROM øvelse";
		List<Exercise> exercises = new ArrayList<>();
		try {
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				Exercise e = new Exercise(rs.getString("navn"), rs.getInt("id")); 
				exercises.add(e);
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		

		
		// TODO GET INFORMATION FROM DB
		
		
		
//		Exercise e1 = new Exercise("pushups", 0);
//		Exercise e2 = new Exercise("situps", 1);
//		Exercise e3 = new Exercise("planke", 2);
//		Exercise e4 = new Exercise("run", 3);
//		Exercise e5 = new Exercise("sofa", 4);
//		ApparatExercise e6 = new ApparatExercise("bull", 55, new Apparat("shit", 55));
		listViewExercises.getItems().addAll(exercises);	
		listViewExercises.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
			if (newValue instanceof ApparatExercise) {
				showApparatView();
			} else {
				hideApparatView();
			}
			
		});
		
		// adding values to choiceboxes
		ObservableList<Integer> oneTen = 
				FXCollections.observableArrayList(
						1, 2, 3, 4, 5, 6, 7, 8, 9, 10); 
		ObservableList<Integer> five150 =  FXCollections.observableArrayList(5);
		for (int i = 10; i < 150; i+= 5) {
			five150.add(i);
			
		}
		
		kilo.setItems(five150);
		form.setItems(oneTen);
		prestasjon.setItems(oneTen);
		sett.setItems(oneTen);

	}
	
	private void hideApparatView() {
		// TODO Auto-generated method stub
		apparatPane.setDisable(true);
		System.out.println("Did something");
	}

	public void showApparatView() {
		apparatPane.setDisable(false);
	}
	
	public void addExercise() {
		Exercise e = listViewExercises.getSelectionModel().getSelectedItem();
		addedExercises.getItems().add(e);
		if (e instanceof ApparatExercise) {
			System.out.println("This is apparatly an apparat.");
		} else {
			System.out.println("This is not.");
		}
	}
	
	
	
	
}

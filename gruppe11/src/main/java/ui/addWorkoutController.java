package ui;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import db_connection.Apparat;
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
	
	@FXML Button addExercise, addSelected;
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

		// with apparater
		List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT * FROM apparat JOIN øvelse";
		try {
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				Exercise e;
				String name = rs.getString("navn");
				System.out.println(name);
				int id = rs.getInt("øvelse.id");
				if (rs.getString("øvelse_type").equals("apparatøvelse")) {
					Apparat ap = new Apparat(rs.getString("navn"), rs.getInt("apparat.id"));					
					e = new Exercise(name, id, ap);
				} else {
					e = new Exercise(name, id);
				}
				if (e != null) {
					for (Exercise ex : exercises) {
						if (ex.getId() != e.getId()) {
							System.out.println("added " + e.getName());
							exercises.add(e);
						}
					}
				}
			}
//			query = "SELECT * FROM øvelse";
//			rs = stmt.executeQuery(query);
//			
//			
//			while (rs.next()) {
//				String name = rs.getString("navn");
//				int id = rs.getInt("id");
//				Exercise e = new Exercise(name, id);
//				if (!exercises.contains(e)) {
//					exercises.add(e);
//				}
//			}
			exercises.add(new Exercise("Satans", 666));

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
			System.out.println(newValue);
			
			if (newValue.getApparat() != null) {
				showApparatView();
				System.out.println(newValue.getApparat());
			} else {
				hideApparatView();
				System.out.println("Exercise does not have an apparat.");
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
		if (e.getApparat() != null) {
			System.out.println("This is apparatly an apparat.");
		} else {
			System.out.println("This is not.");
		}
	}
	
	public void addSelected() {
		// TODO send to database
		clearFields();
	}
	
	public void addSelectedAndClose() {
		// TODO send to database
		clearFields();
		// TODO close window
	}
	
	public void clearFields() {
		notat.clear();
		date.setValue(null);
		addedExercises.getItems().clear();
		kilo.setValue(null);
		sett.setValue(null);
		form.setValue(null);
		prestasjon.setValue(null);
		hour.clear();
		minute.clear();
	}
	
	
	
}

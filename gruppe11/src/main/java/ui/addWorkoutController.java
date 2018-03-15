package ui;



import java.util.Arrays;
import java.util.List;

import db_connection.Apparat;
import db_connection.ApparatExercise;
import db_connection.Exercise;
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
	
	public void initialize() {
		
		// TODO GET INFORMATION FROM DB
		Exercise e1 = new Exercise("pushups", 0);
		Exercise e2 = new Exercise("situps", 1);
		Exercise e3 = new Exercise("planke", 2);
		Exercise e4 = new Exercise("run", 3);
		Exercise e5 = new Exercise("sofa", 4);
		ApparatExercise e6 = new ApparatExercise("bull", 55, new Apparat("shit", 55));
		
		List<Exercise> exercises = Arrays.asList(e1, e2, e3, e4, e5);
		listViewExercises.getItems().addAll(exercises);	
		listViewExercises.getItems().add(e6);
		listViewExercises.selectionModelProperty().addListener(e -> {
			System.out.println("Hello");
			if (listViewExercises.getSelectionModel().getSelectedItem() 
					instanceof ApparatExercise) {
				System.out.println("Below");
				showApparatView();
				
			} else {
				hideApparatView();
				
			}
		});
		
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

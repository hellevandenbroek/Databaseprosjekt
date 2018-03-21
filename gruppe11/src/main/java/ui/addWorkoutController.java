package ui;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import db_connection.Apparat;
import db_connection.ConnectService;
import db_connection.Exercise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

public class addWorkoutController {

	@FXML Button back;


	//dato, tid, duration
	@FXML DatePicker date;
	@FXML TextField hour, minute;
	@FXML TextField durationTimer, durationMinutes;

	//form/prestasjon
	@FXML ChoiceBox<Integer> form;
	@FXML ChoiceBox<Integer> prestasjon;

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

	private Collection<Exercise> addedList = new ArrayList<>();

	public void initialize() throws SQLException {

		Connection c = cs.getConnection();

		stmt = c.createStatement();

		ResultSet rs = null;

		// with apparater
		List<Exercise> exercises = new ArrayList<>();

		// without
		String query = "SELECT øvelse_id, navn FROM øvelse WHERE øvelse_type = \"friøvelse\" ";
		rs = stmt.executeQuery(query);


		while (rs.next()) {
			String name = rs.getString("navn");
			int id = rs.getInt("øvelse_id");
			Exercise e = new Exercise(name, id);
			exercises.add(e);
		}

		String queryApparat = "SELECT * FROM "
				+ "øvelse NATURAL JOIN apparatøvelse JOIN apparat "
				+ "WHERE apparat.apparat_id = apparatøvelse.apparat_id";
		rs = stmt.executeQuery(queryApparat);

		while (rs.next()) {
			String name = rs.getString("øvelse.navn");
			int id = rs.getInt("øvelse_id");
			Exercise ea = new Exercise(name, id, new Apparat(rs.getString("apparat.navn"), rs.getInt("apparat_id")));
			exercises.add(ea);
		}

		listViewExercises.getItems().addAll(exercises);
		listViewExercises.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
			if (newValue.getApparat() != null) {
				showApparatView();
			} else {
				hideApparatView();
				kilo.setValue(null);
				sett.setValue(null);
			}

		});

		kilo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				kilo.setValue(newValue);
			}
			System.out.println(kilo.getValue());
		});

		sett.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				sett.setValue(newValue);
			}
			System.out.println(sett.getValue());
		});

		// adding values to choiceboxes
		ObservableList<Integer> oneTen =
				FXCollections.observableArrayList(
						1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		ObservableList<Integer> five150 =  FXCollections.observableArrayList(5);
		for (int i = 10; i < 155; i+= 5) {
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
	}

	public void showApparatView() {
		apparatPane.setDisable(false);
	}

	public void addExercise() {
		Exercise e = listViewExercises.getSelectionModel().getSelectedItem();
		if (!addedExercises.getItems().contains(e)) {
			if (e.hasApparat() && kilo.getValue() != null && sett.getValue() != null) {
				e.getApparat().setKilo(kilo.getValue());
				e.getApparat().setSett(sett.getValue());
			}
			addedExercises.getItems().add(e);
			addedList.add(e);
		}
	}

	public void addSelected() throws SQLException {
		// TODO send to database
		LocalDate dateV = date.getValue();

		String notatV = notat.getText();


		Connection c = cs.getConnection();
		String query =
				"INSERT INTO "
						+ "treningsøkt(dato_tidspunkt, varighet, form, prestasjon, notat) "
						+ "VALUES "
						+ "(?, ?, ?, ?, ?);";


		PreparedStatement pstmt = c.prepareStatement(query);
		// Insert into tabellnavn(kilo, sett) values (?,?)

		//1
		if (dateV != null) {
			int hourV = 0;
			int minuteV = 0;
			if (!hour.getText().equals("") && !minute.getText().equals("")) {
				hourV = Integer.parseInt(hour.getText());
				minuteV = Integer.parseInt(minute.getText());
			}
			int year = dateV.getYear();
			int month = dateV.getMonth().getValue();
			int day = dateV.getDayOfMonth();



			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, day);

			cal.set(Calendar.MINUTE, minuteV);
			cal.set(Calendar.HOUR, hourV);

			pstmt.setTimestamp(1, new Timestamp(cal.getTimeInMillis()));
		} else {
			pstmt.setNull(1, Types.TIMESTAMP);
		}
		// 2
		if (!durationTimer.getText().equals("") || !durationMinutes.getText().equals("")) {
			String fullDuration = durationTimer.getText() + ":" + durationMinutes.getText() + ":00";
			pstmt.setString(2, fullDuration);
		} else {
			pstmt.setNull(2, Types.VARCHAR);
		}
		// 3
		if (form.getValue() != null) {
			pstmt.setInt(3, form.getValue());
		} else {
			pstmt.setNull(3, Types.INTEGER);
		}
		// 4
		if (prestasjon.getValue() != null) {
			pstmt.setInt(4, prestasjon.getValue());
		} else {
			pstmt.setNull(4, Types.INTEGER);
		}
		// 5
		if (!notat.getText().equals("")) {
			pstmt.setString(5, notat.getText());
		} else {
			pstmt.setNull(5, Types.VARCHAR);
		}

		System.out.println(query);
		pstmt.executeUpdate();

		clearFields();
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
		durationMinutes.clear();
		durationTimer.clear();
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
}

package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
	}

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Treningsapp");
		stage.show();
	}
}

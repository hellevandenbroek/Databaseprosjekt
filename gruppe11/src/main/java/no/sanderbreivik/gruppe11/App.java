package no.sanderbreivik.gruppe11;

<<<<<<< Updated upstream
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
=======
import db_connection.ConnectService;
>>>>>>> Stashed changes

/**
 * Hello world!
 *
 */
public class App 
{
<<<<<<< Updated upstream
    public static void main( String[] args ){
    
    }

    public void start (Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Treningsapp");
		stage.show();
=======
    public static void main( String[] args )
    {
    		ConnectService c = new ConnectService();
        if (condition) {
			
		}
>>>>>>> Stashed changes
    }
}


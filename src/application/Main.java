package application;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import application.model.Restaurant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Main extends Application {
	
	public static Restaurant res;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			/*MediaPlayer media=new MediaPlayer(new Media(getClass().getResource("/Images/audioo.mp3").toString()));
			media.setCycleCount(MediaPlayer.INDEFINITE);
			media.play();
			primaryStage.setOnCloseRequest(windowEvent->
			{
				media.stop();
			});*/
			Parent root = FXMLLoader.load(getClass().getResource("/view/FirstPage.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		
	}
	
	
	
	
	
}

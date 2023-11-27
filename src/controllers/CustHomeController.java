package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class CustHomeController implements Initializable{
	
	
	@FXML
	MediaView mv;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Media media = new Media(getClass().getResource("/Images/javaV.mp4").toString());		
		   MediaPlayer player=new MediaPlayer(media);
		   player.setCycleCount(MediaPlayer.INDEFINITE);
		   mv.setMediaPlayer(player);
		   player.play();
		
	}
	
	
	


}

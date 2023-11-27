package controllers;

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class FirstPageControlle {
	
	@FXML
	Button buttonn;
	@FXML
	StackPane stack;
	@FXML
	AnchorPane anchoor;
	
	
	
	//animation between scenes.
	@FXML
	public void start(ActionEvent e)
	{
		try {
			Parent roott =FXMLLoader.load(getClass().getResource("/view/MyMainDesign.fxml"));
			Scene scene=buttonn.getScene();
			roott.translateYProperty().set(scene.getHeight());
			
			stack.getChildren().add(roott);
			Timeline time=new Timeline();
			KeyValue kv= new KeyValue(roott.translateYProperty(),0,Interpolator.EASE_IN);
			KeyFrame kf=new KeyFrame(Duration.seconds(1),kv);
			time.getKeyFrames().add(kf);
			time.setOnFinished(event1->{
				
				stack.getChildren().remove(anchoor);
			});
			time.play();
			
			
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}


	

}

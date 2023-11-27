package controllers;

import static application.util.Utills.LoadFXML;


import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


import application.model.Customer;
import application.model.HelpingClass;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class CustomerPageController implements Initializable{
	
	
	@FXML
	BorderPane myborder;
	@FXML
	 Label labell;
	@FXML
	AnchorPane anchoor;
	@FXML
	Button logg;
	@FXML
	ImageView custImage;
	@FXML
	MediaView mv;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		   Media media = new Media(getClass().getResource("/Images/javaV.mp4").toString());	
		   MediaPlayer player=new MediaPlayer(media);
		   player.setCycleCount(MediaPlayer.INDEFINITE);
		   mv.setMediaPlayer(player);
		   player.play();
		  HelpingClass holder = HelpingClass.getInstance();
		  Customer u = holder.getUser();
		  String name = u.getFirstName();
		  labell.setText("                      Welcome "+name);
		  setPic(u.getPhoto());
		  
		 
			
		  
	}
	private void setPic(byte[] castPhoto) {
		if(castPhoto!=null) {
	        
        	Image image=new Image(new ByteArrayInputStream(castPhoto));
            custImage.setImage(image);
	 }
	 else
	 {
		 custImage.setImage(new Image("File:Images/noPhoto.png"));
	 }
	}

	    
	
	
	@FXML
    public void goToLogIn(ActionEvent e) throws IOException{
		
		try {
			Parent roott =FXMLLoader.load(getClass().getResource("/view/MyMainDesign.fxml"));
			Scene scene=logg.getScene();
			roott.translateYProperty().set(scene.getHeight());
		    StackPane container=(StackPane)scene.getRoot();
		    container.getChildren().add(roott);
			Timeline time=new Timeline();
			KeyValue kv= new KeyValue(roott.translateYProperty(),0,Interpolator.EASE_IN);
			KeyFrame kf=new KeyFrame(Duration.seconds(1),kv);
			time.getKeyFrames().add(kf);
			time.setOnFinished(event1->{
				
				container.getChildren().remove(anchoor);
			});
			time.play();
			
			
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}
	@FXML
	public void goHome(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/CustomerHomePage.fxml"); 
		myborder.setRight(root);
	}

	//these methods are for transition between scenes.
	@FXML
	private void recievedata(ActionEvent event) throws IOException{
	  
	  Pane root = LoadFXML(getClass(), "/view/ReleventDishCustomer.fxml");
	  
	  myborder.setRight(root);
	  
	}
	
	@FXML
	public void goCookByExp(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/CookByExpertise.fxml"); 
		myborder.setRight(root);
	}
	@FXML
	public void goUpdate(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/PeronalInfo.fxml");
		myborder.setRight(root);
	}
	@FXML
	public void goAddDish(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/CustomerAddDishes.fxml"); 
		myborder.setRight(root);
	}
	@FXML
	public void goShoppingCart(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/ShoppingCard.fxml"); 
		myborder.setRight(root);
	}
	@FXML
	public void goRemoveDishes(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/CustomerRemoveDishes.fxml"); 
		myborder.setRight(root);
	}
	@FXML
	public void goAddOrder(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/CustomerAddOrder.fxml"); 
		myborder.setRight(root);
	}
	@FXML
	public void goAllOrder(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/CustomerAllOrders.fxml"); 
		myborder.setRight(root);
	}
	@FXML
	public void goRemoveOrder(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/CustomerRemoveOrder.fxml"); 
		myborder.setRight(root);
	}
	
	@FXML
    public void exiiit(ActionEvent e) throws IOException{
		Platform.exit();
	}
	@FXML
	public void goUpdateeDishes(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/CustomerUpdateDishes.fxml"); 
		myborder.setRight(root);
	}
	@FXML
	public void goAllDishes(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/Menu.fxml");
		myborder.setRight(root);
	}
	@FXML
	 public void goPopular(ActionEvent e) throws IOException{
			Pane root = LoadFXML(getClass(), "/view/PopularCompo.fxml"); 
			myborder.setRight(root);
		}

}

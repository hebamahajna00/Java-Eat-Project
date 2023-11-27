package controllers;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import static application.util.Utills.LoadFXML;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;

public class ManagerController implements Initializable{
	
	//java --module-path "C:\Program Files\Java\javafx-sdk-16\lib" --add-modules javafx.controls,javafx.fxml,javafx.media -jar Desktop\JavaProject_Ex3_322775123\javaproo.jar
	@FXML
	BorderPane myborder;
	@FXML
	AnchorPane anchoor;
	@FXML
	Button logg;
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
	
	//this methods are for transfer between scenes.
	
	@FXML
	public void goDelivery(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AddDeliveryPerson.fxml"); 
		myborder.setRight(root);
	}
	@FXML
	public void goHome(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/HomeManager.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAddCompo(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AddComponent.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goPopular(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/PopularCompo.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void removeCompo(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/RemoveComponent.fxml");
		myborder.setRight(root);
	}
	@FXML
    public void goBlackList(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/BlackList.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAi(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AiMachine.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goNumber(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/NumberPerType.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goDeliver(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/Deliver.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void exiiit(ActionEvent e) throws IOException{
		Platform.exit();
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
    public void goProfit(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/ProfitRelation.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goRemoveDeli(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/RemoveDelivery.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goRemoveDish(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/RemoveDish.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goRemoveOrder(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/RemoveOrder.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goRemoveCustomer(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/RemoveCustomers.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goRemoveCook(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/RemoveCook.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goUpdateDish(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/UpdateDish.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goUpdateOrder(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/UpdateOrders.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goUpdateDelivery(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/UpdateDelivery.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goUpdateCustomer(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/UpdateCustomers.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goUpdateDeliPerson(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/UpdateDeliveryPerson.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goUpdateCook(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/UpdateCook.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goUpdateArea(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/UpdateDeliveryArea.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goRemoveDeliPerson(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/RemoveDeliPerson.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goUpdateCompo(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/UpdateComponent.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goRemoveArea(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/RemoveArea.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goOrderBy(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/OrdersByCustomer.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goDeliBy(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/DeliByPerson.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goToReleventDishList(ActionEvent e) throws IOException{

	    Pane root = LoadFXML(getClass(), "/view/ManagerReleventDish.fxml"); 
	    myborder.setRight(root);
		
		
    }
	@FXML
    public void goDeliPerson(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AddDeliveryPerson.fxml"); 
	
		myborder.setRight(root);
	}
	@FXML
    public void goAddblack(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AddToBlackList.fxml"); 
	
		myborder.setRight(root);
	}
	@FXML
    public void goRemoveblack(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/RemoveFromBlack.fxml"); 
	
		myborder.setRight(root);
	}
	@FXML
    public void goAllComponents(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AllComponents.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAllAreas(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AllAreas.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAllCooks(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AllCooks.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAllDishes(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AllDishes.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAllOrders(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AllOrders.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAllDeli(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AllDeliveries.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAllDelPersons(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AllDelPersons.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAllCustomers(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/AllCustomers.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void reve(ActionEvent e) throws IOException{
		Double x=Main.res.revenueFromExpressDeliveries();
		Alert a=new Alert(AlertType.INFORMATION);
		a.setTitle("Renevue From Express Deliveries");
		a.setHeaderText("Revenue from express deliveries is: "+x);
		a.showAndWait();
	}
	@FXML
    public void goCookByExp(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/CookByExpertise.fxml");
		myborder.setRight(root);
	}
	@FXML
    public void goAddDelivery(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/DeliveryDesign.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAddDish(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/DishDesign.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAddArea(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/ManagerAddArea.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAddCust(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/ManagerAddCustomer.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAddOrder(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/ManagerAddOrder.fxml"); 
		myborder.setRight(root);
	}
	@FXML
    public void goAddCook(ActionEvent e) throws IOException{
		Pane root = LoadFXML(getClass(), "/view/ManCookDesign.fxml"); 
		myborder.setRight(root);
	}
	


	
	
	
	
	
	
	
	
	

}

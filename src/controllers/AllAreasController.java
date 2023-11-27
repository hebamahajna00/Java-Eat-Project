package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import Utils.Neighberhood;
import application.Main;
import application.model.Delivery;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class AllAreasController implements Initializable{
	
	@FXML 
	TableView<DeliveryArea> tablee;
	@FXML
	TableColumn<DeliveryArea,String> name;
	@FXML
	TableColumn<DeliveryArea,HashSet<DeliveryPerson>> persons ;
	@FXML
	TableColumn<DeliveryArea,HashSet<Delivery>>deliveries;
	@FXML
	TableColumn<DeliveryArea,HashSet<Neighberhood>> neighberhoods;
	@FXML
	TableColumn<DeliveryArea,Integer> timee;
	@FXML
	TableColumn<DeliveryArea,Integer> id;
	@FXML
	Label errorid;
	@FXML
	TextField idsearch;
	@FXML 
	Button search;
	
	
	//the method shows all the data in the tables.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("areaName"));
		persons.setCellValueFactory(new PropertyValueFactory<>("delPersons"));
		deliveries.setCellValueFactory(new PropertyValueFactory<>("delivers"));
		neighberhoods.setCellValueFactory(new PropertyValueFactory<>("neighberhoods"));
		timee.setCellValueFactory(new PropertyValueFactory<>("deliverTime"));
		ArrayList<DeliveryArea> a=new ArrayList<DeliveryArea>(Main.res.getAreas().values());
		ObservableList<DeliveryArea> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);
		
	}
	
	/*
	 * this method helps the user to know if the inputs are valid.
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(idsearch.getText().chars().allMatch(Character::isDigit)) {
			  errorid.setText("");
			  idsearch.setStyle("-fx-border-color:green;");
			}
			else {
				
			    errorid.setText("*Invalid Input!");
			    idsearch.setStyle("-fx-border-color:red;");
			    }
	}
	
	/*
	 * i gave the user the opportunity to search for a specific area using id ,
	 * here i used lambda.
	 */
	@FXML
	 public void search1(ActionEvent e)
	 {
		 if(idsearch.getText().equals("")||!idsearch.getText().chars().allMatch(Character::isDigit))
		 {
			 Alert a=new Alert(AlertType.CONFIRMATION);
			 a.setHeaderText("Invalid Input!!!");
			 a.showAndWait();
		 }
		 else
		 {
			 int id1=Integer.parseInt(idsearch.getText());
			 if(Main.res.getRealDeliveryArea(id1)==null)
			 {
				 Alert a=new Alert(AlertType.INFORMATION);
				 a.setHeaderText("doesnt Exist!");
				 a.showAndWait();
			 }
			 else {
			 tablee.getItems().stream().filter(item->item.getId()==id1).findAny().ifPresent(item->{
				 tablee.getSelectionModel().select(item);
				 tablee.scrollTo(item);
				 
			 });
			 }
		 }
	 }
	

}
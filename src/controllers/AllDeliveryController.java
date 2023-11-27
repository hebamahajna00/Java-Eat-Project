package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;
import application.Main;
import application.model.Delivery;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
import application.model.ExpressDelivery;
import application.model.Order;
import application.model.RegularDelivery;
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

public class AllDeliveryController implements Initializable{
	
	
	@FXML 
	TableView<RegularDelivery> tablee;
	@FXML
	TableColumn<RegularDelivery,DeliveryPerson> person;
	@FXML
	TableColumn<RegularDelivery,DeliveryArea> area ;
	@FXML
	TableColumn<RegularDelivery,Boolean>delivered;
	@FXML
	TableColumn<RegularDelivery,LocalDate> date;
	@FXML
	TableColumn<RegularDelivery,TreeSet<Order>> orders;
	@FXML
	TableColumn<RegularDelivery,Integer> id;
	@FXML
	Label errorid;
	@FXML
	TextField idsearch;
	@FXML 
	Button search;
	
	
	@FXML 
	TableView<ExpressDelivery> tablee1;
	@FXML
	TableColumn<ExpressDelivery,DeliveryPerson> person1;
	@FXML
	TableColumn<ExpressDelivery,DeliveryArea> area1 ;
	@FXML
	TableColumn<ExpressDelivery,Boolean>delivered1;
	@FXML
	TableColumn<ExpressDelivery,LocalDate> date1;
	@FXML
	TableColumn<ExpressDelivery,Order> orders1;
	@FXML
	TableColumn<ExpressDelivery,Integer> id1;
	@FXML
	TableColumn<ExpressDelivery,Integer> postage;

	
	
	//set the date in the relevent table.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		person.setCellValueFactory(new PropertyValueFactory<>("deliveryPerson"));
		area.setCellValueFactory(new PropertyValueFactory<>("area"));
		delivered.setCellValueFactory(new PropertyValueFactory<>("isDelivered"));
		orders.setCellValueFactory(new PropertyValueFactory<>("orders"));
		date.setCellValueFactory(new PropertyValueFactory<>("deliveredDate"));
		ArrayList<Delivery> a=new ArrayList<Delivery>(Main.res.getDeliveries().values());
		ArrayList<RegularDelivery> arr=new ArrayList<RegularDelivery>();
		for(Delivery d:a)
		{
			if(d instanceof RegularDelivery)
			{
				arr.add((RegularDelivery)d);
			}
		}
		ObservableList<RegularDelivery> list=FXCollections.observableArrayList(arr);
		tablee.setItems(list);
		
		id1.setCellValueFactory(new PropertyValueFactory<>("id"));
		person1.setCellValueFactory(new PropertyValueFactory<>("deliveryPerson"));
		area1.setCellValueFactory(new PropertyValueFactory<>("area"));
		delivered1.setCellValueFactory(new PropertyValueFactory<>("isDelivered"));
		orders1.setCellValueFactory(new PropertyValueFactory<>("order"));
		date1.setCellValueFactory(new PropertyValueFactory<>("deliveredDate"));
		postage.setCellValueFactory(new PropertyValueFactory<>("postage"));
		
		
		
		ArrayList<Delivery> a1=new ArrayList<Delivery>(Main.res.getDeliveries().values());
		ArrayList<ExpressDelivery> arr1=new ArrayList<ExpressDelivery>();
		for(Delivery d:a1)
		{
			if(d instanceof ExpressDelivery)
			{
				arr1.add((ExpressDelivery)d);
			}
		}
		ObservableList<ExpressDelivery> list1=FXCollections.observableArrayList(arr1);
		tablee1.setItems(list1);
		
	}
	/*
	 * this method help the user to know if the inputs are valid.
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(e.getSource()==idsearch) {
		if(idsearch.getText().chars().allMatch(Character::isDigit)) {
			  errorid.setText("");
			  idsearch.setStyle("-fx-border-color:green;");
			}
			else {
				
			    errorid.setText("*Invalid Input!");
			    idsearch.setStyle("-fx-border-color:red;");
			    }
		}
	}
	/*
	 * i gave the user the opportunity to search for a specific delivery using id ,
	 * here i used lambda.
	 */
	@FXML
	 public void search1(ActionEvent e)
	 {
		 if(e.getSource()==search) {
		 if(idsearch.getText().equals("")||!idsearch.getText().chars().allMatch(Character::isDigit))
		 {
			 Alert a=new Alert(AlertType.CONFIRMATION);
			 a.setHeaderText("Invalid Input!!!");
			 a.showAndWait();
		 }
		 else
		 {
			 int id1=Integer.parseInt(idsearch.getText());
			 if(Main.res.getRealDelivery(id1)==null)
			 {
				 Alert a=new Alert(AlertType.INFORMATION);
				 a.setHeaderText("doesnt Exist!");
				 a.showAndWait();
			 }
			 else {
			 Delivery d=Main.res.getRealDelivery(id1);
			 if(d instanceof RegularDelivery)
			 {
			 tablee.getItems().stream().filter(item->item.getId()==id1).findAny().ifPresent(item->{
				 tablee.getSelectionModel().select(item);
				 tablee.scrollTo(item);
				 
			 });
			 }
			 else
			 {
				 tablee1.getItems().stream().filter(item->item.getId()==id1).findAny().ifPresent(item->{
					 tablee1.getSelectionModel().select(item);
					 tablee1.scrollTo(item);
					 
				 });
			 }
			 }
			 
		 }
		 }
		
		
	 }
	

}



package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.model.Customer;
import application.model.Delivery;
import application.model.Dish;
import application.model.Order;
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

public class AllOrdersController implements Initializable{

	
	
	
	@FXML 
	TableView<Order> tablee;
	@FXML
	TableColumn<Order,Customer> customer;
	@FXML
	TableColumn<Order,ArrayList<Dish>> dishes;
	@FXML
	TableColumn<Order,Delivery> delivery;
	@FXML
	TableColumn<Order,Delivery> id;
	
	@FXML
	Label errorid;
	@FXML
	TextField idsearch;
	@FXML 
	Button search;
	

	//set the table's data.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		customer.setCellValueFactory(new PropertyValueFactory<>("customer"));
		dishes.setCellValueFactory(new PropertyValueFactory<>("dishes"));
		delivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
		ArrayList<Order> a=new ArrayList<Order>(Main.res.getOrders().values());
		ObservableList<Order> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);
		
	}
	/*
	 * this method help the user to know if the inputs are valid.
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
	 * i gave the user the opportunity to search for a specific order using id ,
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
			 if(Main.res.getRealOrder(id1)==null)
			 {
				 Alert a=new Alert(AlertType.INFORMATION);
				 a.setHeaderText("doesnt Exist!");
				 a.showAndWait();
			 }
			 else {
			 tablee.getItems().stream().filter(item->item.getId()==id1).findAny().ifPresent(item->{
				 tablee.getSelectionModel().select(item);
				 tablee.scrollTo(item);
				 
			 });}
		 }
	 }
	

}


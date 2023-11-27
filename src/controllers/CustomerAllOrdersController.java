package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.model.Customer;
import application.model.Delivery;
import application.model.Dish;
import application.model.HelpingClass;
import application.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerAllOrdersController implements Initializable{
	
	
	@FXML 
	TableView<Order> tablee;
	@FXML
	TableColumn<Order,ArrayList<Dish>> dishes;
	@FXML
	TableColumn<Order,Delivery> delivery;
	@FXML
	TableColumn<Order,Delivery> id;
	


	//set the table's data.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		dishes.setCellValueFactory(new PropertyValueFactory<>("dishes"));
		delivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
		Customer c=HelpingClass.getInstance().getUser();
		if(Main.res.getOrderByCustomer().get(c)!=null) {
		ArrayList<Order> a=new ArrayList<Order>(Main.res.getOrderByCustomer().get(c));
		ObservableList<Order> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);}

}
}

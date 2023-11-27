package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;

import Exceptions.EmptyFieldsException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class DeliverController implements Initializable {
	
	
	

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
	Button show;
	@FXML
	ComboBox<Delivery> cust;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cust.setVisibleRowCount(5);
		ArrayList<Delivery> a=new ArrayList<Delivery>(Main.res.getDeliveries().values());
		ObservableList<Delivery> list=FXCollections.observableArrayList(a);
		cust.setItems(list);
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		customer.setCellValueFactory(new PropertyValueFactory<>("customer"));
		dishes.setCellValueFactory(new PropertyValueFactory<>("dishes"));
		delivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
		
		
	}
	
	/*
	 * show the relevant information in the table.
	 */
	@FXML
	public void show1(ActionEvent e)
	{
		try {
		if(cust.getValue()==null)
		{
			throw new EmptyFieldsException();
		}
		TreeSet<Order> arr=new TreeSet<Order>(Main.res.deliver(cust.getValue()));
		
		ObservableList<Order> list=FXCollections.observableArrayList(arr);
		tablee.setItems(list);
		}
		catch (EmptyFieldsException e1)
		{
			Alert a=new Alert(AlertType.INFORMATION);
			a.setHeaderText(e1.getMessage());
			a.showAndWait();
		}
		
	}

}

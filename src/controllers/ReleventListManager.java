package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import Exceptions.EmptyFieldsException;
import Utils.DishType;
import application.Main;
import application.model.Component;
import application.model.Customer;
import application.model.Dish;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReleventListManager implements Initializable{
	
	
	@FXML 
	TableView<Dish> tablee;
	@FXML
	TableColumn<Dish,String> name;
	@FXML
	TableColumn<Dish,DishType> type;
	@FXML
	TableColumn<Dish,ArrayList<Component>> components;
	@FXML
	TableColumn<Dish,Double> price;
	@FXML
	TableColumn<Dish,Integer> time;
	@FXML
	TableColumn<Dish,Integer> id;
	@FXML
	Button show;
	@FXML
	ComboBox<Customer> cust;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		cust.setVisibleRowCount(5);
		ArrayList<Customer> a=new ArrayList<Customer>(Main.res.getCustomers().values());
		ObservableList<Customer> list=FXCollections.observableArrayList(a);
		cust.setItems(list);
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("dishName"));
		type.setCellValueFactory(new PropertyValueFactory<>("type"));
		components.setCellValueFactory(new PropertyValueFactory<>("componenets"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		time.setCellValueFactory(new PropertyValueFactory<>("timeToMake"));
	}
	
	
	/*
	 * set the table's items(relevnt list for the given customer).
	 */
	@FXML
	public void show1(ActionEvent e)
	{
		try {
		if(cust.getValue()==null)
		{
			throw new EmptyFieldsException();
		}
		Collection<Dish> arr=new ArrayList<Dish>( Main.res.getReleventDishList(cust.getValue()));
		
		ObservableList<Dish> list=FXCollections.observableArrayList(arr);
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

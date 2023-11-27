package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Customer;
import application.model.Dish;
import application.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ManagerAddOrderController implements Initializable {
	
	@FXML
	Button addordeer;
	@FXML
    ListView<Dish> ordDishes;
	@FXML
	ComboBox<Customer> ordCustomer;
	private ArrayList<Dish> arr;
	
	/*
	 * set all the items and add the listener to data structures in order
	 * to save the selected items
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		arr=new ArrayList<Dish>();
		ArrayList<Customer> a=new ArrayList<Customer>(Main.res.getCustomers().values());
		ObservableList<Customer> list=FXCollections.observableArrayList(a);
		ordCustomer.setItems(list);
		ArrayList<Dish> a2=new ArrayList<Dish>(Main.res.getDishes().values());
		ObservableList<Dish> list2=FXCollections.observableArrayList(a2);
		ordDishes.setItems(list2);
		ordDishes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ordCustomer.setVisibleRowCount(5);
		ordDishes.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			
			if(ordDishes.getSelectionModel().getSelectedItem()!=null) {
	       arr.add(ordDishes.getSelectionModel().getSelectedItem());}
		});
		
		
	}
	
	/*this method add an order to the restaurant after checking if all the fields are valid.
	 * the id is the max id that exist plus 1.
	 */
	@FXML
	public void add(ActionEvent e)
	{
		try {
			if(ordCustomer.getValue()==null||ordDishes.getSelectionModel().getSelectedItems().size()==0)
			{
				throw new EmptyFieldsException();
			}
			ArrayList<Dish> ar1=new ArrayList<Dish>(arr);
			Customer c=ordCustomer.getValue();
			Order o=new Order(c,ar1,null);
			int max=-1;
			for(Integer x:Main.res.getOrders().keySet())
			{
				if(x.intValue()>max)
				{
					max=x.intValue();
				}
			}
			o.setId(max+1);
			
			if(Main.res.addOrder(o))
			{
				Main.update();
				arr.removeAll(ar1);
				clear1();
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Successfully added order");
				a.showAndWait();
		
			}
			else
			{
				clear1();
				arr.removeAll(ar1);
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Failed to add Order!");
				a.showAndWait();
			}
			
		}
		catch(EmptyFieldsException e3)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText(e3.getMessage());
			a.showAndWait();
		}
	}
	
			
	//clear all the fields.
	public void clear1()
	{
		ordDishes.getSelectionModel().clearSelection();
		ordCustomer.getSelectionModel().clearSelection();
	}
	
	
	

}

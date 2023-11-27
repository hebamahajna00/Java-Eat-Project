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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;

public class UpdateOrdersController implements Initializable {
	
	@FXML
	Button update;
	@FXML
    ListView<Dish> ordDishes;
	@FXML
	ListView<Dish> dishesToRe;
	@FXML
	ComboBox<Order> compoo;

	@FXML
	ComboBox<Customer> ordCustomer;
	private ArrayList<Dish> arr;
	private ArrayList<Dish> arrRe;

	
	
	/*
	 * set all the items and add the listener to data structures in order
	 * to save the selected items
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		compoo.setVisibleRowCount(5);
		ordCustomer.setVisibleRowCount(5);
		arr=new ArrayList<Dish>();
		arrRe=new ArrayList<Dish>();
		setNewData();
		ArrayList<Customer> aa=new ArrayList<Customer>(Main.res.getCustomers().values());
		ObservableList<Customer> lista=FXCollections.observableArrayList(aa);
		ordCustomer.setItems(lista);
		ordDishes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ordDishes.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(ordDishes.getSelectionModel().getSelectedItem()!=null) {
	       arr.add(ordDishes.getSelectionModel().getSelectedItem());}
		});
		dishesToRe.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		dishesToRe.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(dishesToRe.getSelectionModel().getSelectedItem()!=null) {
	       arrRe.add(dishesToRe.getSelectionModel().getSelectedItem());}
		});
		
		
	}
	
	//set combo box data.
	public void setNewData()
	{
		ArrayList<Order> a=new ArrayList<Order>(Main.res.getOrders().values());
		ObservableList<Order> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}

	
	//show the given order information.
		@FXML
	public void showInformation(ActionEvent e)
	{
		Order c=compoo.getSelectionModel().getSelectedItem();
		if(c!=null)
		{
			ordCustomer.setValue(c.getCustomer());
			ArrayList<Dish> a2=new ArrayList<Dish>(c.getDishes());
			ObservableList<Dish> list2=FXCollections.observableArrayList(a2);
			dishesToRe.setItems(list2);
			
			ArrayList<Dish> a=new ArrayList<Dish>(Main.res.getReleventDishList(ordCustomer.getValue()));
			ObservableList<Dish> list=FXCollections.observableArrayList(a);
			ordDishes.setItems(list);
			
		}
	}
	
		/*
		 * update the order after making sure that all the fields are valid.
		 * update it by adding chosen dishes ,remove chosen dishes and customer.
		 */
		@FXML
	public void updatee(ActionEvent e)
	{
		try {
			boolean x=true;
			if(ordCustomer.getValue()==null)
			{
				throw new EmptyFieldsException();
			}
			
			ArrayList<Dish> ar=new ArrayList<Dish>(arr);
		    ArrayList<Dish> arRemove=new ArrayList<Dish>(arrRe);
		    Order d=compoo.getValue();
		    if(d!=null)
		    {
		    	for(Dish cc:ar)
		    	{
		    		if(x==true&&!d.addDish(cc))
		    		{
		    			x=false;
		    			Alert a=new Alert(AlertType.CONFIRMATION);
		    			a.setHeaderText("failed to update!");
		    			a.showAndWait();
		    			clear1();
		    			arr.removeAll(ar);
		    			arrRe.removeAll(arRemove);
		    		}
		    	}
		    	for(Dish aw:arRemove)
		    	{
		    		if(x==true&&!d.removeDish(aw))
		    		{
		    			x=false;
		    			Alert a=new Alert(AlertType.CONFIRMATION);
		    			a.setHeaderText("failed to update");
		    			a.showAndWait();
		    			clear1();
		    			arr.removeAll(ar);
		    			arrRe.removeAll(arRemove);
		    		}
		    	}
		    	if(x==true) {
		    	d.setCustomer(ordCustomer.getValue());
		    	Main.update();
		    	clear1();
		    	arr.removeAll(ar);
    			arrRe.removeAll(arRemove);
		    	Alert a=new Alert(AlertType.CONFIRMATION);
    			a.setHeaderText("Successfully updated Order!");
    			a.showAndWait();
    			setNewData();
		    	}
		    }
		    else
		    {
		    	Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("you must choose an Order!!!");
				arr.removeAll(ar);
    			arrRe.removeAll(arRemove);
    			clear1();
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
		dishesToRe.setItems(null);
		ordDishes.getSelectionModel().clearSelection();
		compoo.getSelectionModel().clearSelection();
	}
	
	

}

package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Customer;
import application.model.Dish;
import application.model.HelpingClass;
import application.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;

public class CustomerAddOrderController implements Initializable{
	
	

	@FXML
	ListView<Dish> allDishes;
	private ArrayList<Dish> arr;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		arr=new ArrayList<Dish>();
		Customer c=HelpingClass.getInstance().getUser();
		ArrayList<Dish> a=new ArrayList<Dish>(Main.res.getReleventDishList(c));
		ObservableList<Dish> list=FXCollections.observableArrayList(a);
		allDishes.setItems(list);
        allDishes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		allDishes.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(allDishes.getSelectionModel().getSelectedItem()!=null) {
	       arr.add(allDishes.getSelectionModel().getSelectedItem());}
		});
		
		

}
	
	/*
	 * i gave the customer the opportunity to add order directly without using the shopping cart,
	 * this method add the order to the restaurant.
	 * the id is the max id that exist plus 1.
	 */
	@FXML
	public void add(ActionEvent e)
	{
		try {
			if(allDishes.getSelectionModel().getSelectedItems().size()==0)
			{
				throw new EmptyFieldsException();
			}
			ArrayList<Dish> ar1=new ArrayList<Dish>(arr);
			Customer c=HelpingClass.getInstance().getUser();
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
		allDishes.getSelectionModel().clearSelection();
		
	}
	
}

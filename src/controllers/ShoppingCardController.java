package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import Utils.DishType;
import application.Main;
import application.model.Component;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShoppingCardController  implements Initializable {
	

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
	Label ppp;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("dishName"));
		type.setCellValueFactory(new PropertyValueFactory<>("type"));
		components.setCellValueFactory(new PropertyValueFactory<>("componenets"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		time.setCellValueFactory(new PropertyValueFactory<>("timeToMake"));
		setNewData();
	}
	
	//set the relevant data in the table.
	public void setNewData()
	{
		double summ=0.0;
		Customer c=HelpingClass.getInstance().getUser();
		ArrayList<Dish> a=new ArrayList<Dish>(c.getSalKneyot());
		ObservableList<Dish> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);
		for(Dish dii:a)
		{
			summ+=dii.getPrice();
		}
		ppp.setText("the price is: "+summ);
	}
	
	
	/*
	 * add the order to res after checking that the table is not empty.
	 */
	@FXML
	public void orderr(ActionEvent e)
	{
		try {
			
		if(tablee.getItems()==null)
		{
			throw new EmptyFieldsException();
		}
		ArrayList<Dish> arr=new ArrayList<Dish>(tablee.getItems());
		Customer c=HelpingClass.getInstance().getUser();
		Order o=new Order(c,arr,null);
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
			for(Dish d:arr)
			{
				c.removeOrderFromSal(d);
			}
			setNewData();
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Successfully added order");
			a.showAndWait();
		}
		else
		{
			
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Failed to add Order!");
			a.showAndWait();
		}
		
		}
		catch(EmptyFieldsException e3)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("There is no current Dishes!!");
			a.showAndWait();
		}
		
	}

}

package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.model.Customer;
import application.model.Dish;
import application.model.HelpingClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class CustomerAddDishesController  implements Initializable{
	
	
	
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
	 * this method add a dish to the shopping cart(i want to save it for a long time , thats why
	 * i added a new data structure in the customer class)
	 */
	@FXML
	public void add(ActionEvent e)
	{
		Customer c=HelpingClass.getInstance().getUser();
		ArrayList<Dish> a=new ArrayList<Dish>(arr);
		boolean x=true;
		for(Dish d:a)
		{
			if(!c.addOrderToSal(d))
			{
				x=false;
				Alert ale=new Alert(AlertType.INFORMATION);
				ale.setHeaderText("Failed To Add!");
				ale.showAndWait();
				arr.removeAll(a);
			}
		}
		if(x==true) {
		Main.update();
		Alert ale=new Alert(AlertType.INFORMATION);
		ale.setHeaderText("successfully added dishes");
		ale.showAndWait();
		arr.removeAll(a);
		allDishes.getSelectionModel().clearSelection();
		}
		
		
	}
	}
	
	



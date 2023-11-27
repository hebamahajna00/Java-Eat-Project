package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Utils.DishType;
import application.Main;
import application.model.Component;
import application.model.Dish;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;


public class MenuController implements Initializable{
	
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
	

	// set the table's data.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		name.setCellValueFactory(new PropertyValueFactory<>("dishName"));
		type.setCellValueFactory(new PropertyValueFactory<>("type"));
		components.setCellValueFactory(new PropertyValueFactory<>("componenets"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		time.setCellValueFactory(new PropertyValueFactory<>("timeToMake"));
		ArrayList<Dish> a=new ArrayList<Dish>(Main.res.getDishes().values());
		ObservableList<Dish> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);
		
	}
	
	
	


}

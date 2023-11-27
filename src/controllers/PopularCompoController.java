package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.model.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PopularCompoController implements Initializable{
	
	
	@FXML 
	TableView<Component> tablee;
	@FXML
	TableColumn<Component,String> name;
	@FXML
	TableColumn<Component,Double> price;
	@FXML
	TableColumn<Component,Boolean> lac;
	@FXML
	TableColumn<Component,Boolean> glu;
	@FXML
	TableColumn<Component,Integer> id;
	
	//set popular component in the table.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("componentName"));
		lac.setCellValueFactory(new PropertyValueFactory<>("hasLactose"));
		glu.setCellValueFactory(new PropertyValueFactory<>("hasGluten"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		ArrayList<Component> a=new ArrayList<Component>(Main.res.getPopularComponents());
		ObservableList<Component> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);
		
	}

}

package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Utils.Gender;
import Utils.Neighberhood;
import application.Main;
import application.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BlackListController implements Initializable{
	
	
	@FXML 
	TableView<Customer> tablee;
	@FXML
	TableColumn<Customer,Integer> id;
	@FXML
	TableColumn<Customer,String> firstname ;
	@FXML
	TableColumn<Customer,String> lastname;
	@FXML
	TableColumn<Customer,LocalDate> date;
	@FXML
	TableColumn<Customer,Gender> gender;
	@FXML
	TableColumn<Customer,Neighberhood> neighberhood;
	@FXML
	TableColumn<Customer,Boolean> lac;
	@FXML
	TableColumn<Customer,Boolean> glu;
	
	
	//set the table's data
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		date.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		lac.setCellValueFactory(new PropertyValueFactory<>("isSensitiveToLactose"));
		glu.setCellValueFactory(new PropertyValueFactory<>("isSensitiveToGluten"));
		neighberhood.setCellValueFactory(new PropertyValueFactory<>("neighberhood"));
		
		ArrayList<Customer> a=new ArrayList<Customer>(Main.res.getBlackList());
		ObservableList<Customer> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);
		
		
		
	}
	

}

package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Utils.Expertise;
import Utils.Gender;
import application.Main;
import application.model.Cook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class AllCooksController implements Initializable{
	
	@FXML 
	TableView<Cook> tablee;
	@FXML
	TableColumn<Cook,Integer> id;
	@FXML
	TableColumn<Cook,String> firstname ;
	@FXML
	TableColumn<Cook,String> lastname;
	@FXML
	TableColumn<Cook,LocalDate> date;
	@FXML
	TableColumn<Cook,Gender> gender;
	@FXML
	TableColumn<Cook,Expertise> expertise;
	@FXML
	TableColumn<Cook,Boolean> chef;
	@FXML
	Label errorid;
	@FXML
	TextField idsearch;
	@FXML 
	Button search;
	
	
	
	
	//show the data in the table.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		date.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		expertise.setCellValueFactory(new PropertyValueFactory<>("expert"));
		chef.setCellValueFactory(new PropertyValueFactory<>("isChef"));
		
		ArrayList<Cook> a=new ArrayList<Cook>(Main.res.getCooks().values());
		ObservableList<Cook> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);
		
		
		
	}
	
	
	
	/*
	 * the method help the user know if the inputs are valid.
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(idsearch.getText().chars().allMatch(Character::isDigit)) {
			  errorid.setText("");
			  idsearch.setStyle("-fx-border-color:green;");
			}
			else {
				
			    errorid.setText("*Invalid Input!");
			    idsearch.setStyle("-fx-border-color:red;");
			    }
	}
	
	/*
	 * i gave the user the opportunity to search for a specific cook using id ,
	 * here i used lambda.
	 */
	@FXML
	 public void search1(ActionEvent e)
	 {
		 if(idsearch.getText().equals("")||!idsearch.getText().chars().allMatch(Character::isDigit))
		 {
			 Alert a=new Alert(AlertType.CONFIRMATION);
			 a.setHeaderText("Invalid Input!!!");
			 a.showAndWait();
		 }
		 else
		 {
			 int id1=Integer.parseInt(idsearch.getText());
			 if(Main.res.getRealCook(id1)==null)
			 {
				 Alert a=new Alert(AlertType.INFORMATION);
				 a.setHeaderText("doesnt Exist!");
				 a.showAndWait();
			 }
			 else {
			 tablee.getItems().stream().filter(item->item.getId()==id1).findAny().ifPresent(item->{
				 tablee.getSelectionModel().select(item);
				 tablee.scrollTo(item);
				 
			 });}
			 
		 }
	 }

}


package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Utils.Gender;
import Utils.Vehicle;
import application.Main;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
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

public class AllPersonsController implements Initializable {
	
	
	
	@FXML 
	TableView<DeliveryPerson> tablee;
	@FXML
	TableColumn<DeliveryPerson,Integer> id;
	@FXML
	TableColumn<DeliveryPerson,String> firstname ;
	@FXML
	TableColumn<DeliveryPerson,String> lastname;
	@FXML
	TableColumn<DeliveryPerson,LocalDate> date;
	@FXML
	TableColumn<DeliveryPerson,Gender> gender;
	@FXML
	TableColumn<DeliveryPerson,Vehicle> vehicle;
	@FXML
	TableColumn<DeliveryPerson,DeliveryArea> area;
	@FXML
	Label errorid;
	@FXML
	TextField idsearch;
	@FXML 
	Button search;
	
	
	
	
	//set the table's data.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		date.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		vehicle.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
		area.setCellValueFactory(new PropertyValueFactory<>("area"));
		
		ArrayList<DeliveryPerson> a=new ArrayList<DeliveryPerson>(Main.res.getDeliveryPersons().values());
		ObservableList<DeliveryPerson> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);
		
		
		
	}
	
	
	
	
	/*
	 * this method help the user to know if the inputs are valid.
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
	 * i gave the user the opportunity to search for a specific person using id ,
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
			 if(Main.res.getRealDeliveryPerson(id1)==null)
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

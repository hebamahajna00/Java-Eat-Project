package controllers;

import java.io.ByteArrayInputStream;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AllCustController implements Initializable{
	
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
	@FXML
	Label errorid;
	@FXML
	TextField idsearch;
	@FXML 
	Button search;
	@FXML
	ImageView custImage;
	
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
		
		ArrayList<Customer> a=new ArrayList<Customer>(Main.res.getCustomers().values());
		ObservableList<Customer> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);
		
		
		
	}
	//the method helps the user to know if the inputs are valid.
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
	 * i gave the user the opportunity to search for a specific customer using id ,
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
			 if(Main.res.getRealCustomer(id1)==null)
			 {
				 Alert a=new Alert(AlertType.INFORMATION);
				 a.setHeaderText("doesnt Exist!");
				 a.showAndWait();
			 }
			 else {
			 tablee.getItems().stream().filter(item->item.getId()==id1).findAny().ifPresent(item->{
				 tablee.getSelectionModel().select(item);
				 tablee.scrollTo(item);
				 
			 });
			 }
		 }
	 }
	
	/*
	 * if we clicked item the image will appear.
	 */
	@FXML
	 public void clickItem(MouseEvent event)
	 {
		 Customer c=tablee.getSelectionModel().getSelectedItem();
		 if(c!=null) {
		 byte[] imagee=c.getPhoto();
		 setPic(imagee);
		 }
		 
	 }
	 
	//set cust pic.
	 private void setPic(byte[] castPhoto) {
		 if(castPhoto!=null) {
	        
	        	Image image=new Image(new ByteArrayInputStream(castPhoto));
	            custImage.setImage(image);
		 }
		 else
		 {
			 custImage.setImage(new Image("File:Images/noPhoto.png"));
		 }
	 }

	    }
	

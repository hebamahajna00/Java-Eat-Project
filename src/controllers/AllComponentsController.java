package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.model.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class AllComponentsController implements Initializable{
	
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
	@FXML
	Label errorid;
	@FXML
	TextField idsearch;
	@FXML 
	Button search;
	

	//the method shows the data in the table.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("componentName"));
		lac.setCellValueFactory(new PropertyValueFactory<>("hasLactose"));
		glu.setCellValueFactory(new PropertyValueFactory<>("hasGluten"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		ArrayList<Component> a=new ArrayList<Component>(Main.res.getComponenets().values());
		ObservableList<Component> list=FXCollections.observableArrayList(a);
		tablee.setItems(list);
		
	}
	
	/*
	 * this method helps the user to know if the inputs are valid.
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
	 * i gave the user the opportunity to search for a specific component using id ,
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
			 if(Main.res.getRealComponent(id1)==null)
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
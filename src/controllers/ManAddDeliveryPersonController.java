package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class ManAddDeliveryPersonController implements Initializable{
	
	@FXML
	Button addPerson;
	@FXML
	TextField firstName;
	@FXML
	TextField lastName;
	@FXML
	ChoiceBox<String> vehicle;
	@FXML 
	ChoiceBox<String> gender;
	@FXML 
	ComboBox<DeliveryArea> area;
	@FXML
	DatePicker birth;
	@FXML
	Label nameError;
	@FXML
	Label lastError;

	@Override
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<DeliveryArea> a=new ArrayList<DeliveryArea>(Main.res.getAreas().values());
		ObservableList<DeliveryArea> list=FXCollections.observableArrayList(a);
		area.setItems(list);
		area.setVisibleRowCount(5);
	}
	
	/*
	 * this method add delivery person after checking if all the fields are valid.
	 * * the id is the max id that exist plus 1.
	 */
	@FXML
	public void add(ActionEvent e)
	{
		try {
		if(firstName.getText().equals("")||lastName.getText().equals("")||vehicle.getValue()==null||gender.getValue()==null||area.getValue()==null||birth.getValue()==null)
		{
			throw new EmptyFieldsException();
		}
		if(!firstName.getText().chars().allMatch(Character::isLetter)||!lastName.getText().chars().allMatch(Character::isLetter))
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Invalid Input!");
			a.showAndWait();
		}
		else
		{
			DeliveryArea d=area.getValue();
			DeliveryPerson p=new DeliveryPerson(firstName.getText(),lastName.getText(),birth.getValue(),Gender.valueOf(gender.getValue().toString()),Vehicle.valueOf(vehicle.getValue().toString()),d);
			int max=-1;
			for(Integer x:Main.res.getDeliveryPersons().keySet())
			{
				if(x.intValue()>max)
				{
					max=x;
				}
			}
			p.setId(max+1);
			if(Main.res.addDeliveryPerson(p, d))
			{
				Main.update();
				clear1();
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Successfully added Delivery Person!");
				a.showAndWait();
			}
			else
			{
				clear1();
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Failed to add delivery Person!");
				a.showAndWait();
			}
		}
		}
		catch(EmptyFieldsException e2)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("You must fill all the fields!");
			a.showAndWait();
		}
		
	}
	
	//clear all fields.
	public void clear1()
	{
		firstName.setText("");
		lastName.setText("");
		birth.setValue(null);
		vehicle.getSelectionModel().clearSelection();
		gender.getSelectionModel().clearSelection();
		area.getSelectionModel().clearSelection();
		nameError.setText("");
		lastError.setText("");
		firstName.setStyle("-fx-border-color:transparent;");
		lastName.setStyle("-fx-border-color:transparent;");
		
		
		
	}
	
	/*the key pressed method helped me to make the application easier,it shows to the 
	 * user if the inputs are valid
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(e.getSource()==firstName)
		{
			if(!firstName.getText().chars().allMatch(Character::isLetter))
			{
				nameError.setText("*First name must contains only characters!");
				firstName.setStyle("-fx-border-color:red;");
			}
			else
			{
				nameError.setText("");
				firstName.setStyle("-fx-border-color:green;");
			}
		}
		if(e.getSource()==lastName)
		
			if(lastName.getText().chars().allMatch(Character::isLetter)) {
			  lastError.setText("");
			  lastName.setStyle("-fx-border-color:green;");
			}
			else {
				
			    lastError.setText("*Last name must contains only characters!");
			    lastName.setStyle("-fx-border-color:red;");
			    }
			
		
	}
	
	
	

}

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class UpdateDeliveryPersonController  implements Initializable{
	
	@FXML
	Button update;
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
	@FXML
	ComboBox<DeliveryPerson> compoo;

	@Override
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<DeliveryArea> a=new ArrayList<DeliveryArea>(Main.res.getAreas().values());
		ObservableList<DeliveryArea> list=FXCollections.observableArrayList(a);
		area.setItems(list);
		setNewData();
		area.setVisibleRowCount(5);
		compoo.setVisibleRowCount(5);
	}
	
	//set combo data.
	public void setNewData()
	{
		ArrayList<DeliveryPerson> a=new ArrayList<DeliveryPerson>(Main.res.getDeliveryPersons().values());
		ObservableList<DeliveryPerson> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}
	
	//show the given delivery  person information.
			@FXML
	public void showInformation(ActionEvent e)
	{
		DeliveryPerson c=compoo.getSelectionModel().getSelectedItem();
		if(c!=null)
		{
			firstName.setText(c.getFirstName());
			lastName.setText(c.getLastName());
			birth.setValue(c.getBirthDay());
			gender.setValue(c.getGender().toString());
			vehicle.setValue(c.getVehicle().toString());
			area.setValue(c.getArea());
			
			
		}
	}
	
		
	/*
	 * update the person after making sure that all the fields are valid.
	 */
	@FXML
	public void updatee(ActionEvent e)
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
			DeliveryPerson per=compoo.getValue();
			if(per!=null)
			{
				DeliveryArea before=per.getArea();
				if(before!=null)
				{
					before.removeDelPerson(per);
				}
				per.setArea(d);
				d.addDelPerson(per);
				per.setFirstName(firstName.getText());
				per.setLastName(lastName.getText());
				per.setVehicle(Vehicle.valueOf(vehicle.getValue().toString()));
				per.setBirthDay(birth.getValue());
				per.setGender(Gender.valueOf(gender.getValue().toString()));
				Main.update();
				clear1();
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Successfully updated Delivery Person!!!");
				a.showAndWait();
				setNewData();
				}
				else
				{
					Alert a=new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("you must choose a Delivery Person!!!");
					a.showAndWait();
					clear1();
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
	
	//clear all the fields.
	public void clear1()
	{
		firstName.setText("");
		compoo.setValue(null);
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

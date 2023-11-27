package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Exceptions.EmptyFieldsException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class UpdateCustomersController implements Initializable{
	
	@FXML 
	TextField custFirst;
	@FXML 
	TextField custLast;
	@FXML 
	DatePicker custBirth;
	@FXML 
	ChoiceBox<String> CustGender;
	@FXML 
	ComboBox<String> CustNeigh;
	@FXML 
	TextField userN;
	@FXML
	PasswordField pass1;
	@FXML 
	Label passE;
	@FXML 
	CheckBox yesgluten;
	@FXML 
	CheckBox nogluten;
	@FXML 
	CheckBox yeslactose;
	@FXML 
	Button update;
	@FXML
	ComboBox<Customer> compoo;
	@FXML 
	CheckBox nolactose;
	@FXML
	Label nameError;
	@FXML 
	Label lastError;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNewData();
		CustNeigh.setVisibleRowCount(5);
		compoo.setVisibleRowCount(5);
		
		
	}
	
	//set combo data.
	public void setNewData()
	{
		ArrayList<Customer> a=new ArrayList<Customer>(Main.res.getCustomers().values());
		ObservableList<Customer> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}
	
	//show the given cook information.
			@FXML
	public void showInformation(ActionEvent e)
	{
		Customer c=compoo.getSelectionModel().getSelectedItem();
		if(c!=null)
		{
			custFirst.setText(c.getFirstName());
			custLast.setText(c.getLastName());
			custBirth.setValue(c.getBirthDay());
			pass1.setText(c.getPass());
			userN.setText(c.getUserName());
			CustGender.setValue(c.getGender().toString());
			CustNeigh.setValue(c.getNeighberhood().toString());
			if(c.getIsSensitiveToGluten())
			{
				yesgluten.setSelected(true);
				nogluten.setSelected(false);
			}
			else
			{
				yesgluten.setSelected(false);
				nogluten.setSelected(true);
			}
			if(c.getIsSensitiveToLactose())
			{
				yeslactose.setSelected(true);
				nolactose.setSelected(false);
			}
			else
			{
				yeslactose.setSelected(false);
				nolactose.setSelected(true);
			}
			
			
			
		}
	}
	
	/*
	 * update the customer after making sure that all the fields are valid.
	 */
	@FXML
	public void updatee(ActionEvent e)
	{
		try {
			
			if(!yesgluten.isSelected()&&!nogluten.isSelected())
			{
				throw new EmptyFieldsException();
			}
			if(!yeslactose.isSelected()&&!nolactose.isSelected())
			{
				throw new EmptyFieldsException();
			}
		if(custFirst.getText().equals("")||custLast.getText().equals("")||custBirth.getValue()==null||CustGender.getValue()==null||CustNeigh.getValue()==null||userN.getText().equals("")||pass1.getText().equals(""))
		{
			throw new EmptyFieldsException();
		}
		if(passE.getText().equals("*MEDIUM")||userN.getText().equals("manager")||passE.getText().equals("*NOT STRONG")||!userN.getText().chars().allMatch(Character::isLetterOrDigit)||!custFirst.getText().chars().allMatch(Character::isLetter)||!custLast.getText().chars().allMatch(Character::isLetter))
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Invalid Inputs!!!");
			a.showAndWait();
		}
		
		else
		{
			Customer c=compoo.getValue();
			String name1=custFirst.getText();
			String last1=custLast.getText();
			String password=pass1.getText();
			String userr=userN.getText();
			boolean lac=false;
			boolean glu=false;
			
			if(yeslactose.isSelected())
			{
				lac=true;
			}
			if(yesgluten.isSelected())
			{
				glu=true;
			}
			if(c!=null) {
			c.setBirthDay(custBirth.getValue());
			c.setFirstName(name1);
			c.setLastName(last1);
			c.setPass(password);
			c.setUserName(userr);
			c.setNeighberhood(Neighberhood.valueOf(CustNeigh.getValue().toString()));
			c.setGender(Gender.valueOf(CustGender.getValue().toString()));
			c.setSensitiveToGluten(glu);
			c.setSensitiveToLactose(lac);
			Main.update();
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Successfully updated customer!!!");
			a.showAndWait();
			clear1();
			setNewData();
			}
			else
			{
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("you must choose a customer!!!");
				a.showAndWait();
				clear1();
			}
			
				      
					
		}
		}
		catch (EmptyFieldsException e1)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("You must fill all the fields!!!");
			a.showAndWait();
		}
		
	}
	
	//show the given component information.
			@FXML
	public void checkers(ActionEvent e) throws IOException {
		
		if(e.getSource()==yeslactose)
		{
			nolactose.setSelected(false);
		}
		if(e.getSource()==nolactose)
		{
			yeslactose.setSelected(false);
		}
		if(e.getSource()==yesgluten)
		{
			nogluten.setSelected(false);
		}
		if(e.getSource()==nogluten)
		{
			yesgluten.setSelected(false);
		}
		
		
	}
			
    //clear all the fields.
	public void clear1()
	{
		custFirst.setText("");
		compoo.setValue(null);
		custLast.setText("");
		custBirth.setValue(null);
		CustGender.getSelectionModel().clearSelection();
		CustNeigh.getSelectionModel().clearSelection();
		nameError.setText("");
		lastError.setText("");
		custFirst.setStyle("-fx-border-color:transparent;");
		custLast.setStyle("-fx-border-color:transparent;");
		passE.setText("");
		pass1.setText("");
		userN.setText("");
		pass1.setStyle("-fx-border-color:transparent;");
	}
	
	/*the key pressed method helped me to make the application easier,it shows to the 
	 * user if the inputs are valid
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(e.getSource()==custFirst)
		{
			if(!custFirst.getText().chars().allMatch(Character::isLetter))
			{
				nameError.setText("*First name must contains only characters!");
				custFirst.setStyle("-fx-border-color:red;");
			}
			else
			{
				nameError.setText("");
				custFirst.setStyle("-fx-border-color:green;");
			}
		}
		if(e.getSource()==custLast) {
		
			if(custLast.getText().chars().allMatch(Character::isLetter)) {
			  lastError.setText("");
			  custLast.setStyle("-fx-border-color:green;");
			}
			else {
				
			    lastError.setText("*Last name must contains only characters!");
			    custLast.setStyle("-fx-border-color:red;");
			    }
		}
		if(e.getSource()==pass1)
		{
			boolean hasLetter = false;
	        boolean hasDigit = false;
	        String password=pass1.getText();

	        if (password.length() >= 8) {
	            for (int i = 0; i < password.length(); i++) {
	                char x = password.charAt(i);
	                if (Character.isLetter(x)) {

	                    hasLetter = true;
	                }

	                else if (Character.isDigit(x)) {

	                    hasDigit = true;
	                }
	                if(hasLetter && hasDigit){

	                    break;
	                }

	            }
	            if (hasLetter && hasDigit) {
	               
	                passE.setText("*STRONG");
	                passE.setTextFill(Color.GREEN);
	                pass1.setStyle("-fx-border-color:green;");
	                
	            } else {
	                
	                passE.setText("*MEDIUM");
	                passE.setTextFill(Color.ORANGE);
	                pass1.setStyle("-fx-border-color:orange;");
	            }
	        } else {
	        	passE.setText("*NOT STRONG");
	        	passE.setTextFill(Color.RED);
	        	pass1.setStyle("-fx-border-color:red;");
	        }
	    }
			
		
	}
	
	

}

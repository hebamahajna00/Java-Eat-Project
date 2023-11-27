package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import Utils.Neighberhood;
import application.Main;
import application.model.Customer;
import application.model.HelpingClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class PersonalInfoController  implements Initializable{
	
	@FXML 
	TextField custFirst;
	@FXML 
	TextField custLast;
	@FXML 
	ComboBox<String> CustNeigh;
	@FXML 
	TextField userN;
	@FXML 
	CheckBox yesgluten;
	@FXML 
	CheckBox nogluten;
	@FXML 
	CheckBox yeslactose;
	@FXML 
	Button update;
	@FXML 
	CheckBox nolactose;
	@FXML
	Label nameError;
	@FXML 
	Label lastError;
	
	private Customer c;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		CustNeigh.setVisibleRowCount(5);
		showInformation();
	}
	
	
	//set the customer information,in order to update some fields.
	public void showInformation()
	{
		c=HelpingClass.getInstance().getUser();
		if(c!=null)
		{
			custFirst.setText(c.getFirstName());
			custLast.setText(c.getLastName());
			userN.setText(c.getUserName());
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
	 * this method updates the personal information,after making sure that all
	 * the fields are valid.
	 */
	@FXML
	public void updatee(ActionEvent e)
	{
		try {
		if(custFirst.getText().equals("")||custLast.getText().equals("")||CustNeigh.getValue()==null||userN.getText().equals(""))
		{
			throw new EmptyFieldsException();
		}
		if(!custFirst.getText().chars().allMatch(Character::isLetter)||!custLast.getText().chars().allMatch(Character::isLetter))
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Invalid Inputs!!!");
			a.showAndWait();
		}
		
		else
		{
			String name1=custFirst.getText();
			String last1=custLast.getText();
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
			c.setFirstName(name1);
			c.setLastName(last1);
			c.setUserName(userr);
			c.setNeighberhood(Neighberhood.valueOf(CustNeigh.getValue().toString()));
			c.setSensitiveToGluten(glu);
			c.setSensitiveToLactose(lac);
			Main.update();
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Successfully updated customer!!!");
			a.showAndWait();
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
	
	/*
	 * this method check if the user selected one check box.
	 */
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
		
		
	}
	

}

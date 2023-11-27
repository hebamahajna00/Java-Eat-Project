package controllers;

import java.io.IOException;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Component;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class AddComponentsController {
	
	
	@FXML
	TextField componentName;
	@FXML
	TextField componentPrice;
	@FXML
	CheckBox yesgluten;
	@FXML
	CheckBox nogluten;
	@FXML
	CheckBox yeslactose;
	@FXML
	CheckBox nolactose;
	@FXML
	Button addComponent;
	@FXML 
	Label nameError;
	@FXML 
	Label priceError;
	
	/*this method add a component to the restaurant after checking if all the fields are valid.
	 * the id is the max id that exist plus 1.
	*/
	@FXML
	public void add(ActionEvent e)
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
			if(componentName.getText().equals("")||componentPrice.getText().equals(""))
			{
				throw new EmptyFieldsException();
			}
			double i=Double.parseDouble(componentPrice.getText());
			if(!componentName.getText().matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*"))
			{
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("component name must contains only characters!!!");
				a.showAndWait();
			}
			else
			{
				boolean lac=false;
				boolean glu=false;
				if(yesgluten.isSelected())
				{
					glu=true;
				}
				if(yeslactose.isSelected())
				{
					lac=true;
				}
				Component c=new Component(componentName.getText(),lac,glu,i);
				int max=-1;
				for(Integer x:Main.res.getComponenets().keySet())
				{
					if(x>max)
					{
						max=x;
					}
				}
				c.setId(max+1);
				if(Main.res.addComponent(c))
				{
					Main.update();
					clear1();
					Alert a=new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("Seccessfully added component");
					a.showAndWait();
					
				}
				else
				{
					clear1();
					Alert a=new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("failed to add Component!");
					a.showAndWait();
				}
			}
			
		}
		catch(EmptyFieldsException e2)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("You must fill all the fields!!!");
			a.showAndWait();
		}
		catch(NumberFormatException e3)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("price must be number!!!");
			a.showAndWait();
			
		}
	}
	
	/*the key pressed method helped me to make the application easier,it shows to the 
	 * user if the inputs are valid
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(e.getSource()==componentName)
		{
			if(!componentName.getText().matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*"))
			{
				nameError.setText("*component name must contains only characters!");
				componentName.setStyle("-fx-border-color:red;");
			}
			else
			{
				nameError.setText("");
				componentName.setStyle("-fx-border-color:green;");
			}
		}
		if(e.getSource()==componentPrice)
			
		
			try {
				Double.parseDouble(componentPrice.getText());
				priceError.setText("");
				componentPrice.setStyle("-fx-border-color:green;");
			}
			catch(NumberFormatException e3)
		      {
				priceError.setText("*Invalid Number!");
			    componentPrice.setStyle("-fx-border-color:red;");
				
		      }
			
			
			
		
	}
	
	//this method clear all the fields.
	public void clear1()
	{
		componentName.setText("");
		componentPrice.setText("");
		nameError.setText("");
		priceError.setText("");
		componentPrice.setStyle("-fx-border-color:transparent;");
		componentName.setStyle("-fx-border-color:transparent;");
	}
	
	/*
	 * this method helps me to make sure that the user will selects one check box.
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

	
	
	
	
	

}

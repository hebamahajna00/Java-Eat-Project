package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class UpdateComponentController implements Initializable{
	
	
	
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
	Button update;
	@FXML 
	Label nameError;
	@FXML 
	Label priceError;
	@FXML
	ComboBox<Component> compoo;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNewData();
		
	}
	
	//show the given component information.
	@FXML
	public void showInformation(ActionEvent e)
	{
		Component c=compoo.getSelectionModel().getSelectedItem();
		if(c!=null)
		{
			componentName.setText(c.getComponentName());
			componentPrice.setText(c.getPrice()+"");
			if(c.isHasGluten())
			{
				yesgluten.setSelected(true);
				nogluten.setSelected(false);
			}
			else
			{
				yesgluten.setSelected(false);
				nogluten.setSelected(true);
			}
			if(c.isHasLactose())
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
	 * update the component after making sure that all the fields are valid.
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
			if(componentName.getText().equals("")||componentPrice.getText().equals(""))
			{
				throw new EmptyFieldsException();
			}
			double i=Double.parseDouble(componentPrice.getText());
			if(!componentName.getText().chars().allMatch(Character::isLetter))
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
				Component c=compoo.getSelectionModel().getSelectedItem();
				if(c!=null) {
				c.setComponentName(componentName.getText());
				c.setHasGluten(glu);
				c.setHasLactose(lac);
				c.setPrice(i);
				Main.update();
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Successfully updated component!!!");
				a.showAndWait();
				clear1();
				setNewData();
				}
				else
				{
					Alert a=new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("you must choose a component!!!");
					a.showAndWait();
					clear1();
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
			if(!componentName.getText().chars().allMatch(Character::isLetter))
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
	
	
	//set combo box data.
	public void setNewData()
	{
		ArrayList<Component> a=new ArrayList<Component>(Main.res.getComponenets().values());
		ObservableList<Component> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}
	
	//clear all the fields.
	public void clear1()
	{
		componentName.setText("");
		componentPrice.setText("");
		nameError.setText("");
		priceError.setText("");
		componentPrice.setStyle("-fx-border-color:transparent;");
		componentName.setStyle("-fx-border-color:transparent;");
		compoo.setValue(null);
	}
	
	
	//this method checks that we selected one check box.
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

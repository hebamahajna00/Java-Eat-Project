package controllers;

import Exceptions.EmptyFieldsException;
import Utils.Expertise;
import Utils.Gender;
import application.Main;
import application.model.Cook;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class ManagerCookAddController {
	
	
	@FXML
	TextField cookfirst;
	@FXML
	TextField cooklast;
	@FXML
	DatePicker birth;
	@FXML
	ChoiceBox<String> gender;
	@FXML
	ChoiceBox<String> expertise;
	@FXML
	CheckBox yeschef;
	@FXML
	CheckBox nochef;
	@FXML
	Button add;
	@FXML
	Label nameError;
	@FXML
	Label lastError;
	
	
	/*this method add a cook to the restaurant after checking if all the fields are valid.
	 * the id is the max id that exist plus 1.
	 */
	@FXML
	public void add(ActionEvent e)
	{
		try {
			if(!yeschef.isSelected()&&!nochef.isSelected())
			{
				throw new EmptyFieldsException();
			}
			
			if(cookfirst.getText().equals("")||cooklast.getText().equals("")||birth.getValue()==null||gender.getValue()==null||expertise.getValue()==null)
			{
				throw new EmptyFieldsException();
			}
			if(!cookfirst.getText().chars().allMatch(Character::isLetter)||!cooklast.getText().chars().allMatch(Character::isLetter))
			{
		       Alert a =new Alert(AlertType.CONFIRMATION);
		       a.setHeaderText("Input Error!");
		       a.showAndWait();
			}
			else
			{
				boolean chef1=false;
				if(yeschef.isSelected())
				{
					chef1=true;
				}
				Cook c=new Cook(cookfirst.getText(),cooklast.getText(),birth.getValue(),Gender.valueOf(gender.getValue().toString()),Expertise.valueOf(expertise.getValue().toString()),chef1);
				int max=-1;
				for(Integer x:Main.res.getCooks().keySet())
				{
					if(x.intValue()>max)
					{
						max=x.intValue();
					}
				}
				c.setId(max+1);
				if(Main.res.addCook(c))
				{
					   Main.update();
					   clear1();
					   Alert a =new Alert(AlertType.CONFIRMATION);
				       a.setHeaderText("successfully added cook!");
				       a.showAndWait();
				}
				else
				{
					   clear1();
					   Alert a =new Alert(AlertType.CONFIRMATION);
				       a.setHeaderText("failed to add cook!");
				       a.showAndWait();
					
				}
			}
			

		}
		catch(EmptyFieldsException e2)
		{
			   Alert a =new Alert(AlertType.CONFIRMATION);
		       a.setHeaderText("You must fill all the fields!");
		       a.showAndWait();
		}
	
		
	}
	
	//clear all the fields.
	public void clear1()
	{
		cookfirst.setText("");
		cooklast.setText("");
		birth.setValue(null);
		gender.getSelectionModel().clearSelection();
		expertise.getSelectionModel().clearSelection();
		nameError.setText("");
		lastError.setText("");
		cookfirst.setStyle("-fx-border-color:transparent;");
		cooklast.setStyle("-fx-border-color:transparent;");
	}
	
	//this method checks that we selected one check box.
	@FXML
	public void checkers(ActionEvent e)
	{
		if(e.getSource()==yeschef)
		{
			nochef.setSelected(false);
		}
		if(e.getSource()==nochef)
		{
			yeschef.setSelected(false);
		}
	}
	
	/*the key pressed method helped me to make the application easier,it shows to the 
	 * user if the inputs are valid
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(e.getSource()==cookfirst)
		{
			if(!cookfirst.getText().chars().allMatch(Character::isLetter))
					{
				       cookfirst.setStyle("-fx-border-color:red;");
				       nameError.setText("*Invalid Input!!!");
					}
			else
			{
				cookfirst.setStyle("-fx-border-color:green;");
				nameError.setText("");
			}
			
		}
		if(e.getSource()==cooklast)
		{
			if(!cooklast.getText().chars().allMatch(Character::isLetter))
			{
				cooklast.setStyle("-fx-border-color:red;");
				lastError.setText("*Invalid Input!!!");
			}
			else
			{
				cooklast.setStyle("-fx-border-color:green;");
				lastError.setText("");
			}
		}
	}

}

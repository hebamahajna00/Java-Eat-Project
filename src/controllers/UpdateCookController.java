package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import Utils.Expertise;
import Utils.Gender;
import application.Main;
import application.model.Cook;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class UpdateCookController implements Initializable{
	
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
	Button update;
	@FXML
	ComboBox<Cook> compoo;
	@FXML
	Label nameError;
	@FXML
	Label lastError;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNewData();
		compoo.setVisibleRowCount(5);
		
		
	}
	
	//set combo box data.
	public void setNewData()
	{
		ArrayList<Cook> a=new ArrayList<Cook>(Main.res.getCooks().values());
		ObservableList<Cook> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}
	
	
	/*
	 * update the cook after making sure that all the fields are valid.
	 */
	@FXML
	public void updatee(ActionEvent e)
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
				Cook cc=compoo.getValue();
				if(cc!=null)
				{
					cc.setFirstName(cookfirst.getText());
					cc.setLastName(cooklast.getText());
					cc.setBirthDay(birth.getValue());
					cc.setGender(Gender.valueOf(gender.getValue().toString()));
					cc.setExpert(Expertise.valueOf(expertise.getValue().toString()));
					cc.setIsChef(chef1);
					Main.update();
					Alert a=new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("Successfully updated cook!!!");
					a.showAndWait();
					clear1();
					setNewData();
					}
					else
					{
						Alert a=new Alert(AlertType.CONFIRMATION);
						a.setHeaderText("you must choose a cook!!!");
						a.showAndWait();
						clear1();
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
	
	//show the given cook information.
		@FXML
		public void showInformation(ActionEvent e)
		{
			Cook c=compoo.getSelectionModel().getSelectedItem();
			if(c!=null)
			{
				cookfirst.setText(c.getFirstName());
				cooklast.setText(c.getLastName());
				birth.setValue(c.getBirthDay());
				gender.setValue(c.getGender().toString());
				expertise.setValue(c.getExpert().toString());
				if(c.getIsChef())
				{
					yeschef.setSelected(true);
					nochef.setSelected(false);
				}
				else
				{
					yeschef.setSelected(false);
					nochef.setSelected(true);
				}
			}
				
				
			
		}
	
		//clear all the fields.
	public void clear1()
	{
		cookfirst.setText("");
		cooklast.setText("");
		birth.setValue(null);
		compoo.setValue(null);
		gender.getSelectionModel().clearSelection();
		expertise.getSelectionModel().clearSelection();
		nameError.setText("");
		lastError.setText("");
		cookfirst.setStyle("-fx-border-color:transparent;");
		cooklast.setStyle("-fx-border-color:transparent;");
	}
	
	//show the given component information.
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

package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import Utils.DishType;
import application.Main;
import application.model.Component;
import application.model.Dish;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class ManagerAddDishController implements Initializable {
	
	@FXML
	Button addDish;
	@FXML
	ListView<Component> components;
	@FXML 
	TextField name;
	
	@FXML 
	TextField timeToMake;
	@FXML
	ChoiceBox<String> type;
	@FXML 
	Label nameError;
	
	@FXML
	Label timeError;
	
	private ArrayList<Component> arr;
	
	/*
	 * set all the items and add the listener to data structures in order
	 * to save the selected items
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		arr=new ArrayList<Component>();
		components.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ArrayList<Component> a=new ArrayList<Component>(Main.res.getComponenets().values());
		ObservableList<Component> list=FXCollections.observableArrayList(a);
		components.setItems(list);
		components.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(components.getSelectionModel().getSelectedItem()!=null) {
	       arr.add(components.getSelectionModel().getSelectedItem());}
		});
		
	}
	
	/*this method add a dish to the restaurant after checking if all the fields are valid.
	 *  the id is the max id that exist plus 1.
	 */
		@FXML
	public void add(ActionEvent e)
	{
		try {
		if(name.getText().equals("")||timeToMake.getText().equals("")||type.getValue()==null||components.getSelectionModel().getSelectedItems().size()==0)
		{
			throw new EmptyFieldsException();
		}
		if(!name.getText().matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*"))
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Invalid Input!!!");
			a.showAndWait();
		}
		else {
		    int time=Integer.parseInt(timeToMake.getText());
		  
		    ArrayList<Component> ar=new ArrayList<Component>(arr);
		    Dish d=new Dish(name.getText(),DishType.valueOf(type.getValue().toString()),ar,time);
		    int max=-1;
			for(Integer x:Main.res.getDishes().keySet())
			{
				if(x.intValue()>max)
				{
					max=x.intValue();
				}
			}
			d.setId(max+1);
		    if(Main.res.addDish(d))
		    {
		    	arr.removeAll(ar);
		    	Main.update();
		    	clear1();
		    	Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Successfully added dish");
				a.showAndWait();
		    }
		    else
		    {
		    	clear1();
		    	arr.removeAll(ar);
		    	Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Failed to add Dish!");
				a.showAndWait();
		    }
		    
		}
		}
		catch(EmptyFieldsException e3)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText(e3.getMessage());
			a.showAndWait();
		}
		catch(NumberFormatException e3)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Invalid Number!!!");
			a.showAndWait();
			
		}
	}
	
	//clear the fields.
	public void clear1()
	{
		components.getSelectionModel().clearSelection();
		name.setText("");
		timeToMake.setText("");
		type.getSelectionModel().clearSelection();
		nameError.setText("");
		timeError.setText("");
		name.setStyle("-fx-border-color:transparent;");
		timeToMake.setStyle("-fx-border-color:transparent;");
	}
	
	/*the key pressed method helped me to make the application easier,it shows to the 
	 * user if the inputs are valid
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(e.getSource()==name)
		{
			if(!name.getText().matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*"))
					{
				       name.setStyle("-fx-border-color:red;");
				       nameError.setText("*Invalid Input!!!");
					}
			else
			{
				name.setStyle("-fx-border-color:green;");
				nameError.setText("");
			}
			
		}
		if(e.getSource()==timeToMake) {
			
			if(timeToMake.getText().chars().allMatch(Character::isDigit)) {
			  timeError.setText("");
			  timeToMake.setStyle("-fx-border-color:green;");
			}
			else {
				
			    timeError.setText("*Invalid Number!");
			    timeToMake.setStyle("-fx-border-color:red;");
			    }
		}
	}
		
	
	

}

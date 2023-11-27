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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class UpdateDishController implements Initializable{
	
	@FXML
	Button update;
	@FXML
	ListView<Component> components;
	@FXML
	ListView<Component> componentsToRe;
	@FXML 
	TextField name;
	
	@FXML 
	TextField timeToMake;
	@FXML
	ChoiceBox<String> type;
	@FXML
	ComboBox<Dish> compoo;
	@FXML 
	Label nameError;
	
	@FXML
	Label timeError;
	
	private ArrayList<Component> arr;
	private ArrayList<Component> arrRe;
	
	
	/*
	 * set all the items and add the listener to data structures in order
	 * to save the selected items
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		compoo.setVisibleRowCount(5);
		setNewData();
		arr=new ArrayList<Component>();
		arrRe=new ArrayList<Component>();
		components.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		componentsToRe.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ArrayList<Component> a=new ArrayList<Component>(Main.res.getComponenets().values());
		ObservableList<Component> list=FXCollections.observableArrayList(a);
		components.setItems(list);
		components.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(components.getSelectionModel().getSelectedItem()!=null) {
	       arr.add(components.getSelectionModel().getSelectedItem());}
		});
		componentsToRe.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(componentsToRe.getSelectionModel().getSelectedItem()!=null) {
	       arrRe.add(componentsToRe.getSelectionModel().getSelectedItem());}
		});
		
	}
	
	
	//set combo box data.
	public void setNewData()
	{
		ArrayList<Dish> a=new ArrayList<Dish>(Main.res.getDishes().values());
		ObservableList<Dish> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}
	
	//show the given dish information.
	@FXML
	public void showInformation(ActionEvent e)
	{
		Dish c=compoo.getSelectionModel().getSelectedItem();
		if(c!=null)
		{
			name.setText(c.getDishName());
			timeToMake.setText(c.getTimeToMake()+"");
			type.setValue(c.getType().toString());
			ArrayList<Component> a2=new ArrayList<Component>(c.getComponenets());
			ObservableList<Component> list2=FXCollections.observableArrayList(a2);
			componentsToRe.setItems(list2);
			
		}
	}

	/*
	 * update the dish after making sure that all the fields are valid.
	 * update it by adding chosen component and remove chosen component.
	 */
	@FXML
	public void updatee(ActionEvent e)
	{
		try {
			boolean x=true;
		if(name.getText().equals("")||timeToMake.getText().equals("")||type.getValue()==null)
		{
			throw new EmptyFieldsException();
		}
		if(!name.getText().chars().allMatch(Character::isLetter))
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Invalid Input!!!");
			a.showAndWait();
		}
		else {
		    int time=Integer.parseInt(timeToMake.getText());
		  
		    ArrayList<Component> ar=new ArrayList<Component>(arr);
		    ArrayList<Component> arRemove=new ArrayList<Component>(arrRe);
		    Dish d=compoo.getValue();
		    if(d!=null)
		    {
		    	for(Component cc:ar)
		    	{
		    		if(x==true&&!d.addComponent(cc))
		    		{
		    			x=false;
		    			Alert a=new Alert(AlertType.CONFIRMATION);
		    			a.setHeaderText("failed to update!");
		    			a.showAndWait();
		    			clear1();
		    			arr.removeAll(ar);
		    			arrRe.removeAll(arRemove);
		    		}
		    	}
		    	for(Component aw:arRemove)
		    	{
		    		if(x==true&&!d.removeComponent(aw))
		    		{
		    			x=false;
		    			Alert a=new Alert(AlertType.CONFIRMATION);
		    			a.setHeaderText("failed to update");
		    			a.showAndWait();
		    			clear1();
		    			arr.removeAll(ar);
		    			arrRe.removeAll(arRemove);
		    		}
		    	}
		    	if(x==true) {
		    	d.setDishName(name.getText());
		    	d.setType(DishType.valueOf(type.getValue().toString()));
		    	d.setTimeToMake(time);
		    	Main.update();
		    	clear1();
		    	arr.removeAll(ar);
    			arrRe.removeAll(arRemove);
		    	Alert a=new Alert(AlertType.CONFIRMATION);
    			a.setHeaderText("Successfully updated dish!");
    			a.showAndWait();
    			setNewData();
		    	}
		    }
		    else
		    {
		    	Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("you must choose a Dish!!!");
				arr.removeAll(ar);
    			arrRe.removeAll(arRemove);
    			clear1();
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
	
	//clear all the fields.
	public void clear1()
	{
		components.getSelectionModel().clearSelection();
		compoo.getSelectionModel().clearSelection();
		name.setText("");
		timeToMake.setText("");
		type.getSelectionModel().clearSelection();
		nameError.setText("");
		timeError.setText("");
		componentsToRe.setItems(null);
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
			if(!name.getText().chars().allMatch(Character::isLetter))
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

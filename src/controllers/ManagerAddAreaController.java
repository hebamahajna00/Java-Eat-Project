package controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import Utils.Neighberhood;
import application.Main;
import application.model.DeliveryArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;

public class ManagerAddAreaController implements Initializable {
	
	@FXML
	Button addarea;
	@FXML
    TextField areaName;
	@FXML
    TextField delitime;
	@FXML
	ListView<Neighberhood> neighberhoods;
	@FXML
	Label timeError;
	@FXML
	Label nameError;
	
	
	private HashSet<Neighberhood> h;
	
	
	/*
	 * set all the items and add the listener to data structures in order
	 * to save the selected items
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		h=new HashSet<Neighberhood>();
		setData();
       
		
	}

	
	
	public void setData()
	{
		neighberhoods.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ObservableList<Neighberhood> j=FXCollections.observableArrayList(Arrays.asList(Neighberhood.values()));
		neighberhoods.getItems().clear();
		neighberhoods.getItems().addAll(j);
		neighberhoods.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(neighberhoods.getSelectionModel().getSelectedItem()!=null) {
	       h.add(neighberhoods.getSelectionModel().getSelectedItem());}
		});
		
		
		
		}
	
	
	/*
	 * this method add delivery area after checking if all the fields are valid.
	 * * the id is the max id that exist plus 1.
	 */
	@FXML
	public void addArea(ActionEvent e)
	{
		try {
		if(areaName.getText().equals("")||delitime.getText().equals("")||neighberhoods.getSelectionModel().getSelectedItems().size()==0)
		{
			throw new EmptyFieldsException();
		}
		int i=Integer.parseInt(delitime.getText());
		if(!areaName.getText().matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*"))
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("area name must contains only characters!!!");
			a.showAndWait();
		}
		
		else {
			
			String name=areaName.getText();
			HashSet<Neighberhood>h1=new HashSet<Neighberhood>(h);
			DeliveryArea d=new DeliveryArea(name,h1,i);
			int max=-1;
			for(Integer x:Main.res.getAreas().keySet())
			{
				if(x.intValue()>max)
				{
					max=x.intValue();
				}
			}
			d.setId(max+1);
			
			if(Main.res.addDeliveryArea(d))
			{
				h.removeAll(h1);
				Main.update();
				clear1();
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("successfully added area");
				a.showAndWait();
			}
			else
			{
				clear1();
				h.removeAll(h1);
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("failed to  add area");
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
		a.setHeaderText("delivery time must be number!!!");
		a.showAndWait();
		
	}
	}
	
	//clear all fields.
	public void clear1()
	{
		areaName.setText("");
		delitime.setText("");
		neighberhoods.getSelectionModel().clearSelection();
		timeError.setText("");
		nameError.setText("");
		areaName.setStyle("-fx-border-color:transparent;");
		delitime.setStyle("-fx-border-color:transparent;");
		
		
	}
	
	/*the key pressed method helped me to make the application easier,it shows to the 
	 * user if the inputs are valid
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(e.getSource()==areaName)
		{
			if(!areaName.getText().matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*"))
			{
				nameError.setText("*area name must contains only characters!");
				areaName.setStyle("-fx-border-color:red;");
			}
			else
			{
				nameError.setText("");
				areaName.setStyle("-fx-border-color:green;");
			}
		}
		if(e.getSource()==delitime)
		
			if(delitime.getText().chars().allMatch(Character::isDigit)) {
			  timeError.setText("");
			  delitime.setStyle("-fx-border-color:green;");
			}
			else {
				
			    timeError.setText("*Invalid Number!");
			    delitime.setStyle("-fx-border-color:red;");
			    }
			
		
	}
}
		
		
	


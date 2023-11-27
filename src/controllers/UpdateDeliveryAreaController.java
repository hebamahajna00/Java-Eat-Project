package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import Utils.Neighberhood;
import application.Main;
import application.model.Delivery;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class UpdateDeliveryAreaController implements Initializable{
	
	@FXML
	Button update;
	@FXML
    TextField areaName;
	
	@FXML
	ListView<Neighberhood> neighberhoods;
	@FXML
	ListView<Delivery> addDeli;
	@FXML
	ListView<Delivery> removeDeli;
	@FXML
	ListView<DeliveryPerson> addPerson;
	@FXML
	ListView<DeliveryPerson> removePerson;
	
	@FXML
	Label nameError;
	@FXML
	ListView<Neighberhood> removee;
	@FXML
	ComboBox<DeliveryArea> compoo;
	
	
	private HashSet<Neighberhood> h;
	private HashSet<Neighberhood> toRe;
	
	private HashSet<DeliveryPerson> persoon;
	private HashSet<DeliveryPerson> toRePersoon;
	
	private HashSet<Delivery> deliveryy;
	private HashSet<Delivery> toReDeliveryy;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		h=new HashSet<Neighberhood>();
		toRe=new HashSet<Neighberhood>();
		persoon= new HashSet<DeliveryPerson>();
		toRePersoon= new HashSet<DeliveryPerson>();
		deliveryy=new HashSet<Delivery> ();
		toReDeliveryy=new HashSet<Delivery>();
		compoo.setVisibleRowCount(5);
		setData();
       
		
	}

	
	
	//set the data , i gave the user the opportunity to update neighborhoods,persons ,deliveries.
	
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
		
		removee.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		removee.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			
			if(removee.getSelectionModel().getSelectedItem()!=null) {
	       toRe.add(removee.getSelectionModel().getSelectedItem());}
		});
		removePerson.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		removePerson.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(removePerson.getSelectionModel().getSelectedItem()!=null) {
	       toRePersoon.add(removePerson.getSelectionModel().getSelectedItem());}
		});
		addPerson.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		addPerson.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(addPerson.getSelectionModel().getSelectedItem()!=null) {
	       persoon.add(addPerson.getSelectionModel().getSelectedItem());}
		});
		
		
		removeDeli.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		removeDeli.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(removeDeli.getSelectionModel().getSelectedItem()!=null) {
	       toReDeliveryy.add(removeDeli.getSelectionModel().getSelectedItem());}
		});
		addDeli.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		addDeli.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(addDeli.getSelectionModel().getSelectedItem()!=null) {
	       deliveryy.add(addDeli.getSelectionModel().getSelectedItem());}
		});
		
		setNewData();
		
		
		ArrayList<Delivery> aaa=new ArrayList<Delivery>(Main.res.getDeliveries().values());
		ObservableList<Delivery> listtt=FXCollections.observableArrayList(aaa);
		addDeli.setItems(listtt);
		
		ArrayList<DeliveryPerson> pp=new ArrayList<DeliveryPerson>(Main.res.getDeliveryPersons().values());
		ObservableList<DeliveryPerson> listP=FXCollections.observableArrayList(pp);
		addPerson.setItems(listP);
		
		
		}
	
	//show the given area information.
	@FXML
	public void showInformation(ActionEvent e)
	{
		DeliveryArea c=compoo.getSelectionModel().getSelectedItem();
		if(c!=null)
		{
			areaName.setText(c.getAreaName());
			ArrayList<Neighberhood> a2=new ArrayList<Neighberhood>(c.getNeighberhoods());
			ObservableList<Neighberhood> list2=FXCollections.observableArrayList(a2);
			removee.setItems(list2);
			
			ArrayList<Delivery> de=new ArrayList<Delivery>(c.getDelivers());
			ObservableList<Delivery> deList=FXCollections.observableArrayList(de);
			removeDeli.setItems(deList);
			
			ArrayList<DeliveryPerson> dePerson=new ArrayList<DeliveryPerson>(c.getDelPersons());
			ObservableList<DeliveryPerson> dePe=FXCollections.observableArrayList(dePerson);
			removePerson.setItems(dePe);
			
			
			
			
			
		}
	}
	
	//set combo box data.
	public void setNewData()
	{
		ArrayList<DeliveryArea> a=new ArrayList<DeliveryArea>(Main.res.getAreas().values());
		ObservableList<DeliveryArea> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}
	
	
	/*
	 * update the area after making sure that all the fields are valid.
	 */
	@FXML
	public void updatee(ActionEvent e)
	{
		try {
		if(areaName.getText().equals(""))
		{
			throw new EmptyFieldsException();
		}
		
		if(!areaName.getText().chars().allMatch(Character::isLetter))
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("area name must contains only characters!!!");
			a.showAndWait();
		}
		
		else {
			
			String name=areaName.getText();
			HashSet<Neighberhood> h1=new HashSet<Neighberhood>(h);
			HashSet<Neighberhood> ree=new HashSet<Neighberhood>(toRe);
			
			HashSet<Delivery> deliveryadd=new HashSet<Delivery>(deliveryy);
			HashSet<Delivery> deliveryRemove=new HashSet<Delivery>(toReDeliveryy);
			
			HashSet<DeliveryPerson> addpersooon=new HashSet<DeliveryPerson>(persoon);
			HashSet<DeliveryPerson> removepersooon=new HashSet<DeliveryPerson>(toRePersoon);
			
			DeliveryArea d=compoo.getValue();
			boolean x=true;
			if(d!=null)
			{
				
				
				for(Neighberhood n:h1)
				{
					if(!d.addNeighberhood(n))
					{
						x=false;
					}
				}
				for(Neighberhood r:ree)
				{
					if(!d.removeNeighberhood(r))
					{
						x=false;
					}
				}
				for(Delivery d3:deliveryadd)
				{
					    if(d3.getArea()!=null)
					    {
					    	d3.getArea().removeDelivery(d3);
					    }
					    d3.setArea(d);
					
						if(!d.addDelivery(d3))
						{
							x=false;
						}
					
				}
				for(Delivery del:deliveryRemove)
				{
					   
						if(!d.removeDelivery(del))
						{
							x=false;
						}
						else
						{
							 del.setArea(null);
						}
					
				}
				
				for(DeliveryPerson p:addpersooon)
				{
					if(p.getArea()!=null)
					{
						p.getArea().removeDelPerson(p);
					}
					    p.setArea(d);
					
						if(!d.addDelPerson(p))
						{
							x=false;
						}
					
				}
				for(DeliveryPerson pr:removepersooon)
				{
					    
						if(!d.removeDelPerson(pr))
						{
							x=false;
						}
						else
						{
							pr.setArea(null);
						}
					
				}
				if(x==true) {
					
					d.setAreaName(name);
					Main.update();
					clear1();
					Alert a=new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("Successfully updated Delivery area!!!");
					a.showAndWait();
					setNewData();
					}
				else
				{
					Alert a=new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("failed to update!!!");
					a.showAndWait();
					clear1();
				}
					
				h.removeAll(h1);
				toRe.removeAll(ree);
				persoon.removeAll(addpersooon);
				toRePersoon.removeAll(removepersooon);
				deliveryy.removeAll(deliveryadd);
				toReDeliveryy.removeAll(deliveryRemove);
				
				
			}
			else
			{
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("you must choose a Delivery Area!!!");
				a.showAndWait();
				clear1();
			}
			
	        }
		}
		
		
	catch(EmptyFieldsException e3)
	{
		Alert a=new Alert(AlertType.CONFIRMATION);
		a.setHeaderText(e3.getMessage());
		a.showAndWait();
	}
	
	}
	
	//clear all the fields.
	public void clear1()
	{
		areaName.setText("");
		neighberhoods.getSelectionModel().clearSelection();
		addDeli.getSelectionModel().clearSelection();
		removeDeli.getSelectionModel().clearSelection();
		addPerson.getSelectionModel().clearSelection();
		removePerson.getSelectionModel().clearSelection();
		removee.getSelectionModel().clearSelection();
		removeDeli.setItems(null);
		removePerson.setItems(null);
		removee.setItems(null);
		nameError.setText("");
		areaName.setStyle("-fx-border-color:transparent;");
		compoo.setValue(null);
		
		
		
	}
	
	
	/*the key pressed method helped me to make the application easier,it shows to the 
	 * user if the inputs are valid
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(e.getSource()==areaName)
		{
			if(!areaName.getText().chars().allMatch(Character::isLetter))
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
		
		
	}

}

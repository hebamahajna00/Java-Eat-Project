package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import Exceptions.HasNoAreaException;
import application.Main;
import application.model.DeliveryPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class RemoveDeliPersonController implements Initializable{
	
	
	
	@FXML
	ComboBox<DeliveryPerson> compo;
	@FXML
	Button remove;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		compo.setVisibleRowCount(5);
		setNewData();
		
	}
	/*
	 * remove person.
	 */
	@FXML
	public void remoove(ActionEvent e)
	{
		try {
		if(compo.getSelectionModel().getSelectedItem()==null)
		{
			throw new EmptyFieldsException();
		}
		DeliveryPerson c=compo.getSelectionModel().getSelectedItem();
		try {
		if(!Main.res.removeDeliveryPerson(c))
		{
			
			compo.getSelectionModel().clearSelection();
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Failed to remove Delivery Person");
			a.showAndWait();
		}
		else
		{
			compo.getSelectionModel().clearSelection();
	        setNewData();
			Main.update();;
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Successfully removed Delivery Person");
			a.showAndWait();
		}
		}
		catch(HasNoAreaException eee)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText(eee.getMessage());
			a.showAndWait();
		}
		}
		catch(EmptyFieldsException e3)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText(e3.getMessage());
			a.showAndWait();
		}
	}
	
	//set combo box data.
	public void setNewData()
	{
		ArrayList<DeliveryPerson> a=new ArrayList<DeliveryPerson>(Main.res.getDeliveryPersons().values());
		ObservableList<DeliveryPerson> list=FXCollections.observableArrayList(a);
		compo.setItems(list);
	}





}

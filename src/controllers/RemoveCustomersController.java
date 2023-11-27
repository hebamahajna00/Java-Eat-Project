package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class RemoveCustomersController  implements Initializable{
	
	@FXML
	ComboBox<Customer> compo;
	@FXML
	Button remove;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		compo.setVisibleRowCount(5);
		setNewData();
		
	}
	
	/*
	 * remove the customer.
	 */
	@FXML
	public void remoove(ActionEvent e)
	{
		try {
		if(compo.getSelectionModel().getSelectedItem()==null)
		{
			throw new EmptyFieldsException();
		}
		Customer c=compo.getSelectionModel().getSelectedItem();
		if(!Main.res.removeCustomer(c))
		{
			
			compo.getSelectionModel().clearSelection();
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Failed to remove Customer");
			a.showAndWait();
		}
		else
		{
			compo.getSelectionModel().clearSelection();
	        setNewData();
			Main.update();;
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Successfully removed Customer");
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
		ArrayList<Customer> a=new ArrayList<Customer>(Main.res.getCustomers().values());
		ObservableList<Customer> list=FXCollections.observableArrayList(a);
		compo.setItems(list);
	}




}

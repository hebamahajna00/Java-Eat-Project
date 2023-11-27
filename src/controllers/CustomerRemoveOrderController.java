package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Customer;
import application.model.HelpingClass;
import application.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class CustomerRemoveOrderController implements Initializable{
	
	
	@FXML
	ComboBox<Order> compo;
	@FXML
	Button remove;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	
		setNewData();
		
	}
	
	/*
	 * this method remove order only if it has a delievry otherwise the method remove
	 * in the class restaurant will throw exception so i first we must check if the delivery
	 * not null.
	 */
	@FXML
	public void remoove(ActionEvent e)
	{
		try {
			Customer cc=HelpingClass.getInstance().getUser();
		if(compo.getSelectionModel().getSelectedItem()==null)
		{
			throw new EmptyFieldsException();
		}
		Order c=compo.getSelectionModel().getSelectedItem();
		if(c.getDelivery()==null)
		{
			compo.getSelectionModel().clearSelection();
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Failed to remove Order! delivery is null!");
			a.showAndWait();
			
		}
		else {
			if(!Main.res.getOrderByCustomer().get(cc).remove(c))
			{
				compo.getSelectionModel().clearSelection();
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Failed to remove Order");
				a.showAndWait();
				setNewData();
			}
		else {
		if(!Main.res.removeOrder(c))
		{
			compo.getSelectionModel().clearSelection();
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Failed to remove Order ! it doesnt exist");
			a.showAndWait();
		}
		else
		{
			compo.getSelectionModel().clearSelection();
	        setNewData();
			Main.update();;
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Successfully removed Order");
			a.showAndWait();
			
		}
		}
		}
		Main.update();
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
		Customer c=HelpingClass.getInstance().getUser();
		ArrayList<Order> a=new ArrayList<Order>(Main.res.getOrderByCustomer().get(c));
		ObservableList<Order> list=FXCollections.observableArrayList(a);
		compo.setItems(list);
	}




}

package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

public class AddToBlackController implements Initializable{
	
	@FXML
	ComboBox<Customer> compoo;
	@FXML
	Button add;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNewData();
		
		
	}
	
	//set the compo box data.
	public void setNewData()
	{
		ArrayList<Customer> a=new ArrayList<Customer>(Main.res.getCustomers().values());
		ObservableList<Customer> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}
	
	/*
	 * the method add a customer to the balackList,that means he cant make an order.
	 */
	@FXML
	public void add(ActionEvent e)
	{
		
		Customer c=compoo.getValue();
		if(c==null)
		{
			Alert a=new Alert(AlertType.INFORMATION);
			a.setHeaderText("choose customer please!");
			a.showAndWait();
		}
		else
		{
			if(!Main.res.addCustomerToBlackList(c))
			{
				Alert a=new Alert(AlertType.INFORMATION);
				a.setHeaderText("Failed To Add customer!");
				a.showAndWait();
			}
			else
			{
				Main.update();
				Alert a=new Alert(AlertType.INFORMATION);
				a.setHeaderText("successfully added customer to blacklist!");
				a.showAndWait();
			}
		}
	}
	

}

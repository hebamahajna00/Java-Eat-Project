package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Exceptions.EmptyFieldsException;
import Exceptions.HasNoAreaException;
import application.Main;
import application.model.Delivery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class RemoveDeliveryController implements Initializable{
	
	@FXML
	ComboBox<Delivery> compo;
	@FXML
	Button remove;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		compo.setVisibleRowCount(5);
		setNewData();
		
	}
	
	/*
	 * remove delivery.
	 */
	@FXML
	public void remoove(ActionEvent e)
	{
		try {
		if(compo.getSelectionModel().getSelectedItem()==null)
		{
			throw new EmptyFieldsException();
		}
		Delivery c=compo.getSelectionModel().getSelectedItem();
		try {
		if(!Main.res.removeDelivery(c))
		{
			
			compo.getSelectionModel().clearSelection();
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Failed to remove Delivery");
			a.showAndWait();
		}
		else
		{
			compo.getSelectionModel().clearSelection();
	        setNewData();
			Main.update();;
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Successfully removed Delivery");
			a.showAndWait();
		}
		}
		catch(HasNoAreaException excep)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText(excep.getMessage());
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
		ArrayList<Delivery> a=new ArrayList<Delivery>(Main.res.getDeliveries().values());
		ObservableList<Delivery> list=FXCollections.observableArrayList(a);
		compo.setItems(list);
	}


}

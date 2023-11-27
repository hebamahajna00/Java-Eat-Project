package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.DeliveryArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class RemoveAreaController implements Initializable {
	
	
	
	@FXML
	ComboBox<DeliveryArea> compo;
	@FXML
	ComboBox<DeliveryArea> compo1;
	@FXML
	Button remove;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		compo.setVisibleRowCount(5);
		setNewData();
		
	}
	
	/*
	 * remove the delivery area after choosing old area and new area.
	 */
	@FXML
	public void remoove(ActionEvent e)
	{
		try {
		if(compo.getSelectionModel().getSelectedItem()==null||compo1.getSelectionModel().getSelectedItem()==null)
		{
			throw new EmptyFieldsException();
		}
		DeliveryArea c=compo.getSelectionModel().getSelectedItem();
		DeliveryArea c2=compo1.getSelectionModel().getSelectedItem();
		
		if(!Main.res.removeDeliveryArea(c, c2))
		{
			
			compo.getSelectionModel().clearSelection();
			compo1.getSelectionModel().clearSelection();
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Failed to remove Area");
			a.showAndWait();
		}
		else
		{
			compo.getSelectionModel().clearSelection();
			compo1.getSelectionModel().clearSelection();
	        setNewData();
			Main.update();;
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Successfully removed Area");
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
	
	
	//set the combo boxes data.
	public void setNewData()
	{
		ArrayList<DeliveryArea> a=new ArrayList<DeliveryArea>(Main.res.getAreas().values());
		ObservableList<DeliveryArea> list=FXCollections.observableArrayList(a);
		compo.setItems(list);
		compo1.setItems(list);
	}




}

package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Cook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class RemoveCookController implements Initializable {
	
	
	@FXML
	ComboBox<Cook> compo;
	@FXML
	Button remove;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		compo.setVisibleRowCount(5);
		setNewData();
		
	}
	
	/*
	 * remove the cook.
	 */
	@FXML
	public void remoove(ActionEvent e)
	{
		try {
		if(compo.getSelectionModel().getSelectedItem()==null)
		{
			throw new EmptyFieldsException();
		}
		Cook c=compo.getSelectionModel().getSelectedItem();
		if(!Main.res.removeCook(c))
		{
			
			compo.getSelectionModel().clearSelection();
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Failed to remove Cook");
			a.showAndWait();
		}
		else
		{
			compo.getSelectionModel().clearSelection();
	        setNewData();
			Main.update();;
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Successfully removed Cook");
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
		ArrayList<Cook> a=new ArrayList<Cook>(Main.res.getCooks().values());
		ObservableList<Cook> list=FXCollections.observableArrayList(a);
		compo.setItems(list);
	}




}

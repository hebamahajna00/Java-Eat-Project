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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class RemoveBlackController implements Initializable {
	
	

	@FXML
	ComboBox<Customer> compoo;
	@FXML
	Button remove;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		compoo.setVisibleRowCount(5);
		setNewData();
		
		
	}
	
	//set combo box data.
	public void setNewData()
	{
		ArrayList<Customer> a=new ArrayList<Customer>(Main.res.getBlackList());
		ObservableList<Customer> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}
	
	
	//remove customer from the blackList.
	@FXML
	public void removee(ActionEvent e)
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
			if(!Main.res.getBlackList().remove(c))
			{
				Alert a=new Alert(AlertType.INFORMATION);
				a.setHeaderText("Failed To remove customer!");
				a.showAndWait();
			}
			else
			{
				Main.update();
				Alert a=new Alert(AlertType.INFORMATION);
				a.setHeaderText("successfully removed customer to blacklist!");
				a.showAndWait();
			}
		}
	}

}

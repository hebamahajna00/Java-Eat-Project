package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Customer;
import application.model.Dish;
import application.model.HelpingClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;

public class CustomeraRemoveDishesController implements Initializable{
	

	@FXML
	ListView<Dish> compo;
	@FXML
	Button remove;
	
	private ArrayList<Dish> toRe;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		toRe=new ArrayList<Dish>();
		setNewData();
		compo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		compo.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(compo.getSelectionModel().getSelectedItem()!=null) {
	       toRe.add(compo.getSelectionModel().getSelectedItem());}
		});
		
	}
	
	//check if we choose dishes to remove,then remove them shopping cart.
	public void remoove(ActionEvent e)
	{
		try {
		if(compo.getSelectionModel().getSelectedItems().size()==0)
		{
			throw new EmptyFieldsException();
		}
		
		ArrayList<Dish> arr=new ArrayList<Dish>(toRe);
		Customer cc=HelpingClass.getInstance().getUser();
		boolean x=true;
		for(Dish d:arr)
		{
			if(!cc.removeOrderFromSal(d))
			{
				x=false;
				Alert aa=new Alert(AlertType.CONFIRMATION);
				aa.setHeaderText("Failed To remove!");
				aa.showAndWait();
				break;
			}
		}
		if(x==true)
		{
			Alert aa=new Alert(AlertType.CONFIRMATION);
			aa.setHeaderText("Successfully removes the dishes!");
			aa.showAndWait();
		}
		Main.update();
		toRe.removeAll(arr);
		compo.getSelectionModel().clearSelection();
		setNewData();
		}
		catch(EmptyFieldsException e3)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText(e3.getMessage());
			a.showAndWait();
		}
	}
	
	
	//set the combo box items.
	public void setNewData()
	{
		Customer c=HelpingClass.getInstance().getUser();
		
		ArrayList<Dish> a=new ArrayList<Dish>(c.getSalKneyot());
		ObservableList<Dish> list=FXCollections.observableArrayList(a);
		compo.setItems(list);
	}


}

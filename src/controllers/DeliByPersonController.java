package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Delivery;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
import application.model.ExpressDelivery;
import application.model.Order;
import application.model.RegularDelivery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class DeliByPersonController implements Initializable{
	
	
	
	@FXML 
	TableView<RegularDelivery> tablee1;
	@FXML
	TableColumn<RegularDelivery,DeliveryPerson> reperson;
	@FXML
	TableColumn<RegularDelivery,DeliveryArea> area ;
	@FXML
	TableColumn<RegularDelivery,Boolean>delivered;
	@FXML
	TableColumn<RegularDelivery,LocalDate> date;
	@FXML
	TableColumn<RegularDelivery,TreeSet<Order>> orders;
	@FXML
	TableColumn<RegularDelivery,Integer> id1;
	
	
	
	@FXML 
	TableView<ExpressDelivery> tablee;
	@FXML
	TableColumn<ExpressDelivery,DeliveryPerson> person1;
	@FXML
	TableColumn<ExpressDelivery,DeliveryArea> area1 ;
	@FXML
	TableColumn<ExpressDelivery,Boolean>delivered1;
	@FXML
	TableColumn<ExpressDelivery,LocalDate> date1;
	@FXML
	TableColumn<ExpressDelivery,Order> order1;
	@FXML
	TableColumn<ExpressDelivery,Integer> idExp;
	@FXML
	TableColumn<ExpressDelivery,Integer> postage;
	
	@FXML
	ComboBox<DeliveryPerson> deliiperson;
	@FXML
	ComboBox<Integer> month;
	@FXML
	Button show;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		deliiperson.setVisibleRowCount(5);
		month.setVisibleRowCount(5);
		//regular
		id1.setCellValueFactory(new PropertyValueFactory<>("id"));
		reperson.setCellValueFactory(new PropertyValueFactory<>("deliveryPerson"));
		area.setCellValueFactory(new PropertyValueFactory<>("area"));
		delivered.setCellValueFactory(new PropertyValueFactory<>("isDelivered"));
		orders.setCellValueFactory(new PropertyValueFactory<>("orders"));
		date.setCellValueFactory(new PropertyValueFactory<>("deliveredDate"));
		
		//express
		
		idExp.setCellValueFactory(new PropertyValueFactory<>("id"));
		person1.setCellValueFactory(new PropertyValueFactory<>("deliveryPerson"));
		area1.setCellValueFactory(new PropertyValueFactory<>("area"));
		delivered1.setCellValueFactory(new PropertyValueFactory<>("isDelivered"));
		order1.setCellValueFactory(new PropertyValueFactory<>("order"));
		date1.setCellValueFactory(new PropertyValueFactory<>("deliveredDate"));
		postage.setCellValueFactory(new PropertyValueFactory<>("postage"));
		
		ArrayList<DeliveryPerson> a=new ArrayList<DeliveryPerson>(Main.res.getDeliveryPersons().values());
	    ObservableList<DeliveryPerson> list=FXCollections.observableArrayList(a);
		deliiperson.setItems(list);
		deliiperson.setVisibleRowCount(5);
		ArrayList<Integer> num=new ArrayList<Integer>();
		for(int i=1;i<=12;i++)
		{
			num.add(i);
		}
		ObservableList<Integer> list2=FXCollections.observableArrayList(num);
		month.setItems(list2);
		month.setVisibleRowCount(5);
		
		
		
	
		
	}
	
	/*
	 * the method set the tables items depending on the inputs(month and person)
	 */
	@FXML
	public void show1(ActionEvent e)
	{
		
		try {
			if(month.getValue()==null||deliiperson.getValue()==null)
			{
				throw new EmptyFieldsException();
			}
			TreeSet<Delivery> arr= new TreeSet<Delivery>(Main.res.getDeliveriesByPerson(deliiperson.getValue(),month.getValue()));
			ArrayList<ExpressDelivery> arrr=new ArrayList<ExpressDelivery>();
			ArrayList<RegularDelivery> arrr2=new ArrayList<RegularDelivery>();
			for(Delivery d:arr)
			{
				if(d instanceof RegularDelivery)
				{
					arrr2.add((RegularDelivery)d);
				}
				else
				{
					arrr.add((ExpressDelivery)d);
				}
				
			}
			ObservableList<ExpressDelivery> list=FXCollections.observableArrayList(arrr);
			ObservableList<RegularDelivery> list2=FXCollections.observableArrayList(arrr2);
			tablee.setItems(list);
			tablee1.setItems(list2);
			
			
			}
			catch (EmptyFieldsException e1)
			{
				Alert a=new Alert(AlertType.INFORMATION);
				a.setHeaderText(e1.getMessage());
				a.showAndWait();
			}
	}

}

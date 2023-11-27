package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;

import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Delivery;
import application.model.DeliveryArea;
import application.model.DeliveryPerson;
import application.model.Order;
import application.model.RegularDelivery;
import application.model.ExpressDelivery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class AddDeliveryController implements Initializable{
	
	@FXML
	Button addExpress;
	@FXML 
	ComboBox<DeliveryPerson> deliPersons;
	@FXML
	ComboBox<DeliveryArea> deliAreas;
	@FXML
	CheckBox yesDeli;
	@FXML
	CheckBox noDeli;
	@FXML
	DatePicker  deliDate;
	@FXML
	Button addRegular;
	@FXML
	ListView<Order> regularorders;
	@FXML
	ListView<Order> expressorder;
	@FXML 
	TextField postage;
	@FXML
	Label pError;
	private  TreeSet<Order> arr; 
	
	/*
	 * set all the items and add the listener to data structures in order
	 * to save the selected items
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		arr=new TreeSet<Order>();
		regularorders.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ArrayList<DeliveryPerson> a=new ArrayList<DeliveryPerson>(Main.res.getDeliveryPersons().values());
	    ObservableList<DeliveryPerson> list=FXCollections.observableArrayList(a);
		deliPersons.setItems(list);
		ArrayList<DeliveryArea> ar=new ArrayList<DeliveryArea>(Main.res.getAreas().values());
		ObservableList<DeliveryArea> list2=FXCollections.observableArrayList(ar);
		deliAreas.setItems(list2);
		deliPersons.setVisibleRowCount(5);
		deliAreas.setVisibleRowCount(5);
		ArrayList<Order> arrr=new ArrayList<Order>(Main.res.getOrders().values());
		ObservableList<Order> li=FXCollections.observableArrayList(arrr);
		expressorder.setItems(li);
		regularorders.setItems(li);
		
		
		regularorders.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(regularorders.getSelectionModel().getSelectedItem()!=null) {
	       arr.add(regularorders.getSelectionModel().getSelectedItem());}
		});
		
	
		
	}
	
	
	/*
	 * this method add a delivery to the res,i gave the user the option to choose
	 * which kind of delivery he wants(express or regular),and i checked all the fields,
	 * if one field is empty ,the method will throw exception.
	 * 
	 * the user could also add orders which already have delivery,so for each order that have
	 * a delivery i removed it from the it's delivery and added it to the new one.
	 * * the id is the max id that exist plus 1.
	 */
	@FXML
	public void add(ActionEvent e)
	{
		try {
			
			if(!yesDeli.isSelected()&&!noDeli.isSelected())
			{
				throw new EmptyFieldsException();
			}
			
		if(deliDate.getValue()==null||deliPersons.getValue()==null||deliAreas.getValue()==null)
		{
			throw new EmptyFieldsException();
		}
		if(e.getSource()==addRegular)
		{
			if(regularorders.getSelectionModel().getSelectedItems().size()==0)
			{
				throw new EmptyFieldsException();
			}
			TreeSet<Order> trr=new TreeSet<Order>(arr);
			boolean isD=false;
			if(yesDeli.isSelected())
			{
				isD=true;
			}
			for(Order o:arr)
			{
				if(o.getDelivery()!=null)
				{
					if(o.getDelivery() instanceof RegularDelivery)
					{
						((RegularDelivery)(o.getDelivery())).removeOrder(o);
					}
					else
					{
						((ExpressDelivery)(o.getDelivery())).setOrder(null);
					}
				}
			}
			Delivery d=new RegularDelivery(trr,deliPersons.getValue(),deliAreas.getValue(),isD,deliDate.getValue());
			
			int max=-1;
			for(Integer x:Main.res.getDeliveries().keySet())
			{
				if(x.intValue()>max)
				{
					max=x.intValue();
				}
			}
			d.setId(max+1);
			if(Main.res.addDelivery(d))
			{
				Main.update();
				arr.removeAll(trr);
				clear1();
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("successfully added delivery");
				a.showAndWait();
			}
			else
			{
				clear1();
				arr.removeAll(trr);
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("failed to add delivery");
				a.showAndWait();
			}
			
		}
		else
		{
			if(e.getSource()==addExpress)
			{
				 
				if(expressorder.getSelectionModel().getSelectedItem()==null)
				{
					throw new EmptyFieldsException();
				}
				double p=0.0;
				if(postage.getText().equals(""))
				{
					p=5.0;
				}
				else
				{
					p=Double.parseDouble(postage.getText());
				}
				boolean is1=false;
				if(yesDeli.isSelected())
				{
					is1=true;
				}
				Order o1=expressorder.getSelectionModel().getSelectedItem();
				if(o1.getDelivery()!=null)
				{
					if(o1.getDelivery() instanceof RegularDelivery)
					{
						((RegularDelivery)(o1.getDelivery())).removeOrder(o1);
					}
					else
					{
						((ExpressDelivery)(o1.getDelivery())).setOrder(null);
					}
				}
				Delivery express =new ExpressDelivery(deliPersons.getValue(),deliAreas.getValue(),is1,o1,p,deliDate.getValue());
				int max=-1;
				for(Integer x:Main.res.getDeliveries().keySet())
				{
					if(x>max)
					{
						max=x;
					}
				}
				express.setId(max+1);
				if(Main.res.addDelivery(express))
				{
					Main.update();
					clear2();
					Alert a=new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("successfully added express delivery");
					a.showAndWait();
					
				}
				else
				{
					clear2();
					Alert a=new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("failed to add Express delivery");
					a.showAndWait();
				}
			}
		}
		}
		catch(EmptyFieldsException e3)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText(e3.getMessage());
			a.showAndWait();
		}
		catch(NumberFormatException e3)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("postage must be a number!!!");
			a.showAndWait();
			
		}
		
			
		
		
		
	}
    
	//this method shows the user if he added invalid inputs.
	@FXML
	public void keyPressed(KeyEvent e)
	{
		
		if(e.getSource()==postage)
			try {
			  Double.parseDouble(postage.getText());
			  pError.setText("");
			  postage.setStyle("-fx-border-color:green;");
			  
			
			}
		   catch(NumberFormatException e3)
		    {
			    pError.setText("*Invalid Number!");
			    postage.setStyle("-fx-border-color:red;");
		    }
		
		
	}
	
	// clear the regular delivery fields.
	private void clear1()
	{
		deliPersons.getSelectionModel().clearSelection();
		deliAreas.getSelectionModel().clearSelection();
		regularorders.getSelectionModel().clearSelection();
		deliDate.setValue(null);
		
	}
	//clear the express delivery fields.
	private void clear2()
	{
		deliPersons.getSelectionModel().clearSelection();
		deliAreas.getSelectionModel().clearSelection();
		expressorder.getSelectionModel().clearSelection();
		postage.setText("");
		pError.setText("");
		deliDate.setValue(null);
		postage.setStyle("-fx-border-color:transparent;");
	}
	
	@FXML
	public void checkers(ActionEvent e) throws IOException {
		

		if(e.getSource()==yesDeli)
		{
			noDeli.setSelected(false);
		}
		if(e.getSource()==noDeli)
		{
			yesDeli.setSelected(false);
		}
		
		
		
	}







	
	
	
	
	
	

}

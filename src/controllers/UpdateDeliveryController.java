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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class UpdateDeliveryController implements Initializable {
	
	@FXML
	Button updateExpress;
	@FXML 
	ComboBox<DeliveryPerson> deliPersons;
	@FXML 
	ComboBox<Delivery> compoo;
	@FXML
	ComboBox<DeliveryArea> deliAreas;
	@FXML
	CheckBox yesDeli;
	@FXML
	CheckBox noDeli;
	@FXML
	DatePicker  deliDate;
	@FXML
	Button updateRe;
	@FXML
	ListView<Order> regularorders;
	@FXML
	ListView<Order> removeOrders;
	@FXML
	ListView<Order> expressorder;
	@FXML 
	TextField postage;
	@FXML
	Label pError;
	private  TreeSet<Order> arr; 
	private  TreeSet<Order> arrRe; 
	
	
	/*
	 * set all the items and add the listener to data structures in order
	 * to save the selected items
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNewData();
		deliPersons.setVisibleRowCount(5);
		compoo.setVisibleRowCount(5);
		deliAreas.setVisibleRowCount(5);
		arr=new TreeSet<Order>();
		arrRe=new TreeSet<Order>();
		regularorders.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		removeOrders.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ArrayList<DeliveryPerson> a=new ArrayList<DeliveryPerson>(Main.res.getDeliveryPersons().values());
	    ObservableList<DeliveryPerson> list=FXCollections.observableArrayList(a);
		deliPersons.setItems(list);
		ArrayList<DeliveryArea> ar=new ArrayList<DeliveryArea>(Main.res.getAreas().values());
		ObservableList<DeliveryArea> list2=FXCollections.observableArrayList(ar);
		deliAreas.setItems(list2);
		
		ArrayList<Order> arrr=new ArrayList<Order>(Main.res.getOrders().values());
		ObservableList<Order> li=FXCollections.observableArrayList(arrr);
		expressorder.setItems(li);
		regularorders.setItems(li);
		regularorders.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(regularorders.getSelectionModel().getSelectedItem()!=null) {
	       arr.add(regularorders.getSelectionModel().getSelectedItem());}
		});
		removeOrders.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(removeOrders.getSelectionModel().getSelectedItem()!=null) {
	       arrRe.add(removeOrders.getSelectionModel().getSelectedItem());}
		});
		
	
		
	}
	/*
	 * update the delivery after making sure that all the fields are valid.
	 * the method remove the chosen orders and add the chosen orders.
	 * for the orders that we want to remove, i set the delivery to null(if express) and if
	 * the delivery is regular so i deleted the order from it.
	 * for each order that we want to add i set the delivery to the current delivery.
	 */
	@FXML
	public void updatee(ActionEvent e)
	{
		try {
			boolean x=true;
			if(!yesDeli.isSelected()&&!noDeli.isSelected())
			{
				throw new EmptyFieldsException();
			}
			if(deliDate.getValue()==null||deliPersons.getValue()==null||deliAreas.getValue()==null)
			{
				throw new EmptyFieldsException();
			}
		    Delivery d=compoo.getValue();
		    if(d!=null)
		    {
		    	if(d instanceof RegularDelivery) {
		    		
		    		TreeSet<Order> trr=new TreeSet<Order>(arr);
		    		TreeSet<Order> trrRe=new TreeSet<Order>(arrRe);
		    		boolean isD=false;
					if(yesDeli.isSelected())
					{
						isD=true;
					}
					RegularDelivery ddd=(RegularDelivery)d;
					for(Order cc:trr)
			    	{
			    		if(x==true&&!ddd.addOrder(cc))
			    		{
			    			x=false;
			    			Alert a=new Alert(AlertType.CONFIRMATION);
			    			a.setHeaderText("failed to update!");
			    			a.showAndWait();
			    			clear1();
			    			arr.removeAll(trr);
			    			arrRe.removeAll(trrRe);
			    		}
			    	}
					for(Order aw:trrRe)
			    	{
			    		if(x==true&&!ddd.removeOrder(aw))
			    		{
			    			x=false;
			    			Alert a=new Alert(AlertType.CONFIRMATION);
			    			a.setHeaderText("failed to update");
			    			a.showAndWait();
			    			clear1();
			    			arr.removeAll(trr);
			    			arrRe.removeAll(trrRe);
			    		}
			    	}
					
					if(x==true) {
						for(Order aw:trr)
				    	{
							if(aw.getDelivery()!=null) {
							if(aw.getDelivery() instanceof RegularDelivery)
							{
								RegularDelivery t=(RegularDelivery)(aw.getDelivery());
								t.removeOrder(aw);
							}
							else
							{
								ExpressDelivery ex=(ExpressDelivery)(aw.getDelivery());
								ex.setOrder(null);
								
							}
							}
							aw.setDelivery(ddd);
				    	}
						for(Order ordeer:trrRe)
				    	{
							ordeer.setDelivery(null);
				    	
				    	}
						DeliveryArea aah=ddd.getArea();
						if(aah!=null) {aah.removeDelivery(ddd);}
						ddd.setArea(deliAreas.getValue());
						deliAreas.getValue().addDelivery(ddd);
						ddd.setDeliveryPerson(deliPersons.getValue());
						ddd.setDeliveredDate(deliDate.getValue());
						ddd.setDelivered(isD);
				    	Main.update();
				    	clear1();
				    	arr.removeAll(trr);
		    			arrRe.removeAll(trrRe);
				    	Alert a=new Alert(AlertType.CONFIRMATION);
		    			a.setHeaderText("Successfully updated Delivery!");
		    			a.showAndWait();
		    			setNewData();
				    	}
		    		
		    	}
		    	else
		    	{
		    		ExpressDelivery exp=(ExpressDelivery)(d);
		    		Double p=5.0;
		    		if(postage.getText().equals(""))
					{
						p=5.0;
					}
					else
					{
						p=Double.parseDouble(postage.getText());
					}
		    		Order oo=expressorder.getSelectionModel().getSelectedItem();
		    		if(oo!=null)
		    		{
		    			Delivery r=oo.getDelivery();
		    			if(r!=null) {
		    			if(r instanceof RegularDelivery) {
		    				RegularDelivery g=(RegularDelivery)(r);
		    				g.removeOrder(oo);
		    			}
		    			else
		    			{
		    				ExpressDelivery v=(ExpressDelivery)(r);
		    				v.setOrder(null);
		    			}
		    			}
		    			oo.setDelivery(exp);
		    			exp.setOrder(oo);
		    		}
		    		boolean isDeli=false;
					if(yesDeli.isSelected())
					{
						isDeli=true;
					}
					DeliveryArea aa=exp.getArea();
					if(aa!=null) {aa.removeDelivery(exp);}
		    		exp.setArea(deliAreas.getValue());
		    		deliAreas.getValue().addDelivery(exp);
					exp.setDeliveryPerson(deliPersons.getValue());
					exp.setDeliveredDate(deliDate.getValue());
					exp.setDelivered(isDeli);
		    		exp.setPostage(p);
		    		Main.update();
		    		Alert a=new Alert(AlertType.CONFIRMATION);
	    			a.setHeaderText("Successfully updated Delivery!");
	    			a.showAndWait();
	    			setNewData();
	    			clear2();
		    	}
		    	
		    	
		    }
		    else
		    {
		    	Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("you must choose a Delivery!!!");
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
	
	
	
	//show the given delivery information.
		@FXML
	public void showInformation(ActionEvent e)
	{
		Delivery c=compoo.getSelectionModel().getSelectedItem();
		if(c!=null) {
		deliDate.setValue(c.getDeliveredDate());
		deliPersons.setValue(c.getDeliveryPerson());
		deliAreas.setValue(c.getArea());
		if(c.getIsDelivered()) {
			yesDeli.setSelected(true);
			noDeli.setSelected(false);
		}
		else
		{
			yesDeli.setSelected(false);
			noDeli.setSelected(true);
		}
		if(c instanceof RegularDelivery) {
		  RegularDelivery cc=(RegularDelivery)(c);
			ObservableList<Order> list2=FXCollections.observableArrayList(cc.getOrders());
			removeOrders.setItems(list2);
			postage.setText("");
			pError.setText("");
			
		}
		else
		{
			
			ExpressDelivery x=(ExpressDelivery)(c);
			postage.setText(""+x.getPostage());
			removeOrders.setItems(null);
		}
			
		}
	}
	
		
	//clear express fields.
	private void clear2()
	{
		deliPersons.getSelectionModel().clearSelection();
		deliAreas.getSelectionModel().clearSelection();
		expressorder.getSelectionModel().clearSelection();
		postage.setText("");
		pError.setText("");
		deliDate.setValue(null);
		compoo.getSelectionModel().clearSelection();
		postage.setStyle("-fx-border-color:transparent;");
	}
	//clear regular fields.
	private void clear1()
	{
		deliPersons.getSelectionModel().clearSelection();
		deliAreas.getSelectionModel().clearSelection();
		regularorders.getSelectionModel().clearSelection();
		removeOrders.setItems(null);
		removeOrders.getSelectionModel().clearSelection();;
		deliDate.setValue(null);
		compoo.getSelectionModel().clearSelection();
		
	}
	
	
	//set combo box data.
	public void setNewData()
	{
		ArrayList<Delivery> a=new ArrayList<Delivery>(Main.res.getDeliveries().values());
		ObservableList<Delivery> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}
	
	
	/*the key pressed method helped me to make the application easier,it shows to the 
	 * user if the inputs are valid
	 */
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

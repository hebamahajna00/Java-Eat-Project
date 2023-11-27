package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.Main;
import application.model.Component;
import application.model.Dish;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;

public class CustomerUpdateDishesController implements Initializable{
	
	
	@FXML
	Button update;
	@FXML
	ListView<Component> components;
	@FXML
	ListView<Component> componentsToRe;
	
	@FXML
	ComboBox<Dish> compoo;
	
	// added this data structures in order to save all the selected items
	private ArrayList<Component> arr;
	private ArrayList<Component> arrRe;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		compoo.setVisibleRowCount(5);
		setNewData();
		arr=new ArrayList<Component>();
		arrRe=new ArrayList<Component>();
		components.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		componentsToRe.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ArrayList<Component> a=new ArrayList<Component>(Main.res.getComponenets().values());
		ObservableList<Component> list=FXCollections.observableArrayList(a);
		components.setItems(list);
		components.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(components.getSelectionModel().getSelectedItem()!=null) {
	       arr.add(components.getSelectionModel().getSelectedItem());}
		});
		componentsToRe.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->
		{
			if(componentsToRe.getSelectionModel().getSelectedItem()!=null) {
			
	       arrRe.add(componentsToRe.getSelectionModel().getSelectedItem());}
		});
		
	}
	
	//set combo data.
	public void setNewData()
	{
		ArrayList<Dish> a=new ArrayList<Dish>(Main.res.getDishes().values());
		ObservableList<Dish> list=FXCollections.observableArrayList(a);
		compoo.setItems(list);
	}
	
	
	//show all the given dish's data.
	@FXML
	public void showInformation(ActionEvent e)
	{
		Dish c=compoo.getSelectionModel().getSelectedItem();
		if(c!=null)
		{
			ArrayList<Component> a2=new ArrayList<Component>(c.getComponenets());
			ObservableList<Component> list2=FXCollections.observableArrayList(a2);
			componentsToRe.setItems(list2);
			
		}
	}

	/*
	 * update the dish , by creating a new one,the new dish information(name..) are same as the chosen
	 * dish in the combo box.
	 * i added the array lists as fields to help me know what the user choose.
	 * the id is the max id that exist plus 1.
	 */
	@FXML
	public void updatee(ActionEvent e)
	{
		
			boolean x=true;
		    ArrayList<Component> ar=new ArrayList<Component>(arr);
		    ArrayList<Component> arRemove=new ArrayList<Component>(arrRe);
		    Dish d=compoo.getValue();
		    
		    
		    if(d!=null)
		    {
		    	ArrayList<Component> copy=new ArrayList<Component>(d.getComponenets());
		    	
		    	Dish newDish=new Dish(d.getDishName(),d.getType(),copy,d.getTimeToMake());
		    	int max=-1;
				for(Integer intt:Main.res.getDishes().keySet())
				{
					if(intt.intValue()>max)
					{
						max=intt.intValue();
					}
				}
				newDish.setId(max+1);
		    	for(Component cc:ar)
		    	{
		    		if(x==true&&!newDish.addComponent(cc))
		    		{
		    			x=false;
		    			Alert a=new Alert(AlertType.CONFIRMATION);
		    			a.setHeaderText("failed to update!");
		    			a.showAndWait();
		    			clear1();
		    			arr.removeAll(ar);
		    			arrRe.removeAll(arRemove);
		    		}
		    	}
		    	for(Component aw:arRemove)
		    	{
		    		if(x==true&&!newDish.removeComponent(aw))
		    		{
		    			x=false;
		    			Alert a=new Alert(AlertType.CONFIRMATION);
		    			a.setHeaderText("failed to update");
		    			a.showAndWait();
		    			clear1();
		    			arr.removeAll(ar);
		    			arrRe.removeAll(arRemove);
		    		}
		    	}
		    	if(x==true) {
		    		
		    	if(!Main.res.addDish(newDish))
		    	{
		    		clear1();
		    		arr.removeAll(ar);
	    			arrRe.removeAll(arRemove);
			    	Alert a=new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("Failed to add Dish!");
					a.showAndWait();
			    }
		    	else 
		    	{
		    	Main.update();
		    	clear1();
		    	arr.removeAll(ar);
    			arrRe.removeAll(arRemove);
		    	Alert a=new Alert(AlertType.CONFIRMATION);
    			a.setHeaderText("Successfully updated dish!");
    			a.showAndWait();
    			setNewData();
		    	}
		    	}
		    }
		    else
		    {
		    	Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("you must choose a Dish!!!");
				arr.removeAll(ar);
    			arrRe.removeAll(arRemove);
    			clear1();
				a.showAndWait();
		    }
		   
			
		    
		}
		
	
	//clear the fields.
	public void clear1()
	{
		components.getSelectionModel().clearSelection();
		compoo.getSelectionModel().clearSelection();
		componentsToRe.setItems(null);
		
	}

}

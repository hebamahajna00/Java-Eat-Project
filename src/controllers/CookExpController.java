package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Exceptions.EmptyFieldsException;
import Utils.Expertise;
import Utils.Gender;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class CookExpController implements Initializable {
	
	
	@FXML 
	TableView<Cook> tablee;
	@FXML
	TableColumn<Cook,String> name;
	@FXML
	TableColumn<Cook,String> last;
	@FXML
	TableColumn<Cook,LocalDate> birth;
	@FXML
	TableColumn<Cook,Gender> gender;
	@FXML
	TableColumn<Cook,Expertise> expertisee;
	@FXML
	TableColumn<Cook,Boolean> ischef;
	@FXML
	TableColumn<Cook,Integer> id;
	@FXML
	Button show;
	@FXML
	ComboBox<String> exp;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
	
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		last.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		birth.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		expertisee.setCellValueFactory(new PropertyValueFactory<>("expert"));
		ischef.setCellValueFactory(new PropertyValueFactory<>("isChef"));
	}
	
	
	/*
	 * this method shows the relevent data(cooks by given expertise) in the table.
	 */
	@FXML
	public void show1(ActionEvent e)
	{
		try {
		if(exp.getValue()==null)
		{
			throw new EmptyFieldsException();
		}
		ArrayList<Cook> arr=new ArrayList<Cook>(Main.res.GetCooksByExpertise(Expertise.valueOf(exp.getValue())));
		ObservableList<Cook> list=FXCollections.observableArrayList(arr);
		tablee.setItems(list);
		
		
		}
		catch (EmptyFieldsException e1)
		{
			Alert a=new Alert(AlertType.INFORMATION);
			a.setHeaderText(e1.getMessage());
			a.showAndWait();
		}
		
	}
	

}

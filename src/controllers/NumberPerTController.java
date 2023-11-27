package controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class NumberPerTController implements Initializable{
	
	
	@FXML
	Label expN;

	@FXML
	Label reN;
	
	//update the label texts to the number of each delivery.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		 HashMap<String,Integer> hm=Main.res.getNumberOfDeliveriesPerType();
		 if(hm!=null)
		 {
			 if(hm.containsKey("Express delivery"))
			 {
				 expN.setText(""+hm.get("Express delivery"));
			 }
			 if(hm.containsKey("Regular delivery"))
			 {
				 reN.setText(""+hm.get("Regular delivery"));
			 }
			 
		 }
		
		
	}

}

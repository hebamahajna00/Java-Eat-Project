package controllers;

import java.io.IOException;


import Exceptions.CustomerDoesntExist;
import Exceptions.EmptyFieldsException;
import application.Main;
import application.model.Customer;
import application.model.HelpingClass;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MyHomePageController {
	
	
	@FXML
	PasswordField passw;
	@FXML 
	TextField userName;
	@FXML
	Button signinbttn;
	@FXML
	Button upp;
	@FXML
    AnchorPane root;
	@FXML
	Label userE;
	@FXML
	Label passE;
	@FXML 
	StackPane stackk;
	
	
	
	
	
	
/*
 * this method checks if the username and password are "manager" otherwise,it checks if the given
 * user name exists in the application, the it checks the password.
 */
@FXML
public void save(ActionEvent e) throws IOException {

	try {
		
	if(userName.getText().equals("")||passw.getText().equals(""))
	{
		throw new EmptyFieldsException();
	}
	else 
	{
		if(userName.getText().equals("manager")&&passw.getText().equals("manager"))
		{
            Parent roott =FXMLLoader.load(getClass().getResource("/view/ManagerPage.fxml"));
     		Scene scene=upp.getScene();
     		roott.translateXProperty().set(scene.getHeight());
     	    StackPane container=(StackPane)scene.getRoot();
     	    container.getChildren().add(roott);
     		Timeline time=new Timeline();
     		KeyValue kv= new KeyValue(roott.translateXProperty(),0,Interpolator.EASE_IN);
     		KeyFrame kf=new KeyFrame(Duration.seconds(1),kv);
     		time.getKeyFrames().add(kf);
     		time.setOnFinished(event1->{
     			
     			container.getChildren().remove(root);
     		});
     		time.play();
     		return;
		}
		else
		{
		boolean a=false;
		Customer c1=null;
		for(Customer c:Main.res.getCustomers().values())
		{
			if(c.getUserName().equals(userName.getText()))
			{
				a=true;
				c1=c;
			}
		}
		if(a==false)
		{
			userName.setStyle("-fx-border-color:red;");
			throw new CustomerDoesntExist();
		}
		if(c1!=null)
		{
			if(!c1.getPass().equals(passw.getText()))
			{
				   passE.setText("wrong Pass!");
                   passw.setStyle("-fx-border-color:red;");
				   Alert a3=new Alert(AlertType.CONFIRMATION);
				   a3.setHeaderText("Password is Wrong");
				   a3.showAndWait();
				   
			}
			else
			{
				passE.setText("");
                passw.setStyle("-fx-border-color:green;");
           
                try {
                	HelpingClass he=HelpingClass.getInstance();
                    he.setUser(c1);
                    Parent roott =FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
            		Scene scene=upp.getScene();
            		roott.translateYProperty().set(scene.getHeight());
            	    StackPane container=(StackPane)scene.getRoot();
            	    container.getChildren().add(roott);
            		Timeline time=new Timeline();
            		KeyValue kv= new KeyValue(roott.translateYProperty(),0,Interpolator.EASE_IN);
            		KeyFrame kf=new KeyFrame(Duration.seconds(1),kv);
            		time.getKeyFrames().add(kf);
            		time.setOnFinished(event1->{
            			
            			container.getChildren().remove(root);
            		});
            		time.play();
            		return;
                } catch (IOException ee) {
                  ee.printStackTrace();
                }
			}
		}
		}
		
	
	}		
	
   }
   catch(EmptyFieldsException e1)
   {
	   Alert a=new Alert(AlertType.CONFIRMATION);
	   a.setHeaderText(e1.getMessage());
	   a.showAndWait();
   }
	catch(CustomerDoesntExist e3)
	   {
		   Alert a=new Alert(AlertType.CONFIRMATION);
		   a.setHeaderText(e3.getMessage());
		   a.showAndWait();
	   }
 }


/*the key pressed method helped me to make the application easier,it shows to the 
 * user if the inputs are valid
 */
@FXML
public void keyPressed(KeyEvent e)
{
	
	if(e.getSource()==userName)
	{
		if(!userName.getText().chars().allMatch(Character::isLetterOrDigit))
		{
			userE.setText("invalid input!!");
			userName.setStyle("-fx-border-color:green;");
		}
		else
		{
			userE.setText("");
			userName.setStyle("-fx-border-color:green;");
		}
	}
	if(e.getSource()==passw)
	{
		boolean hasLetter = false;
        boolean hasDigit = false;
        String password=passw.getText();

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                char x = password.charAt(i);
                if (Character.isLetter(x)) {

                    hasLetter = true;
                }

                else if (Character.isDigit(x)) {

                    hasDigit = true;
                }
                if(hasLetter && hasDigit){

                    break;
                }

            }
            if (hasLetter && hasDigit) {
               
                passE.setText("*STRONG");
                passE.setTextFill(Color.GREEN);
                passw.setStyle("-fx-border-color:green;");
                
            } else {
                
                passE.setText("*MEDIUM");
                passE.setTextFill(Color.ORANGE);
                passw.setStyle("-fx-border-color:orange;");
            }
        } else {
        	passE.setText("*NOT STRONG");
        	passE.setTextFill(Color.RED);
        	passw.setStyle("-fx-border-color:red;");
        }
    }
		
	
}

// go to sign up page using animation.
@FXML
public void up(MouseEvent e)
{
	try {
		Parent roott =FXMLLoader.load(getClass().getResource("/view/Register.fxml"));
		Scene scene=upp.getScene();
		roott.translateXProperty().set(scene.getHeight());
	    StackPane container=(StackPane)scene.getRoot();
	    container.getChildren().add(roott);
		Timeline time=new Timeline();
		KeyValue kv= new KeyValue(roott.translateXProperty(),0,Interpolator.EASE_IN);
		KeyFrame kf=new KeyFrame(Duration.seconds(1),kv);
		time.getKeyFrames().add(kf);
		time.setOnFinished(event1->{
			
			container.getChildren().remove(root);
		});
		time.play();
		
		
		
	} catch (IOException e1) {
		
		e1.printStackTrace();
	}
	
}


}

	
	

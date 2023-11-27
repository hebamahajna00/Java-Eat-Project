package controllers;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

import Exceptions.CustomerExist;
import Exceptions.EmptyFieldsException;
import Utils.Gender;
import Utils.Neighberhood;
import application.Main;
import application.model.Customer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class RegisterController implements Initializable{
	
	@FXML 
	TextField custFirst;
	@FXML
	Label userError;
	@FXML 
	TextField custLast;
	@FXML 
	DatePicker custBirth;
	@FXML 
	ChoiceBox<String> CustGender;
	@FXML 
	ComboBox<String> CustNeigh;
	@FXML 
	TextField userN;
	@FXML
	PasswordField pass;
	@FXML 
	Label passE;
	@FXML 
	CheckBox yesgluten;
	@FXML 
	CheckBox nogluten;
	@FXML 
	CheckBox yeslactose;
	@FXML 
	Button addCust;
	@FXML
	Button choosePhoto;
	@FXML 
	ImageView photo;
	@FXML 
	CheckBox nolactose;
	@FXML
	Label nameError;
	@FXML 
	Label lastError;
	@FXML
	Button logg;
	@FXML
	AnchorPane anchoor;
	private byte[] pictureName;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		CustNeigh.setVisibleRowCount(5);
		
		
	}
	
	public void logIn(ActionEvent e)
	{
		
		try {
			Parent roott =FXMLLoader.load(getClass().getResource("/view/MyMainDesign.fxml"));
			Scene scene=logg.getScene();
			roott.translateYProperty().set(scene.getHeight());
		    StackPane container=(StackPane)scene.getRoot();
		    container.getChildren().add(roott);
			Timeline time=new Timeline();
			KeyValue kv= new KeyValue(roott.translateYProperty(),0,Interpolator.EASE_IN);
			KeyFrame kf=new KeyFrame(Duration.seconds(1),kv);
			time.getKeyFrames().add(kf);
			time.setOnFinished(event1->{
				
				container.getChildren().remove(anchoor);
			});
			time.play();
			
			
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}
	
	//this method add a customer to the restaurant after checking if all the fields are valid.
	@FXML
	public void add(ActionEvent e)
	{
		try {
			if(!yesgluten.isSelected()&&!nogluten.isSelected())
			{
				throw new EmptyFieldsException();
			}
			if(!yeslactose.isSelected()&&!nolactose.isSelected())
			{
				throw new EmptyFieldsException();
			}
		if(custFirst.getText().equals("")||custLast.getText().equals("")||custBirth.getValue()==null||CustGender.getValue()==null||CustNeigh.getValue()==null||userN.getText().equals("")||pass.getText().equals(""))
		{
			throw new EmptyFieldsException();
		}
		if(userN.getText().equals("manager")||!passE.getText().equals("*STRONG")||!userN.getText().chars().allMatch(Character::isLetterOrDigit)||!custFirst.getText().chars().allMatch(Character::isLetter)||!custLast.getText().chars().allMatch(Character::isLetter))
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Invalid Inputs!!!,also make sure that user name is not manager");
			a.showAndWait();
		}
		
		else
		{
			for(Customer c:Main.res.getCustomers().values())
			{
				if(c.getUserName().equals(userN.getText()))
				{
					throw new CustomerExist();
				}
			}
			String name1=custFirst.getText();
			String last1=custLast.getText();
			String password=pass.getText();
			String userr=userN.getText();
			boolean lac=false;
			boolean glu=false;
			
			if(yeslactose.isSelected())
			{
				lac=true;
			}
			if(yesgluten.isSelected())
			{
				glu=true;
			}
			Customer c=new Customer(name1,userr,last1,password,pictureName,custBirth.getValue(),Gender.valueOf(CustGender.getValue().toString()),Neighberhood.valueOf(CustNeigh.getValue().toString()),lac,glu);
			int max=-1;
			for(Integer x:Main.res.getCustomers().keySet())
			{
				if(x>max)
				{
					max=x;
				}
			}
			c.setId(max+1);
			if(Main.res.addCustomer(c))
			{
				Main.update();
				clear1();
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Succeffully added you! ,now log in please");
				a.showAndWait();
				try {
					Parent roott =FXMLLoader.load(getClass().getResource("/view/MyMainDesign.fxml"));
					Scene scene=logg.getScene();
					roott.translateYProperty().set(scene.getHeight());
				    StackPane container=(StackPane)scene.getRoot();
				    container.getChildren().add(roott);
					Timeline time=new Timeline();
					KeyValue kv= new KeyValue(roott.translateYProperty(),0,Interpolator.EASE_IN);
					KeyFrame kf=new KeyFrame(Duration.seconds(1),kv);
					time.getKeyFrames().add(kf);
					time.setOnFinished(event1->{
						
						container.getChildren().remove(anchoor);
					});
					time.play();
					
					
					
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
			else
			{
				clear1();
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Failed to add customer!");
				a.showAndWait();
			}
					
				      
					
		}
		}
		catch (EmptyFieldsException e1)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("You must fill all the fields!!!");
			a.showAndWait();
		}
		catch (CustomerExist e5)
		{
			Alert a=new Alert(AlertType.CONFIRMATION);
			a.setHeaderText(e5.getMessage());
			a.showAndWait();
		}
	}
	
	//this method checks that we selected one check box.
	@FXML
	public void checkers(ActionEvent e) throws IOException {
		
		if(e.getSource()==yeslactose)
		{
			nolactose.setSelected(false);
		}
		if(e.getSource()==nolactose)
		{
			yeslactose.setSelected(false);
		}
		if(e.getSource()==yesgluten)
		{
			nogluten.setSelected(false);
		}
		if(e.getSource()==nogluten)
		{
			yesgluten.setSelected(false);
		}
		
		
	}
	
	//clear all the fields.
	public void clear1()
	{
		custFirst.setText("");
		custLast.setText("");
		custBirth.setValue(null);
		CustGender.getSelectionModel().clearSelection();
		CustNeigh.getSelectionModel().clearSelection();
		photo.setImage(new Image("File:Images/noPhoto.png"));
		nameError.setText("");
		lastError.setText("");
		custFirst.setStyle("-fx-border-color:transparent;");
		custLast.setStyle("-fx-border-color:transparent;");
		passE.setText("");
		pass.setText("");
		userN.setText("");
		pass.setStyle("-fx-border-color:transparent;");
	}
	
	
	/*the key pressed method helped me to make the application easier,it shows to the 
	 * user if the inputs are valid
	 */
	@FXML
	public void keyPressed(KeyEvent e)
	{
		if(e.getSource()==custFirst)
		{
			if(!custFirst.getText().chars().allMatch(Character::isLetter))
			{
				nameError.setText("*First name must contains only characters!");
				custFirst.setStyle("-fx-border-color:red;");
			}
			else
			{
				nameError.setText("");
				custFirst.setStyle("-fx-border-color:green;");
			}
		}
		if(e.getSource()==custLast) {
		
			if(custLast.getText().chars().allMatch(Character::isLetter)) {
			  lastError.setText("");
			  custLast.setStyle("-fx-border-color:green;");
			}
			else {
				
			    lastError.setText("*Last name must contains only characters!");
			    custLast.setStyle("-fx-border-color:red;");
			    }
		}
		if(e.getSource()==userN)
		{
			if(!userN.getText().chars().allMatch(Character::isLetterOrDigit))
			{
				userError.setText("invalid input!!");
				userN.setStyle("-fx-border-color:green;");
			}
			else
			{
				userError.setText("");
				userN.setStyle("-fx-border-color:green;");
			}
		}
		if(e.getSource()==pass)
		{
			boolean hasLetter = false;
	        boolean hasDigit = false;
	        String password=pass.getText();

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
	                pass.setStyle("-fx-border-color:green;");
	                
	            } else {
	                
	                passE.setText("*MEDIUM");
	                passE.setTextFill(Color.ORANGE);
	                pass.setStyle("-fx-border-color:orange;");
	            }
	        } else {
	        	passE.setText("*NOT STRONG");
	        	passE.setTextFill(Color.RED);
	        	pass.setStyle("-fx-border-color:red;");
	        }
	    }
			
		
	}
	
	//choose photo.
	public void setOnAction() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        if(file!=null) {
            try {
            	pictureName=Files.readAllBytes(file.toPath());
                Image image=new Image(new ByteArrayInputStream(pictureName));
                photo.setImage(image);
            } catch (IOException ignored) {

            }
        }
    }
	

}

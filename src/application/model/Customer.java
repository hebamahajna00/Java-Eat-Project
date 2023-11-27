package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Utils.Gender;
import Utils.Neighberhood;

public class Customer extends Person implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private Neighberhood neighberhood;
	private boolean isSensitiveToLactose;
	private boolean isSensitiveToGluten;
	private String pass;
	//i added array of byte in order to save the customer picture
	private byte[] photo;
	private String userName;
	private ArrayList<Dish> salKneyot;
	
	
	public Customer(String firstName,String userName, String lastName,String pass,byte[] photo, LocalDate birthDay, Gender gender,
			Neighberhood neighberhood,	boolean isSensitiveToLactose, boolean isSensitiveToGluten) {
		super(idCounter++, firstName, lastName, birthDay, gender);
		this.neighberhood = neighberhood;
		this.isSensitiveToLactose = isSensitiveToLactose;
		this.isSensitiveToGluten = isSensitiveToGluten;
		this.pass=pass;
		this.photo=photo;
		this.userName=userName;
		salKneyot=new ArrayList<Dish>();
	}
	
	
	
	public  List<Dish> getSalKneyot() {
		return Collections.unmodifiableList(salKneyot);
	}

	public boolean addOrderToSal(Dish o)
	{
		if(salKneyot.add(o))
		{
			return true;
		}
		return false;
	}
	public boolean removeOrderFromSal(Dish o)
	{
		if(salKneyot.remove(o))
		{
			return true;
		}
		return false;
	}
	
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Customer(int id) {
		super(id);
	}
	public String getPass()
	{
		return this.pass;
	}
	public void setPass(String pass)
	{
		this.pass=pass;
	}
	
	public byte[] getPhoto()
	{
		return this.photo;
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Customer.idCounter = idCounter;
	}

	public Neighberhood getNeighberhood() {
		return neighberhood;
	}

	public void setNeighberhood(Neighberhood neighberhood) {
		this.neighberhood = neighberhood;
	}

	public boolean getIsSensitiveToLactose() {
		return isSensitiveToLactose;
	}

	public void setSensitiveToLactose(boolean isSensitiveToLactose) {
		this.isSensitiveToLactose = isSensitiveToLactose;
	}

	public boolean getIsSensitiveToGluten() {
		return isSensitiveToGluten;
	}

	public void setSensitiveToGluten(boolean isSensitiveToGluten) {
		this.isSensitiveToGluten = isSensitiveToGluten;
	}

	@Override
	public String toString() {
		return super.toString()+" Customer [isSensitiveToLactose=" + isSensitiveToLactose + ", isSensitiveToGluten=" + isSensitiveToGluten
				+ "]";
	}
}

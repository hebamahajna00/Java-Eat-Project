package Exceptions;

public class HasNoAreaException extends Exception{
	
	
	/*
	 * 
	 * i added this class in case that the manager deleted delivery or person from 
	 * an area(in the update page),then if we wanted to remove them the method in class 
	 * restaurant will throw  null pointer exception thats why i added a new class which prevent the user
	 * from deleting person or area if they have no area.
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public HasNoAreaException()
	{
		super("Delivery Has No idea so you cant remove it!");
	}

}

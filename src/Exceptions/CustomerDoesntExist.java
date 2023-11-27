package Exceptions;

public class CustomerDoesntExist extends Exception{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomerDoesntExist()
	{
		super("Customer does not Exist!!");
	}

}

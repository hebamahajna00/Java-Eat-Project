package Exceptions;

public class CustomerExist extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomerExist()
	{
		super("Failed to add Customer!! its already exists!");
	}

}

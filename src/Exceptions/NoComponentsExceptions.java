package Exceptions;

import application.model.*;

public class NoComponentsExceptions extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoComponentsExceptions(Dish dish) {
		super("The dish "+ dish + " is not include components!");
		
	}
	
}
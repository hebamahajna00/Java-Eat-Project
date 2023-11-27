package application.model;

public final class HelpingClass{
	  
	  private Customer user;
	  private final static  HelpingClass in = new HelpingClass();
	  
	  /*
	   * this class helps me save the current customer in order to use it 
	   * in the customer pages.
	   */
	  private HelpingClass() {}
	  
	  public static HelpingClass getInstance() {
	    return in;
	  }
	  
	  public void setUser(Customer u) {
	    this.user = u;
	  }
	  
	  public Customer getUser() {
	    return this.user;
	  }
	}

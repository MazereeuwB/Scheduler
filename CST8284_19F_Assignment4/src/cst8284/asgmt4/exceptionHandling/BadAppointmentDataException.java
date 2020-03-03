package cst8284.asgmt4.exceptionHandling;



public class BadAppointmentDataException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String description;
	public BadAppointmentDataException(String message, String description) {
		super(message);
		setDescription(description);
	}
	
	public BadAppointmentDataException() {
		this("Please try again", "Bad data entered");
	}
	
	public String getDescription() { return description; }
	public void setDescription(String description) {this.description = description;}
	
	
}
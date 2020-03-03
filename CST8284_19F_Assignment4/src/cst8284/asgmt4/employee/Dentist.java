/*
 *  Course Name:CST8284_310
 *  Student Name: Ben Mazereeuw
 *  Class Name:Object-Oriented Programming
 *  Date: November 28th, 2019
 */
package cst8284.asgmt4.employee;

/**
 * The Dentist class hold the information on the employee type: Dentist, which must contain a fullName String variable, and override 
 * a getActivityType method.
 * This class gives the user four options of assessment, filling, crown, or cosmetic repair as an activity type for a given appointment.
 * 
 * 
 * @author Ben Mazereeuw
 * @version 2nd iteration of this class
 *
 */
public class Dentist extends Employee{
	/**
	 * a constant named ASSESSMENT with an integer value of 1, representing a dental assessment as an activity type for an appointment
	 */
	private static final int ASSESSMENT = 1;
	/**
	 *  a constant named FILLING with an integer value of 2, representing a dental filling as an activity type for an appointment
	 */
	private static final int FILLING = 2;
	/**
	 *  a constant named CROWN with an integer value of 3, representing a dental crown put in as an activity type for an appointment
	 */
	private static final int CROWN = 3;
	/**
	 *  a constant named COSMETIC_REPAIR with an integer value of 4, representing a cosmetic dental repair as an activity type for an appointment
	 */
	private static final int COSMETIC_REPAIR = 4;
	/**
	 * one-arg constructor taking a dentist name as its parameter, and passing it to its superclass, the employee class, to then be stored via a setter.
	 * 
	 * @param fullName of a dentist employee type.
	 */
	public Dentist(String fullName) {
		super(fullName);
		}
	/**
	 * Presents a choice of four activity options to the user for a dental appointment - Assessment, filling, crown, or cosmetic repair using the getResponseTo method,
	 * which returns the user's input, and selects the proper activity to return and be stored for later use.
	 * @return  a String called activity that stores the activity type of a dental appointment
	 */
	@Override
	public String getActivityType() {
		int choice = getResponseTo("Enter a category from the following menu:\n"
				+ ASSESSMENT + ". Assessment\n"
				+ FILLING + ". Filling\n"
				+ CROWN + ". Crown\n"
				+ COSMETIC_REPAIR + ". Cosmetic Repair");
		String activity = "";
		switch(choice) {
		case ASSESSMENT: activity = "Assessment";
			break;
		case FILLING: activity = "Filling";
			break;
		case CROWN: activity = "Crown";
			break;
		case COSMETIC_REPAIR: activity = "Cosmetic Repair";
			break;
		default: System.out.println("Please choose 1 of the 4 options."); getActivityType(); return null;
		}
		System.out.println();
		return activity;
		
	}
	/**
	 * Takes a String, and outputs it to the user as a prompt, and then accepts an integer as a response, and returns it.
	 * @param s - a String taken in to output to the user
	 * @return an integer input by the user
	 */
	public int getResponseTo(String s) {
		System.out.print(s);
		return(scan.nextInt());
	}
	
	


}
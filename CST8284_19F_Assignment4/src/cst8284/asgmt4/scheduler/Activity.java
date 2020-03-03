/*
 *  Course Name:CST8284_310
 *  Student Name: Ben Mazereeuw
 *  Class Name:Object-Oriented Programming
 *  Date: November 28th, 2019
 */
 
package cst8284.asgmt4.scheduler;

import java.io.Serializable;
/**
 * This class holds the data for the activity of a given dentist appointment.  Allows for the flow of data with getters and setters,
 * and an output of data to the user with the toString method.
 * @author Ben Mazereeuw
 * @version 3rd iteration of this class
 *
 */
public class Activity implements Serializable{
	/**
	 * String containing description of work being done in an appointment
	 */
	private String descriptionOfWork;
	/**
	 * String containing category of activity of an appointment
	 */
	private String category;
	/**
	 * serialVersionUID is a marker interface that helps identify which objects can be safely shipped
	 */
	public static final long serialVersionUID = 1L;
	/**
	 * This two-arg constructor passes the data of the Activity class to their respective setters
	 * @param description String of the description of work being done in an appointment
	 * @param category of work being done in an appointment - String
	 */
	public Activity(String description, String category) {
		setDescription(description);
		setCategory(category);
	}
	/**
	 * Getter for DescriptionOfWork String
	 * @return DescriptionOfWork String
	 */
	public String getDescription() {return descriptionOfWork;}
	/**
	 * Setter for descriptionOfWork String
	 * @param descriptionOfWork String
	 */
	public void setDescription(String description) {this.descriptionOfWork = description;}
	/**
	 * Getter for category String
	 * @return category String
	 */
	public String getCategory() {return category;}
	/**
	 * Setter for category String
	 * @param category String
	 */
	public void setCategory(String category) {this.category = category;}
	/**
	 * Gets two strings using getters, the description and category of an appointment and concatenates them together.
	 * @return concatenated strings category and descriptions of the appointment activity.
	 */
	public String toString() {return getCategory() + "\n" + getDescription();}
}

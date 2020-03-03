/*
 *  Course Name:CST8284_310
 *  Student Name: Ben Mazereeuw
 *  Class Name:Object-Oriented Programming
 *  Date: November 28th, 2019
 */
/**
 *  Student Name: Ben Mazereeuw
 *  Class Name:Object-Oriented Programming
 *  Date: October 28th, 2019
 */
package cst8284.asgmt4.scheduler;

import java.io.Serializable;

import cst8284.asgmt4.exceptionHandling.BadAppointmentDataException;
/**
 * This class represents a clients telephone number within the client's information in the appointment scheduler.
 * It takes the number as a String, parses it into three components, the area code, the prefix, and the line number.
 * It then stores each of these in their own String variables to be used for later.
 * @author ben mazereeuw
 * @version 3rd iteration of this class
 *
 */
public class TelephoneNumber implements Serializable{
	/**
	 * integer that stores the area code of the client's phone number
	 */
	private int areaCode;
	/**
	 * integer that stores the line number of the client's phone number
	 */
	private int lineNumber;
	/**
	 * integer that stores the prefix of the client's phone number
	 */
	private int prefix;
	/**
	 * serialVersionUID is a marker interface that helps identify which objects can be safely shipped
	 */
	public static final long serialVersionUID = 1L;
	/**
	 * One-arg constructor that takes in the client's full phone number as a String.
	 * It then checks to make sure that the first digit in the phone number is not a 1 or 0.
	 * Finally it splits up the full phone number into its area code, prefix, and line number, and then it passes those values into 
	 * their respective setters
	 * @param phoneNumber of the client that is input by the user
	 */
	public TelephoneNumber(String phoneNumber) {
		
		if(phoneNumber.trim().charAt(0) == '1' || phoneNumber.trim().charAt(0) == '0') throw new BadAppointmentDataException("Please try again", "Area code can't start with a '0' or a '1'");
		
		else {
		int areaCode = Integer.parseInt(phoneNumber.split("-")[0].trim());
		int prefix = Integer.parseInt(phoneNumber.split("-")[1].trim());
		int lineNumber = Integer.parseInt(phoneNumber.split("-")[2].trim());
		setAreaCode(areaCode); setPrefix(prefix); setLineNumber(lineNumber);
		}
		
	}	
	/**
	 * Getter for the integer areaCode
	 * @return areaCode integer
	 */
	public int getAreaCode() {return areaCode;}
	/**
	 * Setter for the integer areaCode
	 * @param areaCode - takes the integer areaCode and sets it
	 */
	public void setAreaCode(int areaCode) {this.areaCode = areaCode;}
	/**
	 * getter for integer prefix
	 * @return prefix integer
	 */
	public int getPrefix() { return prefix;}
	/**
	 * setter for integer prefix
	 * @param prefix integer
	 */
	public void setPrefix(int prefix) {this.prefix = prefix;}
	/**
	 * getter for integer lineNumber
	 * @return lineNumber integer
	 */
	public int getLineNumber() {return lineNumber;}
	/**
	 * setter for integer lineNumber
	 * @param lineNumber integer
	 */
	public void setLineNumber(int lineNumber) {this.lineNumber = lineNumber;}
	/**
	 * The toString method of TelephoneNumber which concatenates the areaCode, prefix, and lineNumber, using their getters
	 * @return a string of areaCode + prefix + lineNumber
	 */
	public String toString() {return "(" + getAreaCode() +") "+ getPrefix() + "-" + getLineNumber();}
}

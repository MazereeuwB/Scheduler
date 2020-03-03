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
package cst8284.asgmt4.employee;
import java.util.Scanner;
/**
 * An abstract class that sets the core fields and methods for a generic employee, allowing for more specific employee types to 
 * build on this abstract core and become instantiated.
 * @author Ben Mazereeuw
 * @version 2nd iteration of this class
 */
public abstract class Employee {
	/**
	 * String containing name of employee
	 */
	private String fullName;
	
	protected Employee() {this("unknown");}
	protected Employee(String fullName) {setName(fullName);}
	protected static Scanner scan = new Scanner(System.in);
	/**
	 * setter for fullName String -  full name of an employee
	 * @param fullName - name of employee
	 */
	public void setName(String fullName) {this.fullName = fullName;}
	/**
	 * full name of an employee getter
	 * @return fullName - String containing full name of an employee
	 */
	public String getName() {return fullName;}
	/**
	 * Abstract getter of dentist activity type for a given appointment
	 * @return String of the activity type of an appointment
	 */
	public abstract String getActivityType();
	/**
	 * an overridden toString method
	 * @return - full name of employee - String
	 */
	@Override
	public String toString() {return getName();}
	
}
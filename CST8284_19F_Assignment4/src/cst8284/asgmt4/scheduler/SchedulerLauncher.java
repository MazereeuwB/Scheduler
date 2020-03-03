/*
 *  Course Name:CST8284_310
 *  Student Name: Ben Mazereeuw
 *  Class Name:Object-Oriented Programming
 *  Date: November 28th, 2019
 */

package cst8284.asgmt4.scheduler;

import cst8284.asgmt4.employee.Dentist;
/**
 * SchedulerLauncher is the starting point for the appointment scheduler program.
 * It instantiates the dentist object and launches the scheduler with it as an argument.
 * @author Ben Mazereeuw
 * @version 3rd iteration of this class
 *
 */
public class SchedulerLauncher {
	/**
	 * The main method of SchedulerLauncher and the starting point of the application.  It instantiates the dentist object and launches the scheduler.
	 * 
	 * @param args -  command-line arguments
	 */
	public static void main(String[] args) {		
		(new Scheduler(new Dentist("Dr. Mazereeuw"))).launch();
	}
}

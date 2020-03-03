/*
 *  Course Name:CST8284_310
 *  Student Name: Ben Mazereeuw
 *  Class Name:Object-Oriented Programming
 *  Date: November 28th, 2019
 */
package cst8284.asgmt4.scheduler;

import java.util.Scanner;


import cst8284.asgmt4.employee.Employee;
import cst8284.asgmt4.exceptionHandling.BadAppointmentDataException;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream; 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;

	/**
	 * This class contains all of the actions of the program that the user would take.  It is the core of the program.  
	 * It prompts the user for a selection after displaying a menu of options, and it takes that selection and can manipulate the scheduler in many ways,
	 * by creating a new appointment and saving it, displaying an existing appointment or an entire day's schedule.  It can change or delete an existing appointment,
	 * or save it to a file, or load appointments from a file.  All user input and output is put through this class, the other classes of this program are
	 * to support the Scheduler class and allow it to function properly and with maximum code re-use.
	 * @author Ben Mazereeuw
	 * @version 3rd iteration of this class
	 */
public class Scheduler {
/**
 * Instantiates the scanner class, to allow for user input.
 */
	private static Scanner scan = new Scanner(System.in);
	/**
	 * Instantiates an ArrayList of type Appointment
	 */
	private ArrayList<Appointment> appointments = new ArrayList<>();
	/**
	 * Instantiates an Employee object
	 */
	private Employee employee;
	/**
	 * Instantiates a new DateFormat class - to be used to keep a rigid calendar format 
	 */
	private static DateFormat dateFormat= new SimpleDateFormat("ddMMyyyy");
	/**
	 * Instantiates a constant, with the integer value of 1.
	 */
	private static final int SAVE_APPOINTMENT = 1;
	/**
	 * Instantiates a constant, with the integer value of 4.
	 */
	private static final int DISPLAY_APPOINTMENT = 4;
	/**
	 * Instantiates a constant, with the integer value of 5.
	 */
	private static final int DISPLAY_SCHEDULE = 5;
	/**
	 * Instantiates a constant, with the integer value of 3.
	 */
	private static final int CHANGE_APPOINTMENT = 3;
	/**
	 * Instantiates a constant, with the integer value of 2.
	 */
	private static final int DELETE_APPOINTMENT = 2;
	/**
	 * Instantiates a constant, with the integer value of 6.
	 */
	private static final int SAVE_APPOINTMENTS_TO_FILE = 6;
	/**
	 * Instantiates a constant, with the integer value of 7.
	 */
	private static final int LOAD_APPOINTMENTS_FROM_FILE = 7;
	/**
	 * Instantiates a constant, with the integer value of 0.
	 */
	private static final int EXIT = 0;
	/**
	 * The one-arg constructor of the Scheduler class, which takes an Employee class, or one of its subclasses, as its argument, and personalizes the scheduler to that
	 * particular employee by giving them a heading with their name.  It also sets that particular employee's value with the setEmployee() method.
	 * @param emp Employee object to specify the employee's ownership of the scheduler
	 */
	public Scheduler(Employee emp) {
		System.out.println("Scheduling appointments for " + emp);
		setEmployee(emp);
		}
	/**
	 * This is the launching point for the Scheduler class, where it sends the user to the display menu and loops through the menu options
	 * until the user selects to exit the program.  Then it lets the program end naturally.
	 */
	public void launch() {
		int choice = 0;
		do {
			choice = displayMenu();
			executeMenuItem(choice);
		} while (choice != EXIT);
	}
	/**
	 * This method displays the options the user has while executing this program.  It prompts the user to select from many options, and handles the user
	 * input as an int.  It then scans netLine() to eat the remainder input by the nextInt() method.  
	 * @return ch - the integer representing the user's choice of action
	 */
	private int displayMenu() {
		System.out.println("Enter a selection from the following menu:");
		System.out.println(SAVE_APPOINTMENT + ". Save appointment\n" + DELETE_APPOINTMENT + ". Remove appointment\n"
				+ CHANGE_APPOINTMENT + ". Change appointment\n" + DISPLAY_APPOINTMENT + ". Get appointment\n"
				+ DISPLAY_SCHEDULE + ". Display schedule\n" + SAVE_APPOINTMENTS_TO_FILE
				+ ". Backup appointments to file\n" + LOAD_APPOINTMENTS_FROM_FILE + ". Load appointments from file\n"
				+ EXIT + ". Exit program");
		int ch = scan.nextInt();
		scan.nextLine(); // 'eat' the next line in the buffer
		System.out.println();
		return ch;
	}
	/**
	 * This method takes a user input integer as its parameter, and identifies it as their selection of what they want to program to do for them.
	 * It runs through a switch case until their choice lines up with one of the pre-determined values, leading the program to act accordingly.
	 * Since all program actions run through this method, this is where the single BadAppointmentDataException catch statement is put.  
	 * All new BadAppointmentDataException throws are caught here, where two specified messages are output to the user with ex.getMessage() and ex.getDescription().
	 * @param choice is an integer representing the user's choice of action for the program
	 */
	private void executeMenuItem(int choice) {
	try {	switch (choice) {
		case SAVE_APPOINTMENT:
			saveAppointment(makeAppointmentFromUserInput());
			break;
		case DISPLAY_APPOINTMENT:
			displayAppointment(makeCalendarFromUserInput(false));
			break;
		case DISPLAY_SCHEDULE:
			displayDaySchedule(makeCalendarFromUserInput(true));
			break;
		case CHANGE_APPOINTMENT:
			changeAppointment(makeCalendarFromUserInput(false));
			break;
		case DELETE_APPOINTMENT:
			deleteAppointment(makeCalendarFromUserInput(false));
			break;
		case SAVE_APPOINTMENTS_TO_FILE:
			saveAppointmentsToFile(getAppointments(), "CurrentAppointments.apts");			
			break;
		case LOAD_APPOINTMENTS_FROM_FILE:
			loadAppointmentsFromFile("CurrentAppointments.apts", getAppointments());			
			break;
		case EXIT:
			saveAppointmentsToFile(getAppointments(), "CurrentAppointments.apts");
			System.out.println("Exiting Scheduler\n\n");
			break;
		default:
			System.out.println("Invalid choice: try again. (Select " + EXIT + " to exit.)\n");
		}
		System.out.println(); // add blank line after each output
	} catch(BadAppointmentDataException ex) {System.out.println(ex.getMessage() + "; " + ex.getDescription());}
	}
	/**
	 * This simple methods takes the input String and outputs it to the user, prompting them for an input, and then handles that input as a string
	 * with scan.nextLine().  It then checks to make sure that the string input by the user is not empty, and if so it returns that string.
	 * @throws if the string input by the user is empty, a new BadAppointmentDataException is thrown, telling the user to "
	 * please try again" and that they "Must enter a value".
	 * @param s -  a String containing a desired output to the consumer, and prompting for an input.
	 * @return the input given by the user for the given prompt.
	 */
	private static String getResponseTo(String s) {
		System.out.print(s);
		String input = scan.nextLine();
		if(input.trim().isEmpty()) throw new BadAppointmentDataException("Please try again", "Must enter a value");
		return input;
			}
	/**
	 * This is where a new appointment object is created with the input from the user.
	 * A name is entered, along with a phone number, and an activity, using getResponseTo() method to prompt the user for the information and handle the input.
	 * A calendar object is created via the makeCalendarFromUserInput method, along with an Activity object "act" with the activity type, attained via the 
	 * getEmployee().getActivityType() method.  These are all used as arguments when a new Appointment object is instantiated and returned.
	 * @throws new BadAppointmentDataExceptions are thrown if the isNameCorrect and isPhoneFormatCorrect are returned false, indicating a mistake on the user's part.
	 * @return a new Appointment object is returned, containing a Calendar object, an Activity object, a full name, and phone number of a client's appointment.
	 */
	private Appointment makeAppointmentFromUserInput() {
		
			String fullName = getResponseTo("Enter Client Name (as FirstName LastName): ");
			if(!isNameCorrect(fullName)) throw new BadAppointmentDataException("Please try again", "Must enter a value");
			String phoneNumber = getResponseTo("Phone Number (e.g. 613-555-1212): ");
			if(!isPhoneFormatCorrect(phoneNumber)) throw new BadAppointmentDataException("Please try again", "Missing digit(s); correct format is AAA-PPP-NNNN, where\n AAA is the area code and PPP-NNNN is the local number");
			TelephoneNumber phone = new TelephoneNumber(phoneNumber);
			Calendar cal = makeCalendarFromUserInput(false);
			String activity = getResponseTo("Enter Activity: ");
			Activity act = new Activity(activity, getEmployee().getActivityType());
			return (new Appointment(cal, fullName, phone, act));
				
		}
	/**
	 * This metho checks to make sure that the phone number input by the user has the correct format.  It takes the full phone number and splits it into
	 * an array of strings, where each digit is checked, using a for loop, and while loops to make sure it has the appropriate value.  Finally, the full phone number is checked
	 * to make sure it is the appropriate length.  With these checks, no format other than the desired format will pass through.  If an issue is spotted, the method will return false, 
	 * if no issue is spotted, true will be returned.
	 * @param phoneNumber - user inputted full phone number for a given client.
	 * @throws a new BadAppointmentDataException - if there is a digit that is not an integer or the '-' character, a NumberformatException is caught,
	 * and a new exception is thrown telling the user to "Please try again" and "Telephone numbers can only contain numbers or the character '-'".
	 * @return boolean, true if phone is in correct format, false if not.
	 */
	public boolean isPhoneFormatCorrect(String phoneNumber) {
		try{
			String[] number = phoneNumber.split("");
		for(int i = 0; i < phoneNumber.length();) {
			while(i<3 || (i > 3 && i < 7) || (i > 7 && i < 12)) {
				if(Integer.valueOf(number[i]) < 0 || Integer.valueOf(number[i]) > 9) return false;
				i++;
			}
			while(i==3 || i == 7) { if(phoneNumber.charAt(i) != '-') return false;
			i++;
			}
			}
		if(number.length != 12) return false;

		
		return true;
	} catch(NumberFormatException ex) {throw new BadAppointmentDataException("Please try again", "Telephone numbers can only contain numbers or the character '-'");}
	}
	/**
	 * This method checks to see if the first or last name input by the user is in correct form.  First it splits the full name it takes into a first name and a last name,
	 * using the .split() method, and separating the names by a space.  Each name is then checked to make sure it is less than 30 characters, and each name has been entered properly.
	 * If the name is too long, a new BadAppointmentDataException is thrown, telling the user to "Please try again" and "Name cannot exceed 30 characters".
	 * If an IndexOutOfBounds exception is caught, that means the first or last name is empty, and must be re-entered, and the method returns false.  If neither of those issues occur,
	 * the method returns true.
	 * @param name - a String containing the full name of a client, input by the user.
	 * @throws a BadAppointmentDataException if the name is too long.
	 * @return a boolean, true if name is correct, false if not.
	 */
	public boolean isNameCorrect(String name) {
		try {
			String firstName = name.trim().split(" ")[0];
			String lastName = name.trim().split(" ")[1];
		if((firstName.length() > 30) || (lastName.length() > 30)) throw new BadAppointmentDataException("Please try again", "Name cannot exceed 30 characters");
		//if(!(isNameLegal(firstName) && isNameLegal(lastName))) throw new BadAppointmentDataException("Please try again", "Name cannot include characters other than alphabetic\n characters, the dash (-), the period (.), and the apostrophe (')");
		} catch(IndexOutOfBoundsException ex) { return false;}
		return true;
	}
	/*public boolean isNameLegal(String name) {
		String[] newName = name.split("");
		for(int i = 0; i < newName.length; i++) {
			if(newName[i] == "[a-zA-z]") return false;
		}
		return true;
	}*/
	/**
	 * This method makes a calendar from user input, as the name would suggest.  First, the getInstance method is called to create a calendar, which is then cleared
	 * with .clear().  If a specific hour is needed, its default is set at 0.  The previously created format for the date is setLenient(false) meaning it must be strictly
	 * followed to be allowed.  The method then prompts the user to input the day/month/year of the appointment, which is parsed with the format provided to make sure it is
	 * adhered to.  The input string is then parsed into integers and divided into three variables, day, month, and year with the substring method.
	 * If the boolean parameter is true, then is hour of the appointment does not need to be specified, and that part is skipped, 
	 * with the default hour set to 0.  However if the parameter is false, then
	 * it indicates that the hour is needed, and the user is prompted for it. The hour is then processed into a 24-hour format.  Finally, using the 
	 * cal.set() method the date and time of the appointment are set and returned.  This method is entirely encased in a try-catch statement, to catch any 
	 * exceptions while the date is parsed for proper format.  If an exception is caught, it is thrown to the BadAppointmentDataException class with a specified message,
	 * telling the user to "Please try again" and "Bad calendar date entered; format is DDMMYYYY".
	 * @param suppressHour is a boolean which dictates whether an hour is to be specified in this particular calendar object, true supresses the hour input, false requires it.
	 * @return a Calendar object
	 */
	private static Calendar makeCalendarFromUserInput(boolean suppressHour) {
		try {
		Calendar cal = Calendar.getInstance();
		int hour = 0;

		cal.clear();
		
		dateFormat.setLenient(false);
		String date = getResponseTo("Appointment Date (entered as DDMMYYYY): ");
		dateFormat.parse(date);
		int day = Integer.parseInt(date.substring(0, 2));
		int month = Integer.parseInt(date.substring(2, 4)) - 1; // offset by one to account for zero-based month in
																// Calendar
		int year = Integer.parseInt(date.substring(4, 8));

		if (!suppressHour) {
			String time = getResponseTo("Appointment Time: ");
			hour = processTimeString(time);
		}

		cal.set(year, month, day, hour, 0);
		return (cal);
		} catch(ParseException ex) {throw new BadAppointmentDataException("Please try again", "Bad calendar date entered; format is DDMMYYYY");}

	}
	/**
	 * This method is used to process the time portion of the calendar object that is input by the user.  It can be input in many forms, 
	 * such as 1, 1 pm, 1:00, 1:00 pm, 13, 13:00, etc, but must be stored as a single integer value.
	 * This is done by taking the raw string input by the user as a parameter, then it is trimmed of leading and trailing empty spaces, with the trim() method.
	 * Then if the string contains the ':' character, the method splits the string and takes the part before the ':', and parses it into an integer value to be stored as the hour.
	 * If the string instead contains a ' ' character, it is then split at the space, and the string before the space is taken, and parsed into an integer value to be stored as the hour.
	 * If the string contains neither, it is parsed as in integer and stored as is, into the hour variable.
	 * Then finally the hour value is returned, either as is, if it is greater then 8, or with an addition of 12 if it is less than 8, to turn the time into a 24-hour time format.
	 * @param t - a String containing the raw input of the time of an appointment input by the user.
	 * @return the integer containing the 24-formatted hour of an appointment.
	 */
	private static int processTimeString(String t) {
		int hour = 0;
		t = t.trim();
		if (t.contains(":"))
			hour = Integer.parseInt(t.split(":")[0]);
		else if (t.contains(" "))
			hour = Integer.parseInt(t.split(" ")[0]);
		else
			hour = Integer.parseInt(t);
		return ((hour < 8) ? hour + 12 : hour);
	}
	/**
	 * This method takes a calendar date and time, and loops through the appointment array until an appointment is either found or not found with a matching
	 * calendar object.  If a match is found, the method returns the appointment, if a match is not found, a null appointment is returned.
	 * @param cal - a calendar object holding the specified date and time to find a match for.
	 * @return an appointment object
	 */
	private Appointment findAppointment(Calendar cal) {
		for (Appointment aptList : getAppointments())
			if (cal.equals(aptList.getCalendar()))
				return aptList;
		return null;
	}
	/**
	 * This method takes a new appointment object, and first checks to see if it already exists, using findAppointments with the calendar object within the appointment
	 * as an argument, which is created in the previous line.  If the time slot if free, then the new appointment is added using the .add method, and the user is notified,
	 * and the method returns true.
	 * However, if the time slot is not free, then the user is notified of that, and the method returns false and ends.  If the appointment or calendar are null, a NullPointerException
	 * is there to catch it and throw the exception to the BadAppointmentDataException class.
	 * @throws a new BadAppointmentdataException if a NullPointerException is caught, specifying a "Null pointer exception" and telling the user to "Please try again".
	 * @param apt - the newly created appointment by the user input to be saved.
	 * @return a boolean, true if the appointment was saved successfully, and false if it was not.
	 */
	private boolean saveAppointment(Appointment apt) {
		try {
		Calendar cal = apt.getCalendar(); // Check that the appointment does not already exist
		if (findAppointment(cal) == null) { // Time slot available, okay to add appointment
			getAppointments().add(apt);
			System.out.println("Appointment saved.");
			return true;
		} // else time slot taken, need to make another choice
		System.out.println("Cannot save; an appointment at that time already exists");
		return false;
		} catch(NullPointerException ex) { throw new BadAppointmentDataException("Please try again", "Null pointer exception");}
		
		
	}
	/**
	 * Displays an individual appointment's details to the user.  First it instantiates a local appointment object, which takes the value of a specific appointment,
	 * using findAppointment(cal), with the calendar date and time object as its argument.  If the new appointment object is null, the method prints a string specifying
	 * that there is no appointment scheduled for that time on that day and returns a boolean value of false.
	 * However, if the appointment is filled, it is then printed out to the user, using the apt.toString() method, displaying the appointment's details, and then it
	 *  returns a boolean value of true.
	 * @param cal -  a calendar object made by the makeCalendarFromUserInput function, with date and time specified by the user, which is used to find an appointment at that given date and time.
	 * 
	 * @return a boolean, which value is determined by the appointment at the given cal date and time existing.  True if it does, false if it doesn't.
	 */
	private boolean displayAppointment(Calendar cal) {
		Appointment apt = findAppointment(cal);
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		System.out.println((apt != null) ? "\n" + apt.toString() + "\n" : // Output the appointment as a string to the
																			// console, otherwise...
				"No appointment scheduled between " + hr + ":00 and " + (hr + 1) + ":00");
		return (apt != null);
	}
	/**
	 * Displays the current appointments for a given date, in order, from first to last, between 8am and 5pm.  It does this by accepting the calendar parameter,
	 * and then using a for-loop to go through each hour of the day between 8am and 5pm, and displaying each appointment per hour using displayAppointment,
	 * using the calendar parameter as its argument.  DisplayAppointment method will either display the full appointment details of an existing appointment,
	 * or it will show no appointment if one is not found at the specified date and hour.
	 * @param cal - object of Calendar that is passed from makeCalendarFromUserInput, to specify a date and time to check for appointments to be displayed.
	 */
	private void displayDaySchedule(Calendar cal) {
		for (int hrCtr = 8; hrCtr < 17; hrCtr++) {
			cal.set(Calendar.HOUR_OF_DAY, hrCtr);
			displayAppointment(cal);
		}
	}
	/**
	 * Setter for employee, however Employee is an abstract class, so a subclass, such as Dentist, would be instantiated.
	 * @param emp - an employee object of the Employee class
	 */
	private void setEmployee(Employee emp) {
		this.employee = emp;
	}
	/**
	 * Getter for employee, however Employee is an abstract class, so a subclass, such as Dentist, would be instantiated.
	 * @return employee object of Employee Class
	 */
	private Employee getEmployee() {
		return employee;
	}
	/**
	 * This method deletes an existing appointment in the appointments ArrayList, as requested by the user.  First it checks to see if the appointment
	 * exists, using displayAppointment, with the existing calendar object as an argument, to check the date and time.  If it does exist, and the user 
	 * confirms their selection to delete the appointment, the ArrayList method remove(), with the calendar parameter passed to select the date and time of the appointment to be deleted,
	 *  is used with the getAppointments() method to specify which ArrayList to act on.  The user is then notified of the change with output, and the method returns true.
	 *  If the appointment does not exist, the method returns false and ends.
	 *  If the user does not confirm their selection, the method returns false and the user is notified of a cancelled request.
	 * 
	 * @param cal - The calendar object made from makeCalendarFromUserInput, to specify which date and time is to be looked at to find an existing appointment to delete.
	 * @return boolean - true if the deletion was successful, and false if it was not.
	 */
	private boolean deleteAppointment(Calendar cal) {
		if(displayAppointment(cal)) {
		String response = getResponseTo("Enter 'Yes' to delete this appointment ");
		if (response.equals("Yes")) {
			getAppointments().remove(findAppointment(cal));
			System.out.println("Appointment deleted");
			return true;
		} else System.out.println("Request cancelled");
		}return false;
		
	}
	/**
	 * Allows the user to change a given existing appointment.  It first checks to see whether the appointment already exists with 
	 * displayAppointment(cal) - using the user given date and time as an argument.  If there is an appointment at that time, the program
	 * prompts the user to confirm their choice to change the appointment.  If 'Yes', the program prompts the user to enter a new date and time,
	 * which is set with the makeCalendarFromUserInput method.  The new date and time availability are checked via findAppointment, 
	 * and if the appointment is free, the new date and time are set using findAppointment(cal).setCalendar(newCal) and the appointment is re-booked,
	 * and the user if notified.
	 * If the initial appointment doesn't exist, then the method returns false.
	 * If the user does not confirm the date and time change, the request is cancelled, and the user is notified as such.
	 * If the new date and time already align with an existing appointment, the user is notified, and the method is ended.
	 * @param cal - An object of Calendar that contains the existing date and time of the appointment to be changed.
	 * @return boolean, whether the new appointment date and time are free to use (true) or not (false).
	 */
	private boolean changeAppointment(Calendar cal) {
		if(displayAppointment(cal)) {
		String response = getResponseTo("Enter 'Yes' to change the date and time of this appointment ");
		if (response.equals("Yes")) {
			System.out.println("Enter new date and time");
			Calendar newCal = makeCalendarFromUserInput(false);
			if(findAppointment(newCal)==null) {
			findAppointment(cal).setCalendar(newCal);
			System.out.println("Appointment re-booked");
			return true;
		} else System.out.println("That time is already booked for an appointment\n"); 
		} else System.out.println("Request cancelled");
		} return false;
		
	}
	/**
	 * This method loads Appointment objects from the ArrayList apts into the saveFile with an ObjcetOutputStream, with the objectFileStream
	 * as the argument, all within a try-catch statement.  The array is put through an enhanced for-loop to allow for continuous reading out
	 * of objects into the saveFile, until the array is empty.  It then stops and returns its boolean value.
	 * An IOException is put at the end of the try statement in order to catch any possible Input/Output exceptions and let the user know an exception
	 * has occurred.
	 * @param apts - The ArrayList of type Appointment that holds the objects to be read into the saveFile.
	 * @param saveFile - The file that will hold the objects read to it from apts.
	 * @return a boolean - true if the array successfully loaded into the file, and false if there is an exception.
	 */
	private static boolean saveAppointmentsToFile(ArrayList<Appointment> apts, String saveFile) {
		try (FileOutputStream objectFileStream = new FileOutputStream(saveFile);
				ObjectOutputStream oos = new ObjectOutputStream(objectFileStream);) {
			for (Appointment thisApt : apts)
				oos.writeObject(thisApt);
			System.out.println("Appointment data saved to " + saveFile);
			return true;
		} catch (IOException e) {
			System.out.println("Failed to load appointments from " + saveFile);
		}
		return false;
	}
	/**
	 * The apts ArrayList<Appointment> is cleared of existing objects with the clear() method.  Within a try-catch, a new FileInputStream is then instantiated with 
	 * the source file as its argument.  A new object of ObjectInputStream is then instantiated with the FileInputStream object as its argument.
	 * This allows the method to go through a continuous while loop, reading object after object from the sourceFile into the apts ArrayList,
	 * until an end of file exception is reached. 
	 * A fileNotFoundException and an IOException are put in as catch statements to catch any exceptions that might stop the program early.
	 * @param sourceFile - The file containing the list of Appointment objects saved by the saveAppointmentsToFile method.
	 * @param apts - the ArrayList of appointments that store the Appointments objects input from the sourceFile.
	 * @return a boolean - true if the file loading was successful, and false if there was an exception.
	 */
	private static boolean loadAppointmentsFromFile(String sourceFile, ArrayList<Appointment> apts) {
		apts.clear();//remove existing appointments from arrayList before loading file
		try (FileInputStream myFile = new FileInputStream(sourceFile);
				ObjectInputStream ois = new ObjectInputStream(myFile);) {
			while (true) apts.add((Appointment) ois.readObject());
			
		} catch (EOFException e) {
			System.out.println("Appointments successfully loaded from " + sourceFile);
			return true;
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			return false;
		} catch (IOException  | ClassNotFoundException e) {
			return false;
			}
	}
/**
 * Getter for current index of appointments - an ArrayList of type Appointment.
 * @return current index of appointments
 */
	private ArrayList<Appointment> getAppointments() {
		return appointments;
	}

}

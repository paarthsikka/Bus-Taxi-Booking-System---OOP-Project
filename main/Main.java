package main;

import java.util.Scanner;

import bookable.*;
import fileHandler.*;
import userAccount.*;
import transports.*;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("--- Travel Mate ---");

		System.out.println("Login to existing account or create a new account.");
		LoginPrompt();
	}

	public static void LoginPrompt() {
	    Scanner scanner = new Scanner(System.in);
	    	
		System.out.println("Enter your username: ");
		String username = scanner.nextLine();
		
		String [] data = new ReadData().getAccount(username);
		if (data == null) {
			System.out.println("No such user exists, creating new account...");
			System.out.print("Enter your name: ");
			String name = scanner.nextLine();
			System.out.print("Enter Password: ");
			String password = scanner.nextLine();
			System.out.print("Confirm Password: ");
			String rePassword = scanner.nextLine();
			if (rePassword.equals(password)) {
				Account newAccount = new Account(username, name, password);
				new WriteToFile().write("accounts.csv", newAccount.getDetails());
				System.out.println("Account created successfully.");
				AccountMenu(newAccount);
			}
			else {
				System.out.println("Passwords do not match.");
				LoginPrompt();
			}
			scanner.close();
			return;
        }
		
		System.out.print("Enter Password: ");
		String password = scanner.nextLine();

		scanner.close();
		
		Account account = new Account(data);
		if (account.checkPassword(password)) {
			System.out.println("\nWelcome, " + account.getName());
			AccountMenu(account);
		}
		else {
			System.out.println("Invalid password.");
			LoginPrompt();
		}
	}
	
	public static void AccountMenu(Account account) {
		
		System.out.println("\n1. Book a ride");
		System.out.println("2. View bookings");
		System.out.println("3. Cancel a ride");
		System.out.println("4. Check Balance");
		System.out.println("5. Reset Password");
		System.out.println("6. Logout");
		System.out.println("7. Logout and Exit");
		System.out.print("Enter your choice: ");

		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		scanner.close();

		switch (choice) {
		case 1:
			bookRide(account);
			break;
		case 2:
			viewBookings(account);
			break;
		case 3:
			CancelRide(account);
			break;
		case 4:
			viewBalance(account);
			break;
		case 5:
			resetPassword(account);
			break;
		case 6:
			System.out.println("Logging out...");
			LoginPrompt();
			return;
		case 7:
			System.out.println("Program closed...");
			return;
		default:
			System.out.println("Invalid choice.");
		}
		AccountMenu(account);
	}
	
	public static void bookRide(Account account) {
		System.out.print("Enter pick up location: ");
		Scanner scanner = new Scanner(System.in);
		String pickUp = scanner.nextLine();

		System.out.print("Enter destination: ");
		String destination = scanner.nextLine();

		System.out.print("Enter time: ");
		String time = scanner.nextLine();
		
		System.out.print("Enter transport type: ");
		String transportType = scanner.nextLine();
		
		scanner.close();

		Vehicle[] rides = Booking.listAvailableRides(account.getBalance(), transportType, pickUp, destination, time);
		
		if (rides[0] == null) {
        	System.out.println("No rides available.");
        	return;
        }
		
		System.out.println("Available rides:\n");
		
		for (int i = 0; i < rides.length; i++) {
			if (rides[i] != null) {
				System.out.println(i + ". " + rides[i].getName() + " - " + rides[i].getUniqueID());
				System.out.println("Driver: " + rides[i].getDriverName());
				System.out.println("fare: " + rides[i].getFare());
				System.out.println();
			}
		}
        scanner = new Scanner(System.in);
		int rideNo = -1;
		do {
			System.out.print("Enter the ride number you want to book: ");
			rideNo = scanner.nextInt();
			if (rideNo < 1 || rideNo > rides.length || rides[rideNo] == null) {
				System.out.println("Invalid input.");
				scanner.next();
			} else if (rides[rideNo].getFare() > account.getBalance()) {
				System.out.println("Not enough balance.");
			} else
				break;
		} while (true);
		
		scanner.close();

		Booking newBooking = new Booking(account, rides[rideNo], pickUp, destination, time);
		System.out.println("Booking successful. Your booking ID is " + newBooking.getBookingID());
	}
	
	public static void viewBookings(Account account) {
		String[][] data = new ReadData().readAll("bookings.csv");
		System.out.println("Your bookings:\n");
		for (int i = 0; i < data.length; i++) {
			if (data[i][1].equals(account.getUserID())) {
				System.out.println("Booking ID: " + data[i][0]);
				System.out.println("Transport ID: " + data[i][2]);
				System.out.println("Status: " + data[i][4]);
				System.out.println();
			}
		}
	}
	
	public static void viewBalance(Account account) {
		System.out.println("Your balance is " + account.getBalance());
	}
	
	public static void resetPassword(Account account) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter new password: ");
		String newPassword = scanner.nextLine();
		System.out.print("Enter current password: ");
		String oldPassword = scanner.nextLine();
		scanner.close();

		if (account.resetPassword(newPassword, oldPassword)) {
			System.out.println("Password changed successfully.");
			// update account record
			new WriteToFile().updateRecord("accounts.csv", account.getUserID(), account.getDetails());
		} else {
			System.out.println("Invalid current password.\nPassword not changed.");
		}
	}
	
	public static void CancelRide(Account account) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter booking ID: ");
		String bookingID = scanner.nextLine();
		scanner.close();

		String[] data = new ReadData().getBooking(bookingID);
		if (data == null) {
			System.out.println("Invalid booking ID.");
			AccountMenu(account);
			return;
		}
		Booking booking = new Booking(data);
		if (booking.getDetails()[1].equals(account.getUserID())) {
			
			System.out.println("Booking details:");
			System.out.println("Booking ID: " + booking.getBookingID());
			System.out.println("Vehicle Type: " + booking.getVehicleType());
			System.out.println("Transport ID: " + booking.getDetails()[2]);
			System.out.println("Pick up location: " + booking.getPickUp());
			System.out.println("Destination: " + booking.getDestination());
			System.out.println("Status: " + booking.getDetails()[3]);
			
			System.out.print("Type YES to confirm cancellation: ");
			scanner = new Scanner(System.in);
			String choice = scanner.nextLine();
			scanner.close();
			
			if (booking.cancelBooking(choice)) {
				System.out.println("Your booking has been cancelled.");
			} else {
				System.out.println("Booking cancellation ignored.");
			}
		} else {
			System.out.println("Invalid booking ID.");
		}
	}
	  
}

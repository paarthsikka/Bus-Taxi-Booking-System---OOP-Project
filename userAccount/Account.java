package userAccount;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import bookable.*;

public class Account{
    private String userID;
    private String name;
    private String passwordHash;
    private double balance;
    private String[] bookings; // reference no. of bookings
    private static final int MAX_BOOKINGS = 1000;
    private int noOfCurrentBookings;

    // constructor used creating new user
    public Account(String userID, String name, String password){
        this.userID = userID;
        this.name = name;
        this.passwordHash = stringToSHA256(password); // so passwords would not be compromised in case of a data leak
        this.balance = 0.0;
        this.bookings = new String [MAX_BOOKINGS];
        this.noOfCurrentBookings = 0;
    }
    
    // constructor used when reading data from file
    public Account (String ... data){
    	this.userID = data[0];
    	this.name = data[1];
    	this.passwordHash = data[2];
    	this.balance = Double.parseDouble(data[3]);
        this.bookings = new String [MAX_BOOKINGS];
    	for (int i = 4; i < data.length; i++) {
    		bookings[i-4] = data[i];
    	}
    	this.noOfCurrentBookings = data.length - 4;
    }

    // security checks
    private static String stringToSHA256(String text){
        String hash = "";
        
        try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] array = digest.digest(text.getBytes(StandardCharsets.UTF_8));
	        BigInteger bigInt = new BigInteger(1, array);
	        hash = bigInt.toString(16);
//	        for (byte i : array){
//	            hash += String.format("%02x", i);
//	        }
        }
        catch(NoSuchAlgorithmException e) {
//        	System.out.println("Unable to convert string to SHA-256 hash.\nThe password would have to be stored in plain text form.\n");
//        	NOTE: In this case the passwords will be stored in plain text format.
//        		This is a bad security practice, used here only as a fall back.
        	hash = text;
        }
        
        return hash;
    }

    public boolean checkPassword(String input){
        String hashOfInput = stringToSHA256(input);
        if (hashOfInput.equals(this.passwordHash)) return true;
        else return false;
    }
    
    // Override checkPassword method
    public static boolean checkPassword(String input, String [] data) {
    	String hashOfInput = stringToSHA256(input);
        if (hashOfInput.equals(data[2])) return true;
        else return false;
    }

    // setters
    public void addBalance(double balance){
        this.balance += balance;
    }
    
	public void addBooking(String bookingID) {
		this.bookings[noOfCurrentBookings] = bookingID;
		this.noOfCurrentBookings++;
	}

    public boolean resetPassword(String newPassword, String oldPassword){
        if (checkPassword(oldPassword)){
            this.passwordHash = stringToSHA256(newPassword);
            return true;
        }
        else return false;
    }

    // getters
    public String getUserID(){
        return this.userID;
    }

    public String getName(){
        return this.name;
    }

    public double getBalance(){
        return this.balance;
    }

    public String [] getBookings(){
        return this.bookings;
    }
    
    public int getNoOfCurrentBookings() {
    	return this.noOfCurrentBookings;
    }
    
    public Booking getBookingDetails(String bookingID) {
    	return Booking.checkBooking(bookingID);
    }
    
    public String[] getDetails() {
    	String [] details = {this.userID, this.name, this.passwordHash, String.valueOf(this.balance)};
    	return details;
    }
    
}
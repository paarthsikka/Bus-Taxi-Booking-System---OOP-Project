package bookable;

import fileHandler.*;
import transports.*;
import userAccount.Account;

public class Booking {

	String bookingID;
	String clientID; // userID of client
	String transportID; // uniqueID of transport
	String pickUp; // pick up location
	String destination; // destination
	String status; // RESERVED or CANCELLED
	String vehicleType; // transport type
	String time; // time of booking
	
	public Booking(Account client, Vehicle ride, String pickUp, String destination, String time){
		client.addBalance(-ride.getFare());
		this.bookingID = "T";
		int i = new ReadData().readAll("bookings.csv").length+1;
		this.bookingID = String.format("%06d", i);
		this.clientID = client.getUserID();
		this.transportID = ride.getDetails(3);
		this.status = "RESERVED";
		this.vehicleType = ride.getTransportType();
		this.pickUp = pickUp;
		this.destination = destination;
		this.time = time;
		ride.claimSeat();
		client.addBooking(this.bookingID); // adds the booking ID to the client's list of bookings
		new WriteToFile().updateRecord("accounts.csv", client.getUserID(), client.getDetails());
		new WriteToFile().write("bookings.csv", this.getDetails());

		String [] vehicle = new ReadData().getVehicle(this.transportID);
		vehicle[6] = ride.isAvailable(); // set availability to "UNAVAILABLE"
		vehicle[4] = Integer.toString(ride.getSeatingCapacity()); // decrease seating capacity
		new WriteToFile().updateRecord("taxis.csv", this.transportID, vehicle);
	}
	
	public Booking(String ... data){
		this.bookingID = data[0];
		this.clientID = data[1];
		this.transportID = data[2];
		this.status = data[3];
		this.vehicleType = data[4];
		this.pickUp = data[5];
		this.destination = data[6];
	}
    
	public static Vehicle [] listAvailableRides(double balance, String transportType,
			String pickUp, String destination, String time) {
		
		Vehicle [] rides = new Vehicle[20];

		// iterate through list of vehicles
		if (transportType.equals("TAXI")) {
			String [][] data = new ReadData().readAll("taxis.csv");
			for (int i = 0; i < data.length; i++) {
				if (data[i][6].equals("AVAILABLE")) {
					rides[i] = new Taxi(data[i]);
				}
			}
		}
		else if (transportType.equals("BUS")) {
			String [][] data = new ReadData().readAll("buses.csv");
			for (int i = 0; i < data.length; i++) {
				Bus bus = new Bus(data[i]);
                if (bus.checkPlaces(pickUp, destination)
                		&& bus.isAvailable().equals("AVAILABLE"))
                {
                    rides[i] = bus;
                }
            }
		}
		return rides;

	}
	
	public boolean cancelBooking(String choice) {
		if (!choice.equals("YES")) return false;
		this.status = "CANCELLED";
		new WriteToFile().updateRecord("bookings.csv", this.bookingID, this.getDetails());
		String [] vehicle = new ReadData().getVehicle(this.transportID);
		vehicle[6] = "AVAILABLE"; // set availability to "AVAILABLE"
		vehicle[4] = Integer.toString(Integer.parseInt(vehicle[4])+1); // increase seating capacity
		new WriteToFile().updateRecord("taxis.csv", this.transportID, vehicle);

		return true;
	}
	
	public String getBookingID() {
		return this.bookingID;
	}
	
	public static Booking checkBooking(String bookingID) {
		return new Booking(new ReadData().getBooking(bookingID));
	}
	
	public String getTransportID() {
		return this.transportID;
	}

	public String getStatus() {
		return this.status;
	}

	public String getVehicleType() {
		return this.vehicleType;
	}

	public String getPickUp() {
		return this.pickUp;
	}
	
	public String getDestination() {
		return this.destination;
	}
	
	public String[] getDetails() {
		String[] details = { this.bookingID, this.clientID, this.transportID, this.status
				, this.vehicleType, this.pickUp, this.destination, this.time};
		return details;
	}

}

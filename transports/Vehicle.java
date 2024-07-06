package transports;

public abstract class Vehicle implements VehicleInterface {
    private String uniqueID;
    private String name; // name of the vehicle manufacturer and model
    private String transportType; // "TAXI" or "BUS"
    private int seatingCapacity;
    private double fare;
    private String availability; // "AVAILABLE" or "UNAVAILABLE"
    private String driverName;
    
    // constructor
	public Vehicle(String uniqueID, String name, String transportType, 
			String driverName, int seatingCapacity, double fare,
			String availability) {
		this.transportType = transportType;
		this.seatingCapacity = seatingCapacity;
		this.uniqueID = uniqueID;
		this.fare = fare;
		this.name = name;
		this.availability = availability;
		this.driverName = driverName;
	}
	
	// constructor used for reading from file
	public Vehicle(String [] data) {
		this(
				data[0],
				data[1], 
				data[2], 
				data[3],
				Integer.parseInt(data[4]), 
				Double.parseDouble(data[5]),
				data[6]
				);
	}
	
	// Nested class
    public static class TransportID {
        String id;

        // Constructor for TransportID
        TransportID(String id) {
            this.id = id;
        }

        String getIDDetails() {
            return "Transport ID: " + id;
        }
    }
	
    // getters
    public String getDetails(int i) {
    	String [] details = {this.uniqueID, this.name, this.transportType, Integer.toString(this.seatingCapacity), Double.toString(this.fare)};
    	return details[i];
    }
    public String getTransportType() {
    	return this.transportType;
    }
    public int getSeatingCapacity() {
    	return this.seatingCapacity;
    }
    public String getUniqueID() {
    	return this.uniqueID;
    }
    public double getFare() {
    	return this.fare;
    }
    public String getName() {
    	return this.name;
    }
    public String isAvailable() {
    	return this.availability;
    }
    public String getDriverName() {
    	return this.driverName;
    }
    
    // setters
	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public void claimSeat() {
		this.seatingCapacity--;
		if (this.seatingCapacity == 0 || this instanceof Taxi)
            this.availability = "UNAVAILABLE";
	}

	public void releaseSeat() {
        this.seatingCapacity++;
        this.availability = "AVAILABLE";

	}
}

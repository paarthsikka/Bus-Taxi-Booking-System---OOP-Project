class Taxi extends Transport {
    String driverName;
    String source;
    String destination;
    String time;
    double fare;
    boolean wifiAvailability;
    int seatingCapacity; 

    // Constructors
    Taxi(String name, int capacity, String uniqueID, String driverName, String source,
            String destination, String time, double fare, boolean wifiAvailability, int seatingCapacity) {
        super(name, capacity, uniqueID);
        this.driverName = driverName;
        this.source = source;
        this.destination = destination;
        this.time = time;
        this.fare = fare;
        this.wifiAvailability = wifiAvailability;
        this.seatingCapacity = seatingCapacity;

        // Validation checks
        if (seatingCapacity <= 0) {
            throw new IllegalArgumentException("Seating capacity must be greater than zero.");
        }

        if (source == null || source.isEmpty()) {
            throw new IllegalArgumentException("Source cannot be null or empty.");
        }

        if (destination == null || destination.isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be null or empty.");
        }
    }

    // Overloaded method for booking
    void book() {
        System.out.println("Booking for Taxi");
    }

    void book(String passengerName) {
      
        System.out.println("Booking for Taxi with passenger: " + passengerName);
    }

    
    @Override
    public void run() {
        // Placeholder multithreading logic specific to Taxi
        System.out.println("Multithreading specific to Taxi");
    }
}
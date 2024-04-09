abstract class Transport {
    String name;
    int capacity;
    String uniqueID;

    // Constructors
    Transport(String name, int capacity, String uniqueID) {
        this.name = name;
        this.capacity = capacity;
        this.uniqueID = uniqueID;
    }

    Transport(String name, int capacity, String uniqueID, String... additionalDetails) {
        this(name, capacity, uniqueID);
        // Need to think what can be done with the additional details
    }

    // Overloaded method to get details
    String getDetails() {
        return "Name: " + name + ", Capacity: " + capacity + ", Unique ID: " + uniqueID;
    }

    String getDetails(int field) {
        switch (field) {
            case 1:
                return name;
            case 2:
                return String.valueOf(capacity);
            case 3:
                return uniqueID;
            default:
                return "Invalid field";
        }
    }

    // Nested class representing the ID of a transport service
    static class TransportID {
        String id;

        // Constructor for TransportID
        TransportID(String id) {
            this.id = id;
        }

        String getIDDetails() {
            return "Transport ID: " + id;
        }
    }

    public void run() {
        System.out.println("Multithreading logic for transport service: " + name);
    }

    abstract void book();

    void book(int numberOfSeats) throws InvalidSeatsException {
        if (numberOfSeats > capacity) {
            throw new InvalidSeatsException("Number of seats exceeds the capacity");
        }
        System.out.println("Booking logic for Transport with " + numberOfSeats + " seats");
    }
}

// Interface for a reservation system
interface ReservationSystem {
    void reserveSeats(int numberOfSeats);
}

class InvalidSeatsException extends Exception {
    public InvalidSeatsException(String message) {
        super(message);
    }
}





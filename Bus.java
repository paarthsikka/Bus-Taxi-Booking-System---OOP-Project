class Bus extends Transport{
    String manufacturer;
    int year;
    String route;
    String driverName;
    double fare;
    boolean wifiAvailability;
    int seatingCapacity;

    // Constructors
    Bus(String name, int capacity, String uniqueID, String... additionalDetails) {
        super(name, capacity, uniqueID, additionalDetails);
        if (additionalDetails.length > 0) {
            for (String detail : additionalDetails) {
                String[] keyValue = detail.split("=");
                if (keyValue.length == 2) {
                    switch (keyValue[0]) {
                        case "manufacturer":
                            this.manufacturer = keyValue[1];
                            break;
                        case "year":
                            this.year = Integer.parseInt(keyValue[1]);
                            break;
                        case "route":
                            this.route = keyValue[1];
                            break;
                        case "driverName":
                            this.driverName = keyValue[1];
                            break;
                        case "fare":
                            this.fare = Double.parseDouble(keyValue[1]);
                            break;
                        case "wifiAvailability":
                            this.wifiAvailability = Boolean.parseBoolean(keyValue[1]);
                            break;
                        case "seatingCapacity":
                            this.seatingCapacity = Integer.parseInt(keyValue[1]);
                            break;
                        default:
                            System.out.println("Invalid key: " + keyValue[0]);
                    }
                }
            }
        }
    }

    Bus(String name, int capacity, String uniqueID, String manufacturer, int year,
        String route, String driverName, double fare, boolean wifiAvailability) {
        super(name, capacity, uniqueID);
        this.manufacturer = manufacturer;
        this.year = year;
        this.route = route;
        this.driverName = driverName;
        this.fare = fare;
        this.wifiAvailability = wifiAvailability;
    }

    // Overloaded method for booking
    void book() {
        System.out.println("Booking for Bus");
    }

    void book(int numberOfSeats) {
        System.out.println("Booking for Bus with " + numberOfSeats + " seats");
    }

    @Override
    public void run() {
        System.out.println("Multithreading specific to Bus");
    }
}
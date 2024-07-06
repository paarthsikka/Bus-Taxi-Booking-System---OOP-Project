package transports;

public class Bus extends Vehicle implements VehicleInterface{
	
	String [] stops;
	String time;
	double farePerStop;
	
	public Bus(String[] data) {
		super(data);
		this.time = data[7];
		this.farePerStop = Double.parseDouble(data[8]);
		this.stops = new String[data.length-9];
		for (int i = 9; i < data.length; i++) {
			this.stops[i - 9] = data[i];
		}
	}
	
	public String[] getStops() {
		return this.stops;
	}
	
	public double getFarePerStop() {
        return this.farePerStop;
    }
	
	public String getTime() {
		return this.time;
	}
	
	public boolean checkPlaces(String pickUp, String destination) {
		int found = 0;
		for (String stop : this.stops) {
			if (stop.equals(pickUp) || stop.equals(destination)) {
				found++;
			}
		}
		if (found == 2)
			return true;
		else
			return false;
	}
	
}

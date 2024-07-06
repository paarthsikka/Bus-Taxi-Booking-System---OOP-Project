package fileHandler;

import java.io.*;

public final class ReadData {
	
	// Read all data from file
	public String [][] readAll(String file){
        String [][] data = new String[1000][];
        int i = 0;
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                data[i++] = line.split(",");
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
        }
	
	// Extract data from file by keyword
	String [] readLine(String file, String keyWord, int index){
		String [] data = null;
		try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                data = line.split(",");
				if (data[index].equals(keyWord)) {
					break;
				}
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return data;
	}
	
	public String [] getBooking(String bookingID) {
		return readLine("bookings.csv", bookingID, 0);
	}
	
	public String [] getAccount(String userID) {
		return readLine("accounts.csv", userID, 0);
	}
	
	public String[] getVehicle(String transportID) {
		String [] vehicle = readLine("taxis.csv", transportID, 0);
		if (vehicle == null) {
			vehicle = readLine("buses.csv", transportID, 0);
		}
		return vehicle;
	}
}

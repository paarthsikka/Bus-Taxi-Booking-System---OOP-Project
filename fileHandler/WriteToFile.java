package fileHandler;

import java.io.*;

public final class WriteToFile {
	
	// Write data to file
	public void write(String file, String[] data) {
		try {
			FileWriter fw = new FileWriter(file, true);
			for (String s : data) {
				fw.append(s + ",");
			}
			fw.append("\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Overloaded method to write data to file
	public void write(String file, String data) {
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.append(data + "\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// update data by id
	public void updateRecord(String file, String id, String[] data) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String[] temp;
			String updatedData = "";
			while ((line = br.readLine()) != null) {
				temp = line.split(",");
				if (temp[0].equals(id)) {
					updatedData += id + ",";
					for (String s : data) {
						updatedData += s + ",";
					}
					updatedData += "\n";
				} else {
					updatedData += line + "\n";
				}
			}
			br.close();
			fr.close();
			FileWriter fw = new FileWriter(file);
			fw.write(updatedData);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

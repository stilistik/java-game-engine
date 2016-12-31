package csvReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import fileSystem.ResFile;
import general.Settings;

public class CSVreader {
	
	public static List<String[]> readStringCSV(ResFile dataFile){
		ArrayList<String[]> data = new ArrayList<String[]>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Settings.RES_LOC+dataFile.toString()));
			reader.readLine();
			String line;
			while ((line = reader.readLine()) != null){
				String[] lineData = line.split(";");
				if (lineData.length == 0){
					continue;
				}
				data.add(lineData);
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Could not read scene data file.");
			e.printStackTrace();
		}
		return data;
	}

	public static List<float[]> readFloatCSV(ResFile dataFile){
		ArrayList<float[]> data = new ArrayList<float[]>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Settings.RES_LOC+dataFile.toString()));
			reader.readLine();
			String line;
			while ((line = reader.readLine()) != null){
				String[] lineFragments = line.split(";");
				if (lineFragments.length == 0){
					continue;
				}
				float[] lineData = new float[lineFragments.length];
				for (int i = 0; i < lineData.length; i++){
					lineData[i] = Float.parseFloat(lineFragments[i]);
				}
				data.add(lineData);
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Could not read scene data file.");
			e.printStackTrace();
		}
		return data;
	}
}

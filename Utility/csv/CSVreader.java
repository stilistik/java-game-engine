package csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import file.ResFile;
import general.Settings;
import tools.LineSplitter;

public class CSVreader {
	
	private static final String SEPARATOR = ";";
	private BufferedReader reader;
	private LineSplitter splitter;
	
	public CSVreader(ResFile csvFile) throws Exception{
		reader = csvFile.getReader();
	}
	
	public String nextLine(){
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (line != null){
			splitter = new LineSplitter(line, SEPARATOR);
			return line;
		}else{
			return null;
		}
	}
	
	public String nextString(){
		return splitter.nextString();
	}
	
	public int nextInt(){
		return splitter.nextInt();
	}
	
	public long nextLong(){
		return splitter.nextLong();
	}
	
	public float nextFloat(){
		return splitter.nextFloat();
	}
	
	public double nextDouble(){
		return splitter.nextDouble();
	}
	
	public Vector2f nextVector2f(){
		return splitter.nextVector2f();
	}
	
	public Vector3f nextVector3f(){
		return splitter.nextVector3f();
	}
	
	public Vector4f nextVector4f(){
		return splitter.nextVector4f();
	}
	
	public boolean nextBoolean(){
		return splitter.nextBoolean();
	}
	
	public boolean hasNext(){
		return splitter.hasNext();
	}
	
	public void close() throws IOException{
		reader.close();
	}
	
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

package tools;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class LineSplitter {
	
	private int pointer = 0;
	private String[] data;
	
	public LineSplitter(String line, String separator){
		data = line.split(separator);
	}
	
	public String nextString(){
		return data[pointer++];
	}
	
	public int nextInt(){
		return Integer.parseInt(data[pointer++]);
	}
	
	public long nextLong(){
		return Long.parseLong(data[pointer++]);
	}
	
	public float nextFloat(){
		return Float.parseFloat(data[pointer++]);
	}
	
	public double nextDouble(){
		return Double.parseDouble(data[pointer++]);
	}
	
	public Vector2f nextVector2f(){
		float x = Float.parseFloat(data[pointer++]);
		float y = Float.parseFloat(data[pointer++]);
		return new Vector2f(x,y);
	}
	
	public Vector3f nextVector3f(){
		float x = Float.parseFloat(data[pointer++]);
		float y = Float.parseFloat(data[pointer++]);
		float z = Float.parseFloat(data[pointer++]);
		return new Vector3f(x,y,z);
	}
	
	public Vector4f nextVector4f(){
		float x = Float.parseFloat(data[pointer++]);
		float y = Float.parseFloat(data[pointer++]);
		float z = Float.parseFloat(data[pointer++]);
		float w = Float.parseFloat(data[pointer++]);
		return new Vector4f(x,y,z,w);
	}
	
	public boolean nextBoolean(){
		return Integer.parseInt(data[pointer++]) == 1 ? true : false;
	}
	
	public boolean hasNext(){
		return pointer < data.length;
	}
	
	

}

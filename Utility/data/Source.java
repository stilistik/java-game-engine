package data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Source {
	
	private static final String FILE_SEPARATOR = "/";
	
	private String path;
	private String name;
	
	public Source(String path){
		this.path = FILE_SEPARATOR + path;
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}
	
	public Source(String...paths){
		this.path = "";
		for (String path : paths){
			this.path += (FILE_SEPARATOR + path);
		}
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];		
	}
	
	public Source(Source source, String subfile){
		this.path = source.path + FILE_SEPARATOR + subfile;
		this.name = subfile;
	}
	
	public Source(Source source, String...subfiles){
		this.path = source.path;
		for (String subfile : subfiles){
			this.path += (FILE_SEPARATOR + subfile);
		}
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}
	
	public String getPath(){
		return path;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return getPath();
	}
	
	public InputStream getInputStream(){
		return Class.class.getResourceAsStream(path);
	}
	
	public BufferedReader getReader() throws Exception{
		try {
			InputStreamReader isr = new InputStreamReader(getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			return reader;
		} catch (Exception e) {
			System.err.println("File: Could not get reader for File: " + path);
			throw e;
		}
	}
}

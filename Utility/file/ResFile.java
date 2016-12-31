package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import general.Settings;

public class ResFile {
	
	private static final String FILE_SEPARATOR = "/";
	
	private String path;
	private String name;
	
	public ResFile(String path){
		this.path = FILE_SEPARATOR + path;
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}
	
	public ResFile(String...paths){
		this.path = "";
		for (String path : paths){
			this.path += (FILE_SEPARATOR + path);
		}
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];		
	}
	
	public ResFile(ResFile source, String subfile){
		this.path = source.path + FILE_SEPARATOR + subfile;
		this.name = subfile;
	}
	
	public ResFile(ResFile source, String...subfiles){
		this.path = source.path;
		for (String subfile : subfiles){
			this.path += (FILE_SEPARATOR + subfile);
		}
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}
	
	public List<ResFile> getSubDirectories(){
		String currentDir = toString();
		File currentFile = new File(Settings.RES_LOC + currentDir);
		String[] subDirNames = currentFile.list();
		List<ResFile> subDirectories = new ArrayList<ResFile>();
		for (String name : subDirNames){
			if ((new File(Settings.RES_LOC + currentDir + FILE_SEPARATOR + name)).isDirectory()){
				subDirectories.add(new ResFile(this, name));
			}
		}
		return subDirectories;
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
			System.err.println("ResFile: Could not get reader for File: " + path);
			throw e;
		}
	}
}

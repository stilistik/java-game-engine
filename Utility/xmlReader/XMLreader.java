package xmlReader;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fileSystem.ResFile;

public class XMLreader {
	
	private static final Pattern DATA = Pattern.compile(">(.+?)<");
	private static final Pattern START_TAG = Pattern.compile("<(.+?)>");
	private static final Pattern ATTR_NAME = Pattern.compile("(.+?)=");
	private static final Pattern ATTR_VAL = Pattern.compile("\"(.+?)\"");
	private static final Pattern CLOSED = Pattern.compile("(</|/>)");
	
	
	public static XMLnode loadXMLfile(ResFile file){
		BufferedReader reader = null;
		try {
			reader = file.getReader();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("XMLreader: Could not find XML file.");
			System.exit(0);
			return null;
		}
		try {
			reader.readLine();
			XMLnode rootNode = loadNode(reader);
			reader.close();
			return rootNode;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("XMLreader: Error due to corrupt XML file.");
			System.exit(0);
			return null;
		}
	}
	
	private static XMLnode loadNode(BufferedReader reader) throws Exception{
		String line = reader.readLine().trim();
		if (line.startsWith("</")){
			return null;
		}
		String[] startTagParts = getStartTag(line).split(" ");
		XMLnode node = new XMLnode(startTagParts[0].replaceAll("/", ""));
		addAttributes(startTagParts, node);
		addData(line, node);
		if (CLOSED.matcher(line).find()){
			return node;
		}
		XMLnode child = null;
		while((child = loadNode(reader)) != null){
			node.addChild(child);
		}
		return node;		
	}
	
	private static void addData(String data, XMLnode node){
		Matcher match = DATA.matcher(data);
		if (match.find()){
			node.setData(match.group(1));
		}
	}
	
	private static void addAttributes(String[] parts, XMLnode node){
		for (int i = 1; i < parts.length; i++){
			if (parts[i].contains("=")){
				addAttribute(parts[i], node);
			}
		}
	}
	
	private static void addAttribute(String attrib, XMLnode node){
		Matcher nameMatch = ATTR_NAME.matcher(attrib);
		nameMatch.find();
		Matcher valMatch = ATTR_VAL.matcher(attrib);
		valMatch.find();
		node.addAttribute(nameMatch.group(1), valMatch.group(1));
	}
	
	private static String getStartTag(String line){
		Matcher match = START_TAG.matcher(line);
		match.find();
		return match.group(1);
	}

}

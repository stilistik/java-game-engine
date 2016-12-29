package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLnode {
		
	private String name;
	private String data;
	private Map<String, String> attributes;
	private Map<String, List<XMLnode>> childNodes;
	
	public XMLnode(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public String getData(){
		return data;
	}
	
	public String getAttribute(String attrib){
		if (attributes != null){
			return attributes.get(attrib);
		}else{
			return null;
		}
	}
	
	public XMLnode getChild(String childName){
		if (childNodes != null){
			List<XMLnode> children = childNodes.get(childName);
			if (children != null && !children.isEmpty()){
				return children.get(0);
			}
		}
		return null;
	}
	
	public XMLnode getChildWithAttribute(String childName, String attribName, String attribValue){
		if (childNodes != null){
			List<XMLnode> children = childNodes.get(childName);
			if (children != null && !children.isEmpty()){
				for (XMLnode node : children){
					String v = node.getAttribute(attribName);
					if (v.equals(attribValue)){
						return node;
					}
				}
			}
		}
		return null;
	}
	
	public List<XMLnode> getChildren(String childName){
		if (childNodes != null){
			List<XMLnode> children = childNodes.get(childName);
			if (children != null && !children.isEmpty()){
				return children;
			}		
		}
		return new ArrayList<XMLnode>();
	}
	
	public void addAttribute(String attribName, String attribValue){
		if (attributes == null){
			attributes = new HashMap<String, String>();
		}
		attributes.put(attribName, attribValue);
	}
	
	public void addChild(XMLnode child){
		if (childNodes == null){
			childNodes = new HashMap<String, List<XMLnode>>();
		}
		List<XMLnode> list = childNodes.get(child.getName());
		if (list == null){
			list = new ArrayList<XMLnode>();
			childNodes.put(child.getName(), list);
		}
		list.add(child);
	}
	
	public void setData(String data){
		this.data = data;
	}

}

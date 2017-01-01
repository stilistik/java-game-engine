package architecture;

import components.CollisionComponent;
import components.TransformationComponent;

public class ComponentBundle {

	private ComponentContainer components = new ComponentContainer();
	
	public ComponentBundle(){}
	
	public void addComponent(Component component){
		components.put(component.getClass(), component);
	}
	
	public ComponentContainer getComponents(){
		return components;
	}	
}

package entity;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import architecture.Component;
import architecture.ComponentContainer;
import components.CollisionComponent;
import components.TransformationComponent;
import tools.Maths;

public class Entity {
	
	private final int id;
	
	private ComponentContainer components = new ComponentContainer();
	
	public Entity(int id){
		this.id = id;
	}

	public void updateComponents(){
		components.update();
	}
	
	public void addComponent(Component component){
		components.put(component.getClass(), component);
	}
	
	public <T extends Component> T getComponent(Class<T> type){
		return components.get(type);
	}
	
	public int getId() {
		return id;
	}
}

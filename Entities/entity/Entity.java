package entity;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import component.Component;
import tools.Maths;

public class Entity {
	
	private Vector3f position;
	private Vector3f rotation;
	private float scale;
	private Matrix4f transformationMatrix;
	
	private Map<Class, Component> components = new HashMap<Class, Component>();
	
	public Entity(Vector3f position, Vector3f rotation, float scale){
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.transformationMatrix = Maths.createTransformationMatrix(position, rotation.x, rotation.y, rotation.z, scale);
	}

	public void updateComponents(){
		for (Class type : components.keySet()){
			components.get(type).update();
		}
	}
	
	public void addComponent(Component component){
		components.put(component.getClass(), component);
	}
	
	public <T extends Component> T getComponent(Class<T> type){
		if (components.get(type) != null){
			return type.cast(components.get(type));
		}else{
			return null;
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position){
		this.position = position;
	}

	public float getRotX() {
		return rotation.x;
	}

	public float getRotY() {
		return rotation.y;
	}

	public float getRotZ() {
		return rotation.z;
	}
	
	public Vector3f getRotation(){
		return rotation;
	}
	
	public void setRotation(Vector3f rotation){
		this.rotation = rotation;
	}

	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}

	public Matrix4f getTransformationMatrix() {
		return transformationMatrix;
	}
	
	public void updateTransformation(){
		this.transformationMatrix = Maths.createTransformationMatrix(position, rotation.x, rotation.y, rotation.z, scale);
	}
	
}

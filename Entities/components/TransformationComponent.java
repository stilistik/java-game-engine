package components;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import architecture.Component;
import architecture.ComponentType;
import tools.Maths;

public class TransformationComponent implements Component {
	
	private Vector3f position;
	private Vector3f rotation;
	private float scale;
	private Matrix4f transformationMatrix;
	private boolean changed = true;
	
	private List<TransformationListener> listeners = new ArrayList<TransformationListener>();

	
	public TransformationComponent(Vector3f position, Vector3f rotation, float scale){
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.transformationMatrix = new Matrix4f();
		update();
	}
	
	@Override
	public void update() {
		if (changed){
			transformationMatrix = Maths.createTransformationMatrix(position, rotation, scale);
			changed = false;
		}
	}
	
	public void addTransformationListener(TransformationListener listener){
		listeners.add(listener);
	}
	
	public Vector3f getPosition() {
		return position;
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
	
	public float getScale() {
		return scale;
	}
	
	public Matrix4f getTransformationMatrix(){
		return transformationMatrix;
	}
	
	public void setPosition(Vector3f position){
		this.position = position;
		indicateChanged();
	}
	
	public void setRotX(float rx){
		this.rotation.x = rx;
		indicateChanged();
	}
	
	public void setRotY(float ry){
		this.rotation.y = ry;
		indicateChanged();
	}
	
	public void setRotZ(float rz){
		this.rotation.z = rz;
		indicateChanged();
	}
	
	public void setRotation(Vector3f rotation){
		this.rotation = rotation;
		indicateChanged();
	}
	
	public void setScale(float scale){
		this.scale = scale;
		indicateChanged();
	}
	
	public void increasePosition(float dx, float dy, float dz){
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
		indicateChanged();
	}
	
	public void increaseRotation(float dRotX, float dRotY, float dRotZ){
		this.rotation.x += dRotX;
		this.rotation.y += dRotY;
		this.rotation.z += dRotZ;
		indicateChanged();
	}
	
	public void setTransformationMatrix(Matrix4f transformationMatrix){
		this.transformationMatrix = transformationMatrix;
		indicateChanged();
	}
	
	private void indicateChanged(){
		changed = true;
		for (TransformationListener listener : listeners){
			listener.transformationChanged(transformationMatrix);
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.TRANSFORMATION;
	}

}

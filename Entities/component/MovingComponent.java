package component;

import org.lwjgl.util.vector.Vector3f;

import entity.Entity;

public class MovingComponent implements Component{
	
	private Entity entity;
	private float dx, dy, dz;
	private float dRotX, dRotY, dRotZ;
	
	public MovingComponent(Entity entity){
		this.entity = entity;
	}

	@Override
	public void update() {
		increasePosition();
		increaseRotation();
	}

	private void increasePosition(){
		Vector3f position = entity.getPosition();
		position.x += dx;
		position.y += dy;
		position.z += dz;
		entity.setPosition(position);
	}
	
	private void increaseRotation(){
		Vector3f rotation = entity.getRotation();
		rotation.x += dRotX;
		rotation.y += dRotY;
		rotation.z += dRotZ;
		entity.setRotation(rotation);
	}
	
	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getDz() {
		return dz;
	}

	public void setDz(float dz) {
		this.dz = dz;
	}

	public float getdRotX() {
		return dRotX;
	}

	public void setdRotX(float dRotX) {
		this.dRotX = dRotX;
	}

	public float getdRotY() {
		return dRotY;
	}

	public void setdRotY(float dRotY) {
		this.dRotY = dRotY;
	}

	public float getdRotZ() {
		return dRotZ;
	}

	public void setdRotZ(float dRotZ) {
		this.dRotZ = dRotZ;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.MOVING;
	}

}

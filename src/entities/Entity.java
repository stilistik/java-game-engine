package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import collision.AABB;
import models.TexturedModel;
import tools.Maths;

public class Entity {
		
	private TexturedModel model;
	private int textureIndex;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private float distanceToPlayer;
	
	private Matrix4f transformationMatrix;
	protected AABB aabb;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this(model, 0, position, rotX, rotY, rotZ, scale);
	}
	
	public Entity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.textureIndex = textureIndex;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.aabb = new AABB(model.getRawModel().getModelSpaceAABB(), position, rotX, rotY, rotZ, scale);
	}
	
	public void increasePosition(float dx, float dy, float dz){
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz){
		rotX += dx;
		rotY += dy;
		rotZ += dz;
	}
	
	public float getTextureXOffset(){
		int column = textureIndex % model.getTexture().getAtlasDimension();
		return (float) column / (float) model.getTexture().getAtlasDimension();
	}

	public float getTextureYOffset(){
		int row = textureIndex / model.getTexture().getAtlasDimension();
		return (float) row / (float) model.getTexture().getAtlasDimension();
	}
	
	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public void setDistanceToPlayer(float d){
		distanceToPlayer = d;
	}
	
	public float getDistanceToPlayer(){
		return distanceToPlayer;
	}
	
	public AABB getAABB(){
		return aabb;
	}
}

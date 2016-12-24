package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import models.TexturedModel;
import toolBox.Maths;

public class Entity {
	
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private float distanceToPlayer;
	private float aabb[];
	
	private int textureIndex = 0;
	
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
		aabb = calculateBoundingBox();
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
	
	public float[] getBoundingBox(){
		return aabb;
	}
	
	private float[] calculateBoundingBox(){
		if(model.getRawModel().getModelSpaceAABB() == null)
			return null;

		float aabb[] = {
				 Float.MAX_VALUE,  -Float.MAX_VALUE, 
				 Float.MAX_VALUE,  -Float.MAX_VALUE,
				 Float.MAX_VALUE,  -Float.MAX_VALUE
		};
		float modelAABB[] = model.getRawModel().getModelSpaceAABB();
		float vertexPositions[] = {
				modelAABB[0], modelAABB[2], modelAABB[4],
				modelAABB[1], modelAABB[2], modelAABB[4],
				modelAABB[0], modelAABB[2], modelAABB[5],
				modelAABB[1], modelAABB[2], modelAABB[5],
				modelAABB[0], modelAABB[3], modelAABB[4],
				modelAABB[1], modelAABB[3], modelAABB[4],
				modelAABB[0], modelAABB[3], modelAABB[5],
				modelAABB[1], modelAABB[3], modelAABB[5]
		};
		Matrix4f matrix = Maths.createTransformationMatrix(position, rotX, rotY, rotZ, scale);
		for (int i = 0; i < vertexPositions.length; i+=3){
			Vector4f v = new Vector4f(vertexPositions[i], vertexPositions[i+1], vertexPositions[i+2], 1);
			Matrix4f.transform(matrix, v, v);
			if (v.x < aabb[0])
				aabb[0] = v.x;
			
			if (v.x > aabb[1])
				aabb[1] = v.x;
			
			if (v.y < aabb[2])
				aabb[2] = v.y;
			
			if (v.y > aabb[3])
				aabb[3] = v.y;
			
			if (v.z < aabb[4])
				aabb[4] = v.z;
			
			if (v.z > aabb[5])
				aabb[5] = v.z;
		}
		return aabb;		
	}
}

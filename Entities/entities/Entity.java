package entities;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import collision.AABB;
import model.Model;
import tools.Maths;

public class Entity {
	
	protected Model model;
	protected Vector3f position;
	protected float rotX, rotY, rotZ;
	protected float scale;
	protected Matrix4f transformationMatrix;
	protected int textureIndex;
	protected AABB aabb;
	
	public Entity(Model model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float[] boundingBox) {
		super();
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.transformationMatrix = Maths.createTransformationMatrix(position, rotX, rotY, rotZ, scale);
		this.aabb = new AABB(boundingBox, transformationMatrix);
		this.textureIndex = 0;
	}

	public Model getModel() {
		return model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public float getScale() {
		return scale;
	}

	public Matrix4f getTransformationMatrix() {
		return transformationMatrix;
	}

	public int getTextureIndex() {
		return textureIndex;
	}

	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}

	public float getTextureXOffset(){
		int column = textureIndex % model.getTexture().getAtlasDimension();
		return (float) column / (float) model.getTexture().getAtlasDimension();
	}

	public float getTextureYOffset(){
		int row = textureIndex / model.getTexture().getAtlasDimension();
		return (float) row / (float) model.getTexture().getAtlasDimension();
	}

	public AABB getAABB() {
		return aabb;
	}
}

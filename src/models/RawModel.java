package models;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	
	private float[] aabb;
	
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public RawModel(int vaoID, int vertexCount, float[] aabb){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.aabb = aabb;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}	
	
	public float[] getModelSpaceAABB(){
		return aabb;
	}
}

package models;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	private List<Vector3f> boundingBox;
	
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}	
	
	public List<Vector3f> getBoundingBox(){
		return boundingBox;
	}
}

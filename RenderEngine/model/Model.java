package model;

import openGL.Vao;
import texture.Texture;

public class Model {

	private Vao vao;
	private Texture texture;
	private float[] boundingBox;

	public Model(Vao vao, Texture texture, float[] boundingBox) {
		this.vao = vao;
		this.texture = texture;
		this.boundingBox = boundingBox;
	}

	public Vao getVao() {
		return vao;
	}

	public Texture getTexture() {
		return texture;
	}	
	
	public float[] getBoundingBox(){
		return boundingBox;
	}
}

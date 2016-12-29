package model;

import openGL.Vao;
import texture.Texture;

public class Model {

	private Vao vao;
	private Texture texture;

	public Model(Vao vao, Texture texture) {
		this.vao = vao;
		this.texture = texture;
	}

	public Vao getVao() {
		return vao;
	}

	public Texture getTexture() {
		return texture;
	}	
}

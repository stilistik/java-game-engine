package creator;

import org.lwjgl.util.vector.Vector3f;

import data.ResFile;
import entities.DynamicEntity;
import entities.StaticEntity;
import model.Model;
import objReader.ModelData;
import objReader.OBJFileLoader;
import openGL.Vao;
import player.Player;
import skybox.Skybox;
import texture.Texture;

public class EntityCreator {
	
	public static StaticEntity createStaticEntity(ResFile entityFile, Vector3f position, float rotX, float rotY, float rotZ, float scale){
		ModelData md = OBJFileLoader.loadOBJ(new ResFile(entityFile, "model.obj"));
		Texture texture = Texture.newTexture(new ResFile(entityFile, "texture.png")).create();
		Vao vao = createVao(md);
		Model model = new Model(vao, texture);
		return new StaticEntity(model, position, rotX, rotY, rotZ, scale, md.getBoundingBox());
	}
	
	public static DynamicEntity createDynamicEntity(ResFile entityFile, Vector3f position, float rotX, float rotY, float rotZ, float scale){
		ModelData md = OBJFileLoader.loadOBJ(new ResFile(entityFile, "model.obj"));
		Texture texture = Texture.newTexture(new ResFile(entityFile, "texture.png")).create();
		Vao vao = createVao(md);
		Model model = new Model(vao, texture);
		return new Player(model, position, rotX, rotY, rotZ, scale, md.getBoundingBox());
	}	
	
	public static Player createPlayer(ResFile entityFile, Vector3f position, float rotX, float rotY, float rotZ, float scale){
		ModelData md = OBJFileLoader.loadOBJ(new ResFile(entityFile, "model.obj"));
		Texture texture = Texture.newTexture(new ResFile(entityFile, "texture.png")).create();
		Vao vao = createVao(md);
		Model model = new Model(vao, texture);
		return new Player(model, position, rotX, rotY, rotZ, scale, md.getBoundingBox());
	}
	
	private static Vao createVao(ModelData md){
		Vao vao = Vao.create();
		vao.bind();
		vao.createIndexBuffer(md.getIndices());
		vao.createFloatAttribute(0, md.getVertices(), 3);
		vao.createFloatAttribute(1, md.getTextureCoords(), 2);
		vao.createFloatAttribute(2, md.getNormals(), 3);
		vao.unbind();
		return vao;
	}
}

package creator;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import component.CollisionComponent;
import component.LightComponent;
import component.ModelComponent;
import component.PlayerComponent;
import component.TextureAtlasComponent;
import data.ResFile;
import entity.Entity;
import model.Model;
import objReader.ModelData;
import objReader.OBJFileLoader;
import openGL.Vao;
import texture.Texture;

public class EntityCreator {
	
	private static Map<String, Model> models = new HashMap<String, Model>();
	
	public static Entity createLight(Vector3f position, Vector3f color, Vector3f attenuation){
		Entity entity = new Entity(position, new Vector3f(0,0,0),1);
		entity.addComponent(new LightComponent(entity, color, attenuation));
		return entity;
	}

	public static Entity createStaticEntity(ResFile entityFile, Vector3f position, Vector3f rotation, float scale, int textureAtlasDimension, int textureIndex){
		Model model = models.get(entityFile.toString());
		if (model == null){
			model = loadModel(entityFile);
			models.put(entityFile.toString(), model);
		}		
		Entity entity = new Entity(position, rotation, scale);
		entity.addComponent(new ModelComponent(entity, model));
		entity.addComponent(new TextureAtlasComponent(entity, textureAtlasDimension, textureIndex));
		entity.addComponent(new CollisionComponent(entity, model.getBoundingBox()));
		return entity;
	}
	
	public static Entity createPlayer(ResFile entityFile, Vector3f position, Vector3f rotation, float scale){
		ModelData md = OBJFileLoader.loadOBJ(new ResFile(entityFile, "model.obj"));
		Texture texture = Texture.newTexture(new ResFile(entityFile, "texture.png")).create();
		Vao vao = createVao(md);
		Model model = new Model(vao, texture, md.getBoundingBox());
		Entity entity = new Entity(position, rotation, scale);
		entity.addComponent(new ModelComponent(entity, model));
		entity.addComponent(new CollisionComponent(entity, md.getBoundingBox()));
		entity.addComponent(new PlayerComponent(entity));
		return entity;	
	}
	
	private static Model loadModel(ResFile entityFile){
		ModelData md = OBJFileLoader.loadOBJ(new ResFile(entityFile, "model.obj"));
		Texture texture = Texture.newTexture(new ResFile(entityFile, "texture.png")).create();
		Vao vao = createVao(md);
		Model model = new Model(vao, texture, md.getBoundingBox());
		return model;
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

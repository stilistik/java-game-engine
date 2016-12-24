package models;

import org.lwjgl.util.vector.Vector3f;

import objParser.ModelData;
import objParser.OBJFileLoader;
import renderEngine.Loader;
import textures.ModelTexture;

public class ModelCreator {
	
	private Loader loader;
	
	public ModelCreator(Loader loader){
		this.loader = loader;
	}
	
	public TexturedModel createModel(String obj, String tex){
		ModelData md = OBJFileLoader.loadOBJ(obj);
		RawModel rm = loader.loadToVAO(md.getVertices(), md.getTextureCoords(), md.getNormals(), md.getIndices(), md.getBoundingBox());
		ModelTexture mt = new ModelTexture(loader.loadTexture(tex));
		return new TexturedModel(rm, mt);
	}
}

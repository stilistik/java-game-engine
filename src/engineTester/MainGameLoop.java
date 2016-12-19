package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import objParser.ModelData;
import objParser.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrain.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args){
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		Random random = new Random();
		List<Entity> entities = new ArrayList<Entity>();
		
		ModelData stallModelData = OBJFileLoader.loadOBJ("stallModel");
		RawModel stallRawModel = loader.loadToVAO(stallModelData.getVertices(), stallModelData.getTextureCoords(), stallModelData.getNormals(), stallModelData.getIndices());
		ModelTexture stallTexture = new ModelTexture(loader.loadTexture("stallTexture"));
		TexturedModel stallModel = new TexturedModel(stallRawModel, stallTexture);
		entities.add(new Entity(stallModel, new Vector3f(0,0,-40),0,0,0,1.7f));

		ModelData fernModelData = OBJFileLoader.loadOBJ("fernModel");
		RawModel fernRawModel = loader.loadToVAO(fernModelData.getVertices(), fernModelData.getTextureCoords(), fernModelData.getNormals(), fernModelData.getIndices());
		ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fernTexture"));
		fernTexture.setFakeLighting(true);
		fernTexture.setTransparency(true);
		TexturedModel fernModel = new TexturedModel(fernRawModel, fernTexture);
		for (int i = 0; i < 200; i++){
			float x = random.nextFloat()*800-400;
			float z = random.nextFloat()*800-400;
			float ry = random.nextFloat()*600;
			entities.add(new Entity(fernModel, new Vector3f(x,0,z),0,ry,0,1));
		}
		
		ModelData treeModelData = OBJFileLoader.loadOBJ("lowPolyTreeModel");
		RawModel treeRawModel = loader.loadToVAO(treeModelData.getVertices(), treeModelData.getTextureCoords(), treeModelData.getNormals(), treeModelData.getIndices());
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("lowPolyTreeTexture"));
		TexturedModel treeModel = new TexturedModel(treeRawModel, treeTexture);
		for (int i = 0; i < 200; i++){
			float x = random.nextFloat()*800-400;
			float z = random.nextFloat()*800-400;
			float ry = random.nextFloat()*600;
			entities.add(new Entity(treeModel, new Vector3f(x,0,z),0,ry,0,1));
		}
		
		ModelData grassModelData = OBJFileLoader.loadOBJ("grassModel");
		RawModel grassRawModel = loader.loadToVAO(grassModelData.getVertices(), grassModelData.getTextureCoords(), grassModelData.getNormals(), grassModelData.getIndices());
		ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grassTexture"));
		grassTexture.setFakeLighting(true);
		grassTexture.setTransparency(true);
		TexturedModel grassModel = new TexturedModel(grassRawModel, grassTexture);
		for (int i = 0; i < 200; i++){
			float x = random.nextFloat()*800-400;
			float z = random.nextFloat()*800-400;
			float ry = random.nextFloat()*600;
			entities.add(new Entity(grassModel, new Vector3f(x,0,z),0,ry,0,2));
		}
		
		Light sun = new Light(new Vector3f(0,1000,0), new Vector3f(1,1,1));
		Camera camera = new Camera(new Vector3f(0,10,0));
		
		ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("grassFloorTexture"));
		Terrain terrain1 = new Terrain(0,-1,loader, terrainTexture);
		Terrain terrain2 = new Terrain(-1,-1,loader, terrainTexture);

		
		while(!Display.isCloseRequested()){
			camera.move();
			renderer.processTerrain(terrain1);
			renderer.processTerrain(terrain2);

			for (Entity e : entities){
				renderer.processEntity(e);
			}
			renderer.render(sun, camera);

			DisplayManager.updateDisplay();
		}		
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}

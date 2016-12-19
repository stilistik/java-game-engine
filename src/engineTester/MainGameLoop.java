package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrain.Terrain;

public class MainGameLoop {

	public static void main(String[] args){
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		TexturedModel staticModel = new TexturedModel(model, texture);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25),0,0,0,1);
		Light sun = new Light(new Vector3f(0,1000,0), new Vector3f(1,1,1));
		Camera camera = new Camera();
		
		ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("grass"));
		Terrain terrain1 = new Terrain(0,-1,loader, terrainTexture);
		Terrain terrain2 = new Terrain(-1,-1,loader, terrainTexture);

		
		while(!Display.isCloseRequested()){
			camera.move();
			renderer.processTerrain(terrain1);
			renderer.processTerrain(terrain2);

			renderer.processEntity(entity);
			renderer.render(sun, camera);

			DisplayManager.updateDisplay();
		}		
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}

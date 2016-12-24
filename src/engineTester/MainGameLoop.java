package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import collision.CollisionManager;
import entities.Camera;
import entities.Entity;
import entities.Lamp;
import entities.Light;
import entities.Player;
import entities.Sun;
import gameState.GameStateManager;
import gameState.GameStateManager.GameState;
import models.ModelCreator;
import models.RawModel;
import models.TexturedModel;
import objParser.ModelData;
import objParser.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import sceneManager.SceneManager;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args){
		DisplayManager.createMetricsDisplay();
		DisplayManager.createDisplay();
//		DisplayManager.setFullScreen();
		
		Loader loader = new Loader();
		ModelCreator modelCreator = new ModelCreator(loader);
		CollisionManager collisionManager = new CollisionManager();
		SceneManager sceneManager = new SceneManager(loader, collisionManager);
		Random random = new Random();
		
		// terrain
		List<TerrainTexture> terrainTextures = new ArrayList<TerrainTexture>();
		List<TerrainTexture> terrainTextureMaps = new ArrayList<TerrainTexture>();
		terrainTextures.add(new TerrainTexture(loader.loadTexture("textures/LOLgrassFloorTexture0")));
		terrainTextures.add(new TerrainTexture(loader.loadTexture("textures/LOLmossyFloorTexture1")));
		terrainTextures.add(new TerrainTexture(loader.loadTexture("textures/LOLgrassFloorTexture2")));
		terrainTextures.add(new TerrainTexture(loader.loadTexture("textures/LOLstoneFloorTexture0")));
		terrainTextureMaps.add(new TerrainTexture(loader.loadTexture("maps/texMap_c0")));
		terrainTextureMaps.add(new TerrainTexture(loader.loadTexture("maps/texMap_c1")));
		terrainTextureMaps.add(new TerrainTexture(loader.loadTexture("maps/texMap_c2")));
		terrainTextureMaps.add(new TerrainTexture(loader.loadTexture("maps/texMap_c3")));

		TerrainTexturePack ttp = new TerrainTexturePack(terrainTextures, terrainTextureMaps);
		Terrain terrain1 = new Terrain(0, 0, loader, ttp, "heightMap");		
		Terrain terrain2 = new Terrain(0, 1, loader, ttp, "heightMap");
		sceneManager.addTerrain(terrain1);
		sceneManager.addTerrain(terrain2);
		collisionManager.addTerrain(terrain1);
		collisionManager.addTerrain(terrain2);
		
		// entities
		TexturedModel fernModel = modelCreator.createModel("obj/fernModel", "textures/fernTextureAtlas");
		fernModel.getTexture().setFakeLighting(true);
		fernModel.getTexture().setTransparency(true);
		fernModel.getTexture().setAtlasDimension(2);
		for (int i = 0; i < 600; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain1.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			sceneManager.addEntity(new Entity(fernModel, random.nextInt(4), new Vector3f(x,y,z),0,ry,0,1));
		}
		
		TexturedModel pineModel = modelCreator.createModel("obj/pineModel", "textures/pinetexture");
		for (int i = 0; i < 1200; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain1.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			sceneManager.addEntity(new Entity(pineModel, 0, new Vector3f(x,y,z),0,ry,0,1));
		}
		
		TexturedModel cherryTreeModel = modelCreator.createModel("obj/cherryTreeModel", "textures/cherryTreeTexture");
		for (int i = 0; i < 400; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain1.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			sceneManager.addEntity(new Entity(cherryTreeModel, new Vector3f(x,y,z),0,ry,0,2));
		}
		
		TexturedModel grassModel = modelCreator.createModel("obj/grassModel", "textures/grassTexture");
		grassModel.getTexture().setTransparency(true);
		grassModel.getTexture().setFakeLighting(true);
		for (int i = 0; i < 200; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain1.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			sceneManager.addEntity(new Entity(grassModel, new Vector3f(x,y,z),0,ry,0,2));
		}
		
		// light
		Sun sun = new Sun(new Vector3f(0,1000,1000), new Vector3f(1.4f,1,1));
		sceneManager.addLight(sun);
		
		TexturedModel lampModel = modelCreator.createModel("obj/lampModel", "textures/lampTexture");
		lampModel.getTexture().setFakeLighting(true);
		for (int i = 0; i < 50; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain1.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			float r = random.nextFloat()*2;
			float g = random.nextFloat()*2;
			float b = random.nextFloat()*2;
			if (r == 0 && g == 0 && b == 0){
				r = 2;
			}
			Lamp lamp = new Lamp(lampModel, new Vector3f(x, y, z), 0, ry, 0, 1, new Vector3f(r,g,b), new Vector3f(1, 0.01f, 0.002f), 13);
			sceneManager.addEntity(lamp);
			sceneManager.addLight(lamp.getLight());
		}
		
		// player
		TexturedModel playerModel = modelCreator.createModel("obj/playerModel", "textures/playerTexture");
		Player player = new Player(playerModel, new Vector3f(400, 40, 400), 0, 0, 0, 0.7f);
		sceneManager.setPlayer(player);
		collisionManager.setPlayer(player);
		
		// camera
		Camera camera = new Camera(player);	
		sceneManager.setCamera(camera);
		
		while(GameStateManager.getCurrentState() != GameState.CLOSE_REQUESTED){
			GameStateManager.update();
			collisionManager.update();
			sceneManager.update();
			sceneManager.renderScene();
			DisplayManager.updateDisplay();
			DisplayManager.updateMetricDisplay();
		}		
		loader.cleanUp();
		DisplayManager.closeDisplay();
		DisplayManager.closeMetricDisplay();
	}
}

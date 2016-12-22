package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;

import entities.Light;
import entities.Player;
import gameState.GameStateManager;
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
		DisplayManager.createDisplay();
		DisplayManager.setFullScreen();
		Loader loader = new Loader();
		SceneManager sceneManager = new SceneManager(loader);
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
		
		// entities
		
		ModelData stallModelData = OBJFileLoader.loadOBJ("obj/stallModel");
		RawModel stallRawModel = loader.loadToVAO(stallModelData.getVertices(), stallModelData.getTextureCoords(), stallModelData.getNormals(), stallModelData.getIndices());
		ModelTexture stallTexture = new ModelTexture(loader.loadTexture("textures/stallTexture"));
		TexturedModel stallModel = new TexturedModel(stallRawModel, stallTexture);
		sceneManager.addEntity(new Entity(stallModel, new Vector3f(0,0,-40),0,0,0,1.7f));

		ModelData fernModelData = OBJFileLoader.loadOBJ("obj/fernModel");
		RawModel fernRawModel = loader.loadToVAO(fernModelData.getVertices(), fernModelData.getTextureCoords(), fernModelData.getNormals(), fernModelData.getIndices());
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("textures/fernTextureAtlas"));
		fernTextureAtlas.setFakeLighting(true);
		fernTextureAtlas.setTransparency(true);
		fernTextureAtlas.setAtlasDimension(2);
		TexturedModel fernModel = new TexturedModel(fernRawModel, fernTextureAtlas);
		for (int i = 0; i < 600; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain1.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			sceneManager.addEntity(new Entity(fernModel, random.nextInt(4), new Vector3f(x,y,z),0,ry,0,1));
		}
		
		ModelData pineModelData = OBJFileLoader.loadOBJ("obj/pineModel");
		RawModel pineRawModel = loader.loadToVAO(pineModelData.getVertices(), pineModelData.getTextureCoords(), pineModelData.getNormals(), pineModelData.getIndices());
		ModelTexture pineTexture = new ModelTexture(loader.loadTexture("textures/pineTexture"));
		TexturedModel pineModel = new TexturedModel(pineRawModel, pineTexture);
		for (int i = 0; i < 1200; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain1.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			sceneManager.addEntity(new Entity(pineModel, new Vector3f(x,y,z),0,ry,0,1));
		}
		
		ModelData cherryTreeModelData = OBJFileLoader.loadOBJ("obj/cherryTreeModel");
		RawModel cherryTreeRawModel = loader.loadToVAO(cherryTreeModelData.getVertices(), cherryTreeModelData.getTextureCoords(), cherryTreeModelData.getNormals(), cherryTreeModelData.getIndices());
		ModelTexture cherryTreeTexture = new ModelTexture(loader.loadTexture("textures/cherryTreeTexture"));
		TexturedModel cherryTreeModel = new TexturedModel(cherryTreeRawModel, cherryTreeTexture);
		for (int i = 0; i < 400; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain1.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			sceneManager.addEntity(new Entity(cherryTreeModel, new Vector3f(x,y,z),0,ry,0,2));
		}
		
		ModelData grassModelData = OBJFileLoader.loadOBJ("obj/grassModel");
		RawModel grassRawModel = loader.loadToVAO(grassModelData.getVertices(), grassModelData.getTextureCoords(), grassModelData.getNormals(), grassModelData.getIndices());
		ModelTexture grassTexture = new ModelTexture(loader.loadTexture("textures/grassTexture"));
		grassTexture.setFakeLighting(true);
		grassTexture.setTransparency(true);
		TexturedModel grassModel = new TexturedModel(grassRawModel, grassTexture);
		for (int i = 0; i < 200; i++){
			float x = random.nextFloat()*800-400;
			float z = random.nextFloat()*800-400;
			float y = terrain1.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			sceneManager.addEntity(new Entity(grassModel, new Vector3f(x,y,z),0,ry,0,2));
		}
		
		// light
		Light sun = new Light(new Vector3f(0,1000,1000), new Vector3f(1.4f,1,1));
		sceneManager.addLight(sun);
		
		// player
		ModelData playerData = OBJFileLoader.loadOBJ("obj/bunnyModel");
		RawModel playerRawModel = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(), playerData.getNormals(), playerData.getIndices());
		ModelTexture playerTexture = new ModelTexture(loader.loadTexture("textures/pathFloorTexture"));
		TexturedModel playerModel = new TexturedModel(playerRawModel, playerTexture);
		Player player = new Player(playerModel, new Vector3f(400, 40, 400), 0, 0, 0, 0.7f);
		sceneManager.setPlayer(player);
		
		// camera
		Camera camera = new Camera(player);	
		sceneManager.setCamera(camera);
		
		while(!Display.isCloseRequested()){
			sceneManager.update();
			sceneManager.renderScene();
			DisplayManager.updateDisplay();
		}		
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}

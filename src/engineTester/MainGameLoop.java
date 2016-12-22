package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.EntityRenderer;
import entities.EntityShader;
import entities.Light;
import entities.Player;
import gui.GuiRenderer;
import gui.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import objParser.ModelData;
import objParser.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args){
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		Random random = new Random();
		
		// terrain
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassFloorTexture"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mudFloorTexture"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("flowerFloorTexture"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("mossPathTexture"));
		TerrainTexturePack ttp = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		Terrain terrain = new Terrain(0, 0, loader, ttp, "texMap_c", "heightMap");		
		
		// entities
		List<Entity> entities = new ArrayList<Entity>();
		
		ModelData stallModelData = OBJFileLoader.loadOBJ("stallModel");
		RawModel stallRawModel = loader.loadToVAO(stallModelData.getVertices(), stallModelData.getTextureCoords(), stallModelData.getNormals(), stallModelData.getIndices());
		ModelTexture stallTexture = new ModelTexture(loader.loadTexture("stallTexture"));
		TexturedModel stallModel = new TexturedModel(stallRawModel, stallTexture);
		entities.add(new Entity(stallModel, new Vector3f(0,0,-40),0,0,0,1.7f));

		ModelData fernModelData = OBJFileLoader.loadOBJ("fernModel");
		RawModel fernRawModel = loader.loadToVAO(fernModelData.getVertices(), fernModelData.getTextureCoords(), fernModelData.getNormals(), fernModelData.getIndices());
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fernTextureAtlas"));
		fernTextureAtlas.setFakeLighting(true);
		fernTextureAtlas.setTransparency(true);
		fernTextureAtlas.setAtlasDimension(2);
		TexturedModel fernModel = new TexturedModel(fernRawModel, fernTextureAtlas);
		for (int i = 0; i < 600; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			entities.add(new Entity(fernModel, random.nextInt(4), new Vector3f(x,y,z),0,ry,0,1));
		}
		
		ModelData pineModelData = OBJFileLoader.loadOBJ("pineModel");
		RawModel pineRawModel = loader.loadToVAO(pineModelData.getVertices(), pineModelData.getTextureCoords(), pineModelData.getNormals(), pineModelData.getIndices());
		ModelTexture pineTexture = new ModelTexture(loader.loadTexture("pineTexture"));
		TexturedModel pineModel = new TexturedModel(pineRawModel, pineTexture);
		for (int i = 0; i < 800; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			entities.add(new Entity(pineModel, new Vector3f(x,y,z),0,ry,0,1));
		}
		
		ModelData cherryTreeModelData = OBJFileLoader.loadOBJ("cherryTreeModel");
		RawModel cherryTreeRawModel = loader.loadToVAO(cherryTreeModelData.getVertices(), cherryTreeModelData.getTextureCoords(), cherryTreeModelData.getNormals(), cherryTreeModelData.getIndices());
		ModelTexture cherryTreeTexture = new ModelTexture(loader.loadTexture("cherryTreeTexture"));
		TexturedModel cherryTreeModel = new TexturedModel(cherryTreeRawModel, cherryTreeTexture);
		for (int i = 0; i < 200; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			entities.add(new Entity(cherryTreeModel, new Vector3f(x,y,z),0,ry,0,2));
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
			float y = terrain.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*600;
			entities.add(new Entity(grassModel, new Vector3f(x,y,z),0,ry,0,2));
		}
		
		// light
		Light sun = new Light(new Vector3f(0,1000,1000), new Vector3f(1.4f,1,1));

		List<Light> lights = new ArrayList<Light>();
		lights.add(sun);
		
		// player
		ModelData playerData = OBJFileLoader.loadOBJ("bunnyModel");
		RawModel playerRawModel = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(), playerData.getNormals(), playerData.getIndices());
		ModelTexture playerTexture = new ModelTexture(loader.loadTexture("pathFloorTexture"));
		TexturedModel playerModel = new TexturedModel(playerRawModel, playerTexture);
		Player player = new Player(playerModel, new Vector3f(400, 40, 400), 0, 0, 0, 1);
		
		// camera
		Camera camera = new Camera(player);		
		
		while(!Display.isCloseRequested()){
			player.move(terrain);
			camera.move();
			renderer.processTerrain(terrain);
			renderer.processEntity(player);
			for (Entity e : entities){
				renderer.processEntity(e);
			}
			renderer.render(lights, camera);
			DisplayManager.updateDisplay();
		}		
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}

package main;


import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import collision.CollisionManager;
import creator.EntityCreator;
import creator.TerrainCreator;
import data.ResFile;
import display.DisplayManager;
import entity.Entity;
import gameStateManager.GameStateManager;
import gameStateManager.GameStateManager.GameState;
import light.Sun;
import player.Camera;
import scene.Scene;
import sceneCreator.SceneCreator;
import sceneManager.SceneManager;
import terrain.Terrain;


public class Main {

	public static void main(String[] args){
		DisplayManager.createMetricsDisplay();
		DisplayManager.createDisplay();
	
		CollisionManager collisionManager = new CollisionManager();
		SceneManager sceneManager = new SceneManager();
		Random random = new Random();
				
		// terrain
		Terrain terrain = TerrainCreator.createTerrainRandom(new ResFile("res/terrain/forest"), "forest", 0, 0);
		collisionManager.addTerrain(terrain);
		sceneManager.addTerrain(terrain);
		
		Scene scene = SceneCreator.loadScene(new ResFile("res/scenes/forest"));
		
		// entity
		for (int i = 0; i < 1200; i++){
			float x = random.nextFloat() * Terrain.SIZE;
			float z = random.nextFloat() * Terrain.SIZE;
			float y = terrain.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*360;
			sceneManager.addEntity(EntityCreator.createStaticEntity("Pine", new ResFile("res/entities/pine"), new Vector3f(x,y,z), new Vector3f(0,ry,0), 1, 1, 0));
		}
		
		for (int i = 0; i < 400; i++){
			float x = random.nextFloat() * Terrain.SIZE;
			float z = random.nextFloat() * Terrain.SIZE;
			float y = terrain.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*360;
			sceneManager.addEntity(EntityCreator.createStaticEntity("Cherry", new ResFile("res/entities/cherry"), new Vector3f(x,y,z), new Vector3f(0,ry,0), 2, 1, 0));
		}
		// player
		Entity player = EntityCreator.createPlayer(new ResFile("res/entities/player"), new Vector3f(400,10,400), new Vector3f(0,0,0), 1);
		collisionManager.setPlayer(player);
		sceneManager.setPlayer(player);
		
		// camera
		Camera camera = new Camera(player);
		collisionManager.setCamera(camera);
		sceneManager.setCamera(camera);
		
		// lights
		Entity sun = EntityCreator.createLight(new Vector3f(0,1000,0), new Vector3f(1.4f,1,1), new Vector3f(1,0,0));
		sceneManager.addLight(sun);
		
		while(GameStateManager.getCurrentState() != GameState.CLOSE_REQUESTED){
			collisionManager.update();
			sceneManager.update();
			sceneManager.renderScene();
			DisplayManager.updateDisplay();
			DisplayManager.updateMetricDisplay();	
			GameStateManager.update();
		}		

		DisplayManager.closeDisplay();
		DisplayManager.closeMetricDisplay();
	}
}

package main;


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
import sceneManager.SceneManager;
import terrain.Terrain;


public class Main {

	public static void main(String[] args){
		DisplayManager.createMetricsDisplay();
		DisplayManager.createDisplay();
	
		CollisionManager collisionManager = new CollisionManager();
		SceneManager sceneManager = new SceneManager();
				
		// terrain
		Terrain terrain = TerrainCreator.createTerrainRandom(new ResFile("res/terrain/forest"), "forest", 0, 0);
		collisionManager.addTerrain(terrain);
		sceneManager.addTerrain(terrain);
		
		// entity
		Entity pineTest = EntityCreator.createStaticEntity(new ResFile("res/entities/fern"), new Vector3f(410, 10, 410), new Vector3f(0,0,0), 1, 2, 1);
		sceneManager.addEntity(pineTest);
		
		// player
		Entity player = EntityCreator.createPlayer(new ResFile("res/entities/player"), new Vector3f(400,10,400), new Vector3f(0,0,0), 1);
		collisionManager.setPlayer(player);
		sceneManager.setPlayer(player);
		
		// camera
		Camera camera = new Camera(player);
		collisionManager.setCamera(camera);
		sceneManager.setCamera(camera);
		
		// lights
		Sun sun = new Sun(new Vector3f(0,1000,0), new Vector3f(1.4f,1,1));
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

package main;


import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import collision.CollisionManager;
import creator.EntityCreator;
import display.DisplayManager;
import entity.Entity;
import fileSystem.ResFile;
import gameStateManager.GameStateManager;
import gameStateManager.GameStateManager.GameState;
import scene.Scene;
import sceneCreator.SceneCreator;
import sceneManager.SceneManager;


public class Main {

	public static void main(String[] args){
		DisplayManager.createMetricsDisplay();
		DisplayManager.createDisplay();
	
		CollisionManager collisionManager = new CollisionManager();
		SceneManager sceneManager = new SceneManager();
		
		Scene scene = SceneCreator.loadScene(new ResFile("res/scenes/forest"));
		collisionManager.setScene(scene);
		sceneManager.setScene(scene);
		
		// player
		Entity player = EntityCreator.createPlayer(new ResFile("res/entities/player"), new Vector3f(400,10,400), new Vector3f(0,0,0), 1);
		collisionManager.setPlayer(player);
		sceneManager.setPlayer(player);
		
		// camera
		Camera camera = new Camera(player);
		collisionManager.setCamera(camera);
		sceneManager.setCamera(camera);
		
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

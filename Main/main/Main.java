package main;
 

import Camera.Camera;
import collision.CollisionManager;
import display.DisplayManager;
import file.ResFile;
import gameStateManager.GameStateManager;
import gameStateManager.GameStateManager.GameState;
import scene.Scene;
import sceneCreator.SceneCreator;
import sceneManager.SceneManager;


public class Main {

	public static void main(String[] args){
		
		// managers
		DisplayManager.createMetricsDisplay();
		DisplayManager.createDisplay();
		DisplayManager.setFullScreen();
		CollisionManager collisionManager = new CollisionManager();
		SceneManager sceneManager = new SceneManager();
		
		// scene
		Scene scene = SceneCreator.loadScene(new ResFile("res/scenes/forest"));
		collisionManager.setScene(scene);
		sceneManager.setScene(scene);
	
		// camera
		Camera camera = new Camera(scene.getPlayer());
		collisionManager.setCamera(camera);
		sceneManager.setCamera(camera);
		
		// main loop
		while(GameStateManager.getCurrentState() != GameState.CLOSE_REQUESTED){
			collisionManager.update();
			sceneManager.update();
			sceneManager.renderScene();
			DisplayManager.updateDisplay();
			DisplayManager.updateMetricDisplay();	
			GameStateManager.update();
		}		

		// closing operations
		DisplayManager.closeDisplay();
		DisplayManager.closeMetricDisplay();
	}
}

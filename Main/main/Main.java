package main;


import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import collision.CollisionManager;
import component.TextureAtlasComponent;
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

package gameState;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;

public class GameStateManager {
	
	public static enum GameState {
		CLOSE_REQUESTED, MENU, RUNNING
	}
	
	private static GameState currentGameState;

	public static void update(){
		checkInputs();
	}
	
	private static void checkInputs(){
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			currentGameState = GameState.CLOSE_REQUESTED;
		}
		if (Display.isCloseRequested()){
			currentGameState = GameState.CLOSE_REQUESTED;
		}
	}
	
	public static GameState getCurrentState(){
		return currentGameState;
	}

}

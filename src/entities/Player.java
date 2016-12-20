package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrain.Terrain;

public class Player extends Entity {
	
	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float JUMP_POWER = 30;
	private static final float GRAVITY = -50;
	private static final float TERRAIN_HEIGHT = 0;

	private float modelHeight = 5;
	
	private float currentSpeed;
	private float currentTurnSpeed;
	private float upwardsSpeed;
	
	private boolean inAir = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move(Terrain terrain){
		checkInputs();
		float t = DisplayManager.getFrameTimeSeconds();
		super.increaseRotation(0, currentTurnSpeed * t, 0);
		float distance = currentSpeed * t;
		upwardsSpeed += GRAVITY * t;
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		float dy = upwardsSpeed * t;
		super.increasePosition(dx, dy, dz);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if (super.getPosition().y < terrainHeight){
			upwardsSpeed = 0;
			super.getPosition().y = terrainHeight;
			inAir = false;
		}
	}
	
	private void checkInputs(){
		if (Keyboard.isKeyDown(Keyboard.KEY_W)){
			currentSpeed = RUN_SPEED;
		}else if (Keyboard.isKeyDown(Keyboard.KEY_S)){
			currentSpeed = -RUN_SPEED;
		}else{
			currentSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)){
			currentTurnSpeed = TURN_SPEED;
		}else if (Keyboard.isKeyDown(Keyboard.KEY_D)){
			currentTurnSpeed = -TURN_SPEED;
		}else{
			currentTurnSpeed = 0;
		}	
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			jump();
		}
	}	
	
	private void jump(){
		if (!inAir){
			upwardsSpeed = JUMP_POWER;
			inAir = true;
		}
	}

	public float getModelHeight() {
		return modelHeight;
	}

	public void setModelHeight(float modelHeight) {
		this.modelHeight = modelHeight;
	}
}

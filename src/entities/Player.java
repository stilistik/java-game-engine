package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

public class Player extends Entity {
	
	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float JUMP_POWER = 30;
	private static final float GRAVITY = -50;
	private static final float TERRAIN_HEIGHT = 0;

	private float currentSpeed;
	private float currentTurnSpeed;
	private float upwardsSpeed;
	
	private boolean inAir = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move(){
		checkInputs();
		float t = DisplayManager.getFrameTimeSeconds();
		super.increaseRotation(0, currentTurnSpeed * t, 0);
		float distance = currentSpeed * t;
		upwardsSpeed += GRAVITY * t;
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		float dy = upwardsSpeed * t;
		super.increasePosition(dx, dy, dz);
		if (super.getPosition().y < TERRAIN_HEIGHT){
			upwardsSpeed = 0;
			super.getPosition().y = TERRAIN_HEIGHT;
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
}

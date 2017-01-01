package components;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import architecture.Component;
import architecture.ComponentType;
import display.DisplayManager;
import entity.Entity;

public class PlayerComponent implements Component{
	
	private static final float RUN_SPEED = 80;
	private static final float TURN_SPEED = 160;
	private static final float JUMP_POWER = 80;
	private static final float GRAVITY = -100;
	
	private Entity owner;
	
	private float currentSpeed;
	private float currentTurnSpeed;
	private float upwardsSpeed;
	private boolean inAir = false;
	
	
	public PlayerComponent(Entity owner){
		this.owner = owner;
	}
	
	public void checkInputs() {
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
	
	public void move() {
		checkInputs();
		float t = DisplayManager.getFrameTimeSeconds();
		increaseRotation(0, currentTurnSpeed * t, 0);
		float distance = currentSpeed * t;
		upwardsSpeed += GRAVITY * t;
		float dx = (float) (distance * Math.sin(Math.toRadians(owner.getComponent(TransformationComponent.class).getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(owner.getComponent(TransformationComponent.class).getRotY())));
		float dy = upwardsSpeed * t;
		increasePosition(dx, dy, dz);	
	}
	
	private void increasePosition(float dx, float dy, float dz){
		owner.getComponent(TransformationComponent.class).increasePosition(dx, dy, dz);
	}
	
	private void increaseRotation(float dRotX, float dRotY, float dRotZ){
		owner.getComponent(TransformationComponent.class).increaseRotation(dRotX, dRotY, dRotZ);
	}

	@Override
	public void update() {}
	
	private void jump(){
		if (!inAir){
			upwardsSpeed = JUMP_POWER;
			inAir = true;
		}
	}

	public float getUpwardsSpeed() {
		return upwardsSpeed;
	}

	public void setUpwardsSpeed(float upwardsSpeed) {
		this.upwardsSpeed = upwardsSpeed;
	}

	public boolean isInAir() {
		return inAir;
	}

	public void setInAir(boolean inAir) {
		this.inAir = inAir;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.PLAYER;
	}

}

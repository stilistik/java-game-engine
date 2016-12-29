package player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import display.DisplayManager;
import entities.DynamicEntity;
import interfaces.Controllable;
import model.Model;
import tools.Maths;

public class Player extends DynamicEntity implements Controllable{
	
	private static final float RUN_SPEED = 80;
	private static final float TURN_SPEED = 160;
	private static final float JUMP_POWER = 30;
	private static final float GRAVITY = -100;
	
	private float currentSpeed;
	private float currentTurnSpeed;
	private float upwardsSpeed;
	private boolean inAir = false;
	
	public Player(Model model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
			float[] boundingBox) {
		super(model, position, rotX, rotY, rotZ, scale, boundingBox);
	}

	@Override
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

	@Override
	public void move() {
		checkInputs();
		float t = DisplayManager.getFrameTimeSeconds();
		increaseRotation(0, currentTurnSpeed * t, 0);
		float distance = currentSpeed * t;
		upwardsSpeed += GRAVITY * t;
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		float dy = upwardsSpeed * t;
		increasePosition(dx, dy, dz);	
		updateTransformation();
		aabb.update(transformationMatrix);
	}

	@Override
	public void updateTransformation() {
		this.transformationMatrix = Maths.createTransformationMatrix(position, rotX, rotY, rotZ, scale);
	}
	
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
}

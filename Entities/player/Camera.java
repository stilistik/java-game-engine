package player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import entity.Entity;

public class Camera {
	
	private static final float MOUSE_SENSITIVITY = 0.1f;
	private static final float ANGLE_RESET_SPEED = 5f;
	private static final float MAX_ZOOM = 200;
	private static final float MIN_ZOOM = 20;
	
	private Entity player;
	
	private Vector3f position = new Vector3f(0,0,0);
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	private float pitch = 30;
	private float yaw; 
	private float roll;
		
	public Camera(Entity player){
		this.player = player;
	}
	
	public void move(){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float hDistance = (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
		float vDistance = (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
		float theta = player.getRotY() + angleAroundPlayer;
		float xOffset = (float) (hDistance * Math.sin(Math.toRadians(theta)));
		float zOffset = (float) (hDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - xOffset;
		position.y = player.getPosition().y + vDistance;
		position.z = player.getPosition().z - zOffset;
		yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * MOUSE_SENSITIVITY;
		distanceFromPlayer -= zoomLevel;
		if (distanceFromPlayer > MAX_ZOOM){
			distanceFromPlayer = MAX_ZOOM;
		}
		if (distanceFromPlayer < MIN_ZOOM){
			distanceFromPlayer = MIN_ZOOM;
		}
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(0)){
			float dPitch = Mouse.getDY() * MOUSE_SENSITIVITY;
			pitch -= dPitch;
		}
	}
	
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(0)){
			float dAngle = Mouse.getDX() * MOUSE_SENSITIVITY;
			angleAroundPlayer -= dAngle;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && !Mouse.isButtonDown(0) || Keyboard.isKeyDown(Keyboard.KEY_S) && !Mouse.isButtonDown(0)){
			if (angleAroundPlayer > 0)
				angleAroundPlayer -= ANGLE_RESET_SPEED;
			if (angleAroundPlayer < 0){
				angleAroundPlayer += ANGLE_RESET_SPEED;
			}
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
}

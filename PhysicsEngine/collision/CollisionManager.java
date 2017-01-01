package collision;

import Camera.Camera;
import components.PlayerComponent;
import components.TransformationComponent;
import scene.Scene;

public class CollisionManager {
	
	private static final float CAMERA_TERRAIN_COLLISION_OFFSET = 1;
	
	private Camera camera;
	private Scene scene;

	public CollisionManager() {}
	
	public void update(){
		scene.getPlayer().getComponent(PlayerComponent.class).move();	
		terrainCollision();
		scene.getPlayer().updateComponents();
		camera.move();
		terrainCollisionCamera();
	}
	
	private void terrainCollision(){
		float xpos = scene.getPlayer().getComponent(TransformationComponent.class).getPosition().x;
		float zpos = scene.getPlayer().getComponent(TransformationComponent.class).getPosition().z;
		float terrainHeight = scene.getTerrain().getHeightOfTerrain(xpos, zpos);
		if (scene.getPlayer().getComponent(TransformationComponent.class).getPosition().y < terrainHeight){
			scene.getPlayer().getComponent(PlayerComponent.class).setUpwardsSpeed(0);
			scene.getPlayer().getComponent(TransformationComponent.class).getPosition().y = terrainHeight;
			scene.getPlayer().getComponent(PlayerComponent.class).setInAir(false);
		}
	}
	
	private void terrainCollisionCamera(){
		float terrainHeight = scene.getTerrain().getHeightOfTerrain(camera.getPosition().x, camera.getPosition().z);
		if (camera.getPosition().y < terrainHeight + CAMERA_TERRAIN_COLLISION_OFFSET){
			camera.getPosition().y = terrainHeight + CAMERA_TERRAIN_COLLISION_OFFSET;
		}
	}
	
	public void setScene(Scene scene){
		this.scene = scene;
	}
	
	public void setCamera(Camera camera){
		this.camera = camera;
	}
}

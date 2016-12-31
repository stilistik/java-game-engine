package collision;

import Camera.Camera;
import component.PlayerComponent;
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
	}
	
	private void terrainCollision(){
		float terrainHeight = scene.getTerrain().getHeightOfTerrain(scene.getPlayer().getPosition().x, scene.getPlayer().getPosition().z);
		if (scene.getPlayer().getPosition().y < terrainHeight){
			scene.getPlayer().getComponent(PlayerComponent.class).setUpwardsSpeed(0);
			scene.getPlayer().getPosition().y = terrainHeight;
			scene.getPlayer().getComponent(PlayerComponent.class).setInAir(false);
		}
		terrainHeight = scene.getTerrain().getHeightOfTerrain(camera.getPosition().x, camera.getPosition().z);
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

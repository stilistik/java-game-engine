package collision;

import Camera.Camera;
import component.PlayerComponent;
import entity.Entity;
import scene.Scene;

public class CollisionManager {
	
	private static final float CAMERA_TERRAIN_COLLISION_OFFSET = 1;
	
	private Entity player;
	private Camera camera;
	
	private Scene scene;

	
	public CollisionManager() {}
	
	public void update(){
		player.updateComponents();
		camera.move();	
		terrainCollision();
	}
	
	private void terrainCollision(){
		float terrainHeight = scene.getTerrain().getHeightOfTerrain(player.getPosition().x, player.getPosition().z);
		if (player.getPosition().y < terrainHeight){
			player.getComponent(PlayerComponent.class).setUpwardsSpeed(0);
			player.getPosition().y = terrainHeight;
			player.getComponent(PlayerComponent.class).setInAir(false);
		}
		terrainHeight = scene.getTerrain().getHeightOfTerrain(camera.getPosition().x, camera.getPosition().z);
		if (camera.getPosition().y < terrainHeight + CAMERA_TERRAIN_COLLISION_OFFSET){
			camera.getPosition().y = terrainHeight + CAMERA_TERRAIN_COLLISION_OFFSET;
		}
	}
	
	public void setScene(Scene scene){
		this.scene = scene;
	}
	
	public void setPlayer(Entity player){
		this.player = player;
	}
	
	public void setCamera(Camera camera){
		this.camera = camera;
	}
}

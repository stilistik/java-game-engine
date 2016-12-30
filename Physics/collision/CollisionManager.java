package collision;

import java.util.ArrayList;
import java.util.List;

import component.ComponentType;
import component.PlayerComponent;
import entity.Entity;
import player.Camera;
import terrain.Terrain;

public class CollisionManager {
	
	private static final float CAMERA_TERRAIN_COLLISION_OFFSET = 1;
	
	private Entity player;
	private Camera camera;
	private SweepAndPrune sap;
	
	private List<Entity> dynamicEntities = new ArrayList<Entity>();
	private List<Entity> staticEntities = new ArrayList<Entity>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	
	private int currentTerrainIndex = 0;
	
	public CollisionManager() {
		sap = new SweepAndPrune();
	}
	
	public void update(){
		player.updateComponents();
		camera.move();	
		terrainCollision();
	}
	
	private void terrainCollision(){
		getCurrentTerrain();
		float terrainHeight = terrains.get(currentTerrainIndex).getHeightOfTerrain(player.getPosition().x, player.getPosition().z);
		if (player.getPosition().y < terrainHeight){
			player.getComponent(ComponentType.PLAYER, PlayerComponent.class).setUpwardsSpeed(0);
			player.getPosition().y = terrainHeight;
			player.getComponent(ComponentType.PLAYER, PlayerComponent.class).setInAir(false);
		}
		terrainHeight = terrains.get(currentTerrainIndex).getHeightOfTerrain(camera.getPosition().x, camera.getPosition().z);
		if (camera.getPosition().y < terrainHeight + CAMERA_TERRAIN_COLLISION_OFFSET){
			camera.getPosition().y = terrainHeight + CAMERA_TERRAIN_COLLISION_OFFSET;
		}
	}
	
	private void getCurrentTerrain(){
		for (int i = 0; i < terrains.size(); i++){
			if ((player.getPosition().x > terrains.get(i).getX()) && 
				(player.getPosition().x < terrains.get(i).getX() + Terrain.SIZE) && 
				(player.getPosition().z > terrains.get(i).getZ()) &&
				(player.getPosition().z < terrains.get(i).getZ() + Terrain.SIZE)){
				currentTerrainIndex = i;
			}
		}
	}
	
	public void addStaticEntity(Entity entity){
		staticEntities.add(entity);
	}
	
	public void addTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	
	public void setPlayer(Entity player){
		this.player = player;
		dynamicEntities.add(player);
	}
	
	public void setCamera(Camera camera){
		this.camera = camera;
	}
}

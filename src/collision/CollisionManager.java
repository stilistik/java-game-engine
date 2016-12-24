package collision;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.Player;
import terrain.Terrain;

public class CollisionManager {
	
	private Player player;
	private List<Entity> collisionEntities = new ArrayList<Entity>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	private int currentTerrainIndex = 0;
	
	public CollisionManager(){
		
	}
	
	public void update(){
		// check for collision
		getCurrentTerrain();
		player.move(terrains.get(currentTerrainIndex));
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
	
	public void addEntity(Entity entity){
		collisionEntities.add(entity);
	}
	
	public void addTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
}

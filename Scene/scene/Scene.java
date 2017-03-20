package scene;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import terrain.Terrain;
import water.WaterTile;

public class Scene {

	private Entity player;
	private Terrain terrain;
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> lights = new ArrayList<Entity>();
	private List<WaterTile> waterTiles = new ArrayList<WaterTile>();
	
	public Scene () {}
	
	public void addEntity(Entity entity){
		entities.add(entity);
	}
	
	public void addLight(Entity light){
		lights.add(light);
	}
	
	public void addWaterTile(WaterTile waterTile){
		waterTiles.add(waterTile);
	}
	
	public void setTerrain(Terrain terrain){
		this.terrain = terrain;
	}
	
	public Terrain getTerrain(){
		return terrain;
	}
	
	public Entity getPlayer() {
		return player;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}

	public List<Entity> getEntities(){
		return entities;
	}
	
	public List<Entity> getLights(){
		return lights;
	}
	
	public List<WaterTile> getWaterTiles(){
		return waterTiles;
	}
}

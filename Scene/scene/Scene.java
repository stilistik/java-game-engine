package scene;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import terrain.Terrain;

public class Scene {

	private Terrain terrain;
	private List<Entity> entities = new ArrayList<Entity>();
	
	public Scene () {}
	
	public void addEntity(Entity entity){
		entities.add(entity);
	}
	
	public void setTerrain(Terrain terrain){
		this.terrain = terrain;
	}
	
	public Terrain getTerrain(){
		return terrain;
	}
	
	public List<Entity> getEntities(){
		return entities;
	}
}

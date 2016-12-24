package sceneManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import collision.CollisionManager;
import entities.Camera;
import entities.Entity;
import entities.EntityShader;
import entities.Light;
import entities.Player;
import entities.Sun;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrain.Terrain;

public class SceneManager {
	
	private Player player;
	private Camera camera;
	
	private MasterRenderer renderer;
	private CollisionManager collisionManager;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Light> lights = new ArrayList<Light>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public SceneManager(Loader loader, CollisionManager collisionManager){
		renderer = new MasterRenderer(loader);
		this.collisionManager = collisionManager;
	}
	
	public void update(){
		sortLights();
		sortEntities();
	}
	
	public void renderScene(){
		renderer.prepare();
		for (Entity entity : entities){
			renderer.processEntity(entity);
		}
		for (Terrain terrain : terrains){
			renderer.processTerrain(terrain);
		}
		renderer.render(lights, camera);		
	}
	
	private void sortLights(){
		for (Light light : lights){
			Vector3f v = new Vector3f();
			Vector3f.sub(light.getPosition(), player.getPosition(), v);
			light.setDistanceToPlayer(v.length());
		}
		lights.sort(new LightDistanceSort());	
	}
	
	private void sortEntities(){
		for(Entity entity: entities){
			Vector3f v = new Vector3f();
			Vector3f.sub(entity.getPosition(), player.getPosition(), v);
			entity.setDistanceToPlayer(v.length());
		}
	}
	
	class EntityDistanceSort implements Comparator<Entity>
	{
	    public int compare(Entity e1, Entity e2){
	        Float change1 = Float.valueOf(e1.getDistanceToPlayer());
	        Float change2 = Float.valueOf(e2.getDistanceToPlayer());
	        return change1.compareTo(change2);
	    }
	}
	
	class LightDistanceSort implements Comparator<Light>
	{
	    public int compare(Light l1, Light l2){
	        Float change1 = Float.valueOf(l1.getDistanceToPlayer());
	        Float change2 = Float.valueOf(l2.getDistanceToPlayer());
	        return change1.compareTo(change2);
	    }
	}
	
	public void setPlayer(Player p){
		player = p;
		entities.add(p);
	}
	
	public void setCamera(Camera cam){
		camera = cam;
	}
	
	public void addEntity(Entity entity){
		entities.add(entity);
	}
	
	public void addLight(Light light){
		lights.add(light);
	}
	
	public void addTerrain(Terrain terrain){
		terrains.add(terrain);
	}
}

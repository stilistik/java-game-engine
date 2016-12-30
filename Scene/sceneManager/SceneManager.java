package sceneManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entity.Entity;
import light.Light;
import player.Camera;
import renderer.MasterRenderer;
import terrain.Terrain;

public class SceneManager {
	
	private Entity player;
	private Camera camera;
	
	private MasterRenderer renderer = new MasterRenderer();
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Light> lights = new ArrayList<Light>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public SceneManager(){}
	
	public void update(){
		sortLights();
	}
	
	public void renderScene(){
		renderer.prepare();
		for (Entity entity : entities){
			renderer.processComponentEntity(entity, camera);
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
	
	class LightDistanceSort implements Comparator<Light>
	{
	    public int compare(Light l1, Light l2){
	        Float change1 = Float.valueOf(l1.getDistanceToPlayer());
	        Float change2 = Float.valueOf(l2.getDistanceToPlayer());
	        return change1.compareTo(change2);
	    }
	}
	
	public void setPlayer(Entity p){
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

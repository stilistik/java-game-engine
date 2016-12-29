package sceneManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.DynamicEntity;
import entities.StaticEntity;
import light.Light;
import player.Camera;
import player.Player;
import renderer.MasterRenderer;
import terrain.Terrain;

public class SceneManager {
	
	private Player player;
	private Camera camera;
	
	private MasterRenderer renderer = new MasterRenderer();
	
	private List<StaticEntity> staticEntities = new ArrayList<StaticEntity>();
	private List<DynamicEntity> dynamicEntities = new ArrayList<DynamicEntity>();
	private List<Light> lights = new ArrayList<Light>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public SceneManager(){}
	
	public void update(){
		sortLights();
	}
	
	public void renderScene(){
		renderer.prepare();
		for (StaticEntity entity : staticEntities){
			renderer.processStaticEntity(entity, camera);
		}
		for (DynamicEntity entity : dynamicEntities){
			renderer.processDynamicEntity(entity, camera);
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
	
	public void setPlayer(Player p){
		player = p;
		dynamicEntities.add(p);
	}
	
	public void setCamera(Camera cam){
		camera = cam;
	}
	
	public void addStaticEntity(StaticEntity entity){
		staticEntities.add(entity);
	}
	
	public void addDynamicEntity(DynamicEntity entity){
		dynamicEntities.add(entity);
	}
	
	public void addLight(Light light){
		lights.add(light);
	}
	
	public void addTerrain(Terrain terrain){
		terrains.add(terrain);
	}
}

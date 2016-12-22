package sceneManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.EntityShader;
import entities.Light;
import entities.Player;
import renderEngine.MasterRenderer;
import terrain.Terrain;

public class SceneManager {
	
	Player player;
	Camera camera;
	
	MasterRenderer renderer = new MasterRenderer();
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Light> lights = new ArrayList<Light>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	private int currentTerrainIndex = 0;
	
	public SceneManager(){}
	
	public void update(){
		getCurrentTerrain();
		sortLights();
	}
	
	public void renderScene(){
		player.move(terrains.get(currentTerrainIndex));
		camera.move();
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
		lights.sort(new DistanceSort());		
	}
	
	class DistanceSort implements Comparator<Light>
	{
	    public int compare(Light l1, Light l2){
	        Float change1 = Float.valueOf(l1.getDistanceToPlayer());
	        Float change2 = Float.valueOf(l2.getDistanceToPlayer());
	        return change1.compareTo(change2);
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

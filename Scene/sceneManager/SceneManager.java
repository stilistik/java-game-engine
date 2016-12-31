package sceneManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import component.LightComponent;
import entity.Entity;
import light.Light;
import player.Camera;
import renderer.MasterRenderer;
import scene.Scene;
import terrain.Terrain;

public class SceneManager {
	
	private Entity player;
	private Camera camera;
	
	private MasterRenderer renderer = new MasterRenderer();
	
	private Scene scene;
	
	public SceneManager(){}
	
	public void update(){
		sortLights();
	}
	
	public void renderScene(){
		renderer.prepare();
		for (Entity entity : scene.getEntities()){
			renderer.processEntity(entity, camera);
		}
		renderer.processEntity(player, camera);
		renderer.processTerrain(scene.getTerrain());
		renderer.render(scene.getLights(), camera);		
	}
	
	private void sortLights(){
		for (Entity light : scene.getLights()){
			Vector3f v = new Vector3f();
			Vector3f.sub(light.getPosition(), player.getPosition(), v);
			light.getComponent(LightComponent.class).setDistanceToPlayer(v.length());
		}
		scene.getLights().sort(new LightDistanceSort());	
	}
	
	class LightDistanceSort implements Comparator<Entity>
	{
	    public int compare(Entity light1, Entity light2){
	        Float change1 = Float.valueOf(light1.getComponent(LightComponent.class).getDistanceToPlayer());
	        Float change2 = Float.valueOf(light2.getComponent(LightComponent.class).getDistanceToPlayer());
	        return change1.compareTo(change2);
	    }
	}
	
	public void setPlayer(Entity p){
		player = p;
	}
	
	public void setCamera(Camera cam){
		camera = cam;
	}
	
	public void setScene(Scene scene){
		this.scene = scene;
	}
}

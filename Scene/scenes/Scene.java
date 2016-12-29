package scenes;

import java.util.List;

import entities.StaticEntity;
import light.Light;
import terrain.Terrain;

public abstract class Scene {
	
	protected Terrain terrain;
	protected List<StaticEntity> staticEntities;
	protected List<Light> lights;
	
	protected abstract void createEntities();
	protected abstract void createLights();
	protected abstract void createTerrain();
	
	public abstract List<StaticEntity> getStaticEntities();
	public abstract List<Light> getLights();
	public abstract Terrain getTerrain();

}

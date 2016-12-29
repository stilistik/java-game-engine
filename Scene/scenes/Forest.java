package scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import creator.EntityCreator;
import creator.TerrainCreator;
import data.ResFile;
import entities.StaticEntity;
import light.Light;
import light.Sun;
import terrain.Terrain;

public class Forest extends Scene{
	
	private static final ResFile sceneFile = new ResFile("res/scenes/forest");
	private static final int NUMBER_OF_PINES = 1200;
	
	private int gridX;
	private int gridZ;
	
	Random random = new Random();

	public Forest(int gridX, int gridZ){
		staticEntities = new ArrayList<StaticEntity>();
		lights = new ArrayList<Light>();
		this.gridX = gridX;
		this.gridZ = gridZ;
		createTerrain();
		createEntities();
		createLights();
	}

	@Override
	protected void createEntities() {
		for (int i = 0; i < NUMBER_OF_PINES; i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain.getHeightOfTerrain(x, z);
			float ry = random.nextFloat();
			staticEntities.add(EntityCreator.createStaticEntity(new ResFile(sceneFile, "entities", "pine"), new Vector3f(x,y,z), 0, ry, 0, 1));
		}
	}

	@Override
	protected void createLights() {
		Sun sun = new Sun(new Vector3f(0,1000,0), new Vector3f(1.4f,1,1));
		lights.add(sun);
	}

	@Override
	protected void createTerrain() {
//		TerrainCreator.createTerrainTexture("forest", new ResFile(sceneFile, "terrain"));
//		terrain = TerrainCreator.createTerrain("forest", gridX,	gridX);
	}

	@Override
	public List<StaticEntity> getStaticEntities() {
		return staticEntities;
	}

	@Override
	public List<Light> getLights() {
		return lights;
	}

	@Override
	public Terrain getTerrain() {
		return terrain;
	}
}

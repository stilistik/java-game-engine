package sceneCreator;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import creator.EntityCreator;
import creator.TerrainCreator;
import csvReader.CSVreader;
import entity.Entity;
import fileSystem.ResFile;
import scene.Scene;
import terrain.Terrain;

public class SceneCreator{
		
	public static Scene loadScene(ResFile sceneFile){
		Scene scene = new Scene();
		
		// data
		ResFile dataFile = new ResFile(sceneFile, "entities/entityData.csv");
		List<float[]> data = CSVreader.readFloatCSV(dataFile);
		
		// terrain
		ResFile terrainFile = new ResFile(sceneFile, "terrain");
		Terrain terrain = TerrainCreator.createTerrainRandom(terrainFile, 0, 0);
		scene.setTerrain(terrain);
		
		// entities
		ResFile entityDirectory = new ResFile(sceneFile, "entities");
		List<ResFile> entityFiles = entityDirectory.getSubDirectories();
				
		for (int entityNumber = 0; entityNumber < data.size(); entityNumber++){
			float[] entityData = data.get(entityNumber);
			ResFile entityFile = entityFiles.get(entityNumber);
			Vector3f position = new Vector3f(entityData[2], entityData[3], entityData[4]);
			Vector3f rotation = new Vector3f(entityData[5], entityData[6], entityData[7]);
			float scale = entityData[8];
			int atlasDimension = (int) entityData[9];
			int textureIndex = (int) entityData[10];
			scene.addEntity(EntityCreator.createStaticEntity(entityFile, position, rotation, scale, atlasDimension, textureIndex));
		}
				
		// light
		Entity sun = EntityCreator.createLight(new Vector3f(0,1000,0), new Vector3f(1.4f,1f,1f), new Vector3f(1,0,0));
		scene.addLight(sun);;
		
		return scene;
	}	

}

package sceneCreator;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import creator.EntityCreator;
import creator.TerrainCreator;
import csv.CSVreader;
import csv.CSVwriter;
import entity.Entity;
import file.ResFile;
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
		
//		CSVwriter.randomInitCSV(terrain);
		
		// entities
		ResFile entityDirectory = new ResFile(sceneFile, "entities");
		List<ResFile> entityFiles = entityDirectory.getSubDirectories();
				
		int id = 0;
		for (int entityNumber = 0; entityNumber < data.size(); entityNumber++){
			float[] entityData = data.get(entityNumber);
			int model_id = (int)entityData[1];
			ResFile entityFile = entityFiles.get(model_id);
			id = (int) entityData[0];
			Vector3f position = new Vector3f(entityData[2], entityData[3], entityData[4]);
			Vector3f rotation = new Vector3f(entityData[5], entityData[6], entityData[7]);
			float scale = entityData[8];
			int atlasDimension = (int) entityData[9];
			int textureIndex = (int) entityData[10];
			boolean fakeLight = entityData[11] > 0 ? true : false;
			boolean transparent = entityData[12] > 0 ? true : false;
			scene.addEntity(EntityCreator.createStaticEntity(entityFile, id, position, rotation, scale, atlasDimension, textureIndex, fakeLight, transparent));
			if (entityData.length > 13){
				position.y += entityData[13];
				Vector3f color = new Vector3f(entityData[14], entityData[15], entityData[16]);
				Vector3f attenuation = new Vector3f(entityData[17], entityData[18], entityData[19]);
				scene.addLight(EntityCreator.createLight(model_id, position, color, attenuation));
			}
		}
		
		Entity player = EntityCreator.createPlayer(new ResFile("res/entities/player"), ++id, new Vector3f(400,10,400), new Vector3f(0,0,0), 1);
		scene.setPlayer(player);
		
		return scene;
	}	

}

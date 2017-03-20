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
import water.WaterTile;

public class SceneCreator{
		
	public static Scene loadScene(ResFile sceneFile){
		Scene scene = new Scene();
		
		// terrain
		ResFile terrainFile = new ResFile(sceneFile, "terrain");
		Terrain terrain = TerrainCreator.createTerrainRandom(terrainFile, 0, 0);
		scene.setTerrain(terrain);
		
		// water
		WaterTile waterTile = new WaterTile(400, 400, -10);
		scene.addWaterTile(waterTile);
		
//		CSVwriter.randomInitCSV(terrain);
		
		// data
		ResFile dataFile = new ResFile(sceneFile, "entities/entityData.csv");
		CSVreader reader = null;
		try {
			reader = new CSVreader(dataFile);
			reader.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// entities
		ResFile entityDirectory = new ResFile(sceneFile, "entities");
		List<ResFile> entityFiles = entityDirectory.getSubDirectories();
		
		int id = 0;
		while (reader.nextLine() != null){
			id = reader.nextInt();
			int model_id = reader.nextInt();
			ResFile entityFile = entityFiles.get(model_id);
			Vector3f position = new Vector3f(reader.nextFloat(), reader.nextFloat(), reader.nextFloat());
			Vector3f rotation = new Vector3f(reader.nextFloat(), reader.nextFloat(), reader.nextFloat());
			float scale = reader.nextFloat();
			int atlasDimension = reader.nextInt();
			int textureIndex = reader.nextInt();
			boolean fakeLight = reader.nextBoolean();
			boolean transparent = reader.nextBoolean();
			scene.addEntity(EntityCreator.createStaticEntity(entityFile, id, position, rotation, scale, atlasDimension, textureIndex, fakeLight, transparent));
			if (reader.hasNext()){
				position.y += reader.nextFloat();
				Vector3f color = new Vector3f(reader.nextFloat(), reader.nextFloat(), reader.nextFloat());
				Vector3f attenuation = new Vector3f(reader.nextFloat(), reader.nextFloat(), reader.nextFloat());
				scene.addLight(EntityCreator.createLight(model_id, position, color, attenuation));
			}
		}
		
		Entity player = EntityCreator.createPlayer(new ResFile("res/entities/player"), ++id, new Vector3f(400,10,400), new Vector3f(0,0,0), 1);
		scene.setPlayer(player);
		
		return scene;
	}	

}

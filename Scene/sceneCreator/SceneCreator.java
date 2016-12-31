package sceneCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import creator.EntityCreator;
import creator.TerrainCreator;
import data.ResFile;
import entity.Entity;
import general.Settings;
import scene.Scene;
import terrain.Terrain;

public class SceneCreator{
	
	private static Random random = new Random();
	
	public static Scene loadScene(ResFile sceneFile){
		Scene scene = new Scene();
		
		// data
		ResFile dataFile = new ResFile(sceneFile, "entities/entityDataCSV.csv");
		List<float[]> data = readEntityData(dataFile);
		
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
	
	private static List<float[]> readEntityData(ResFile dataFile){
		ArrayList<float[]> data = new ArrayList<float[]>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Settings.RES_LOC+dataFile.toString()));
			reader.readLine();
			String line;
			while ((line = reader.readLine()) != null){
				String[] lineFragments = line.split(";");
				if (lineFragments.length == 0){
					continue;
				}
				float[] lineData = new float[lineFragments.length];
				for (int i = 0; i < lineData.length; i++){
					lineData[i] = Float.parseFloat(lineFragments[i]);
				}
				data.add(lineData);
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Could not read scene data file.");
			e.printStackTrace();
		}
		return data;
	}
	

}

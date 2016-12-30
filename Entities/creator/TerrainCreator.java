package creator;

import data.ResFile;
import terrain.Terrain;
import terrain.TerrainTexture;

public class TerrainCreator {	
	
	public static Terrain createTerrainFromHeightMap(ResFile terrainFile, String name, int gridX, int gridZ){
		TerrainTexture texture = TerrainTextureLoader.createTerrainTexture(terrainFile);
		TerrainGeneratorHeightMap generator = new TerrainGeneratorHeightMap(terrainFile);
		return new Terrain(gridX, gridZ, generator.getVao(), texture, generator.getHeights());
	}
	
	public static Terrain createTerrainRandom(ResFile terrainFile, String name, int gridX, int gridZ){
		TerrainTexture texture = TerrainTextureLoader.createTerrainTexture(terrainFile);
		TerrainGeneratorRandom generator = new TerrainGeneratorRandom();
		return new Terrain(gridX, gridZ, generator.getVao(), texture, generator.getHeights());
	}
}
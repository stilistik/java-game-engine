package creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import file.ResFile;
import terrain.Terrain;
import terrain.TerrainTexture;
import texture.Texture;

public class TerrainCreator {	
	
	private static Map<String, TerrainTexture> textures = new HashMap<String, TerrainTexture>();
	
	public static Terrain createTerrainFromHeightMap(ResFile terrainFile, int gridX, int gridZ){
		TerrainTexture texture = createTerrainTexture(terrainFile);
		TerrainGeneratorHeightMap generator = new TerrainGeneratorHeightMap(terrainFile);
		return new Terrain(gridX, gridZ, generator.getVao(), texture, generator.getHeights());
	}
	
	public static Terrain createTerrainRandom(ResFile terrainFile, int gridX, int gridZ){
		TerrainTexture texture = createTerrainTexture(terrainFile);
		TerrainGeneratorRandom generator = new TerrainGeneratorRandom();
		return new Terrain(gridX, gridZ, generator.getVao(), texture, generator.getHeights());
	}
	
	private static TerrainTexture createTerrainTexture(ResFile terrainFile){
		TerrainTexture texture = textures.get(terrainFile.toString());
		if (texture == null){
			texture = loadTexture(terrainFile);
			textures.put(terrainFile.toString(), texture);
		}		
		return texture;
	}	
	
	private static TerrainTexture loadTexture(ResFile terrainFile){
		List<Texture> terrainTextures = new ArrayList<Texture>();
		List<Texture> terrainTextureMaps = new ArrayList<Texture>();
		terrainTextures.add(Texture.newTexture(new ResFile(terrainFile, "textures", "texture0.png")).create());
		terrainTextures.add(Texture.newTexture(new ResFile(terrainFile, "textures", "texture1.png")).create());
		terrainTextures.add(Texture.newTexture(new ResFile(terrainFile, "textures", "texture2.png")).create());
		terrainTextures.add(Texture.newTexture(new ResFile(terrainFile, "textures", "texture3.png")).create());
		terrainTextureMaps.add(Texture.newTexture(new ResFile(terrainFile, "maps", "map0.png")).create());
		terrainTextureMaps.add(Texture.newTexture(new ResFile(terrainFile, "maps", "map1.png")).create());
		terrainTextureMaps.add(Texture.newTexture(new ResFile(terrainFile, "maps", "map2.png")).create());
		terrainTextureMaps.add(Texture.newTexture(new ResFile(terrainFile, "maps", "map3.png")).create());	
		return new TerrainTexture(terrainTextures, terrainTextureMaps); 
	}
}
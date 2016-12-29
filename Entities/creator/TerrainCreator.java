package creator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import data.ResFile;
import terrain.Terrain;
import terrain.TerrainData;
import texture.Texture;

public class TerrainCreator {
	
	private static Map<String, TerrainData> texturePacks = new HashMap<String, TerrainData>();
	
	public static void createTerrainData(String name, ResFile terrainFile){
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
		Texture heightMapTexture = Texture.newTexture(new ResFile(terrainFile, "maps", "height.png")).create();
		ResFile heightMapLocation = new ResFile(terrainFile, "maps", "height.png");
		BufferedImage heightMap = null;
		try {
			heightMap = ImageIO.read(heightMapLocation.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		texturePacks.put(name, new TerrainData(terrainTextures, terrainTextureMaps, heightMapTexture, heightMap));
	}
	
	public static Terrain createTerrain(String type, int gridX, int gridZ){
		return new Terrain(gridX, gridZ, texturePacks.get(type));
	}

}

package creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.ResFile;
import terrain.TerrainTexture;
import texture.Texture;

public class TerrainTextureLoader {
	
	private static Map<String, TerrainTexture> textures = new HashMap<String, TerrainTexture>();

	public static TerrainTexture createTerrainTexture(ResFile terrainFile){
		TerrainTexture texture = null;
		if ((texture = textures.get(terrainFile.toString())) != null){
			return texture;
		}		
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
		texture =  new TerrainTexture(terrainTextures, terrainTextureMaps);
		textures.put(terrainFile.toString(), texture);
		return texture;
	}	
	
}

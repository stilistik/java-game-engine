package textures;

import java.util.ArrayList;
import java.util.List;

import terrain.Terrain;

public class TerrainTexturePack {
	
	private static final int MAX_TEXTURES = 8;
	private int nTextures;
	
	private TerrainTexture textures[];
	private TerrainTexture textureMaps[];
	
	public TerrainTexturePack(List<TerrainTexture> t, List<TerrainTexture> tm) {
		nTextures = t.size();
		textures = new TerrainTexture[MAX_TEXTURES];
		textureMaps = new TerrainTexture[MAX_TEXTURES];
		for (int i = 0; i < t.size(); i++){
			textures[i] = t.get(i);
			textureMaps[i] = tm.get(i);
		}
	}
	
	public TerrainTexture getTexture(int idx) {
		return textures[idx];
	}
	
	public TerrainTexture getTextureMap(int idx){
		return textureMaps[idx];
	}
	
	public int getNumberOfTextures(){
		return nTextures;
	}
}

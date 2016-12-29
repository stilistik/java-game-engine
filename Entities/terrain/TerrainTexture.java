package terrain;

import java.awt.image.BufferedImage;
import java.util.List;

import texture.Texture;

public class TerrainTexture {
	
	private static final int MAX_TEXTURES = 8;
	private int nTextures;
	
	private Texture textures[];
	private Texture textureMaps[];
	
	public TerrainTexture(List<Texture> t, List<Texture> tm) {
		nTextures = t.size();
		textures = new Texture[MAX_TEXTURES];
		textureMaps = new Texture[MAX_TEXTURES];
		for (int i = 0; i < t.size(); i++){
			textures[i] = t.get(i);
			textureMaps[i] = tm.get(i);
		}
	}
	
	public Texture getTexture(int idx) {
		return textures[idx];
	}
	
	public Texture getTextureMap(int idx){
		return textureMaps[idx];
	}
	
	public int getNumberOfTextures(){
		return nTextures;
	}
}

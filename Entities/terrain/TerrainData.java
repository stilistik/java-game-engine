package terrain;

import java.awt.image.BufferedImage;
import java.util.List;

import texture.Texture;

public class TerrainData {
	
	private static final int MAX_TEXTURES = 8;
	private int nTextures;
	
	private Texture textures[];
	private Texture textureMaps[];
	private Texture heightMapTexture;
	private BufferedImage heightMap;
	
	public TerrainData(List<Texture> t, List<Texture> tm, Texture heightMapTexture, BufferedImage heightMap) {
		nTextures = t.size();
		textures = new Texture[MAX_TEXTURES];
		textureMaps = new Texture[MAX_TEXTURES];
		for (int i = 0; i < t.size(); i++){
			textures[i] = t.get(i);
			textureMaps[i] = tm.get(i);
		}
		this.heightMapTexture = heightMapTexture;
		this.heightMap = heightMap;
	}
	
	public Texture getTexture(int idx) {
		return textures[idx];
	}
	
	public Texture getTextureMap(int idx){
		return textureMaps[idx];
	}
	
	public void setHeightMapTexture(Texture heightMapTexture){
		this.heightMapTexture = heightMapTexture;
	}
	
	public Texture getHeightMapTexture(){
		return heightMapTexture;
	}
	
	public int getNumberOfTextures(){
		return nTextures;
	}
	
	public BufferedImage getHeightMap(){
		return heightMap;
	}
}

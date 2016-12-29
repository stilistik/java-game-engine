package creator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

import data.ResFile;
import openGL.Vao;
import terrain.Terrain;
import terrain.TerrainTexture;
import texture.Texture;

public class TerrainCreator {
	
	public static final float SIZE = 800;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
	
	
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
package terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolBox.Maths;

public class Terrain {
	
	public static final int MAX_TEXTURES = 4;
	
	private static final float SIZE = 800;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
	
	private float x;
	private float z;
	
	private RawModel model;
	private TerrainTexturePack texturePack;
	
	private BufferedImage heightMap;
	private BufferedImage textureMaps[];
	private TerrainTexture blendTextures[];
	
	private float[][] heights;
	
	public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, String blendMapLocation, String heightMapLocation){
		loadMaps(heightMapLocation, blendMapLocation, loader);
		this.texturePack = texturePack;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader, heightMapLocation);		
	}
	
	private void loadMaps(String heightMapLocation, String blendMapLocation, Loader loader){
		heightMap = null;
		try {
			heightMap = ImageIO.read(new File("res/textures/"+ heightMapLocation +".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		textureMaps = new BufferedImage[MAX_TEXTURES];
		blendTextures = new TerrainTexture[MAX_TEXTURES];
		try {
			for (int i = 0; i < MAX_TEXTURES; i++){
				textureMaps[i] = ImageIO.read(new File("res/textures/" + blendMapLocation + i + ".png"));
				blendTextures[i] = new TerrainTexture(loader.loadTexture(blendMapLocation + i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private RawModel generateTerrain(Loader loader, String heightMapLocation){
		int VERTEX_COUNT = heightMap.getHeight();
		int count = VERTEX_COUNT * VERTEX_COUNT;
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				float height = getHeight(j,i,heightMap);
				heights[j][i] = height;
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i, heightMap);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	private float getHeight(int x, int z, BufferedImage image){
		if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()){
			return 0;
		}
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOUR/2f;
		height /= MAX_PIXEL_COLOUR/2f;
		height *= MAX_HEIGHT;
		return height;
	}
	
	private Vector3f calculateNormal(int x, int z, BufferedImage image){
		float heightL = getHeight(x-1, z, image);
		float heightR = getHeight(x+1, z, image);
		float heightB = getHeight(x, z-1, image);
		float heightT = getHeight(x, z+1, image);
		Vector3f normal = new Vector3f(heightL-heightR, 2f, heightB-heightT);
		normal.normalise();
		return normal;
	}
	
	public float getHeightOfTerrain(float worldX, float worldZ){
		float terrainX = worldX - x;
		float terrainZ = worldZ - z;
		float gridSquareSize = SIZE / (float)(heights.length-1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0){
			return 0;
		}
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if (xCoord <= (1 - zCoord)){
			answer = Maths.baryCentricInterp(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ], 0), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}else{
			answer = Maths.baryCentricInterp(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		return answer;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	public TerrainTexture getBlendTexture(int index) {
		return blendTextures[index];
	}
}

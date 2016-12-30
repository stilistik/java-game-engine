package terrain;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import openGL.Vao;
import tools.Maths;

public class Terrain {
	
	public static final int MAX_TEXTURES = 4;
	
	public static final float SIZE = 800;
	
	private float gridX;
	private float gridZ;
	
	private Vao vao;
	private TerrainTexture terrainData;
	
	private float[][] heights;
	
	public Terrain(int gridX, int gridZ, Vao vao, TerrainTexture terrainData, float[][] heights){
		this.gridX = gridX;
		this.gridZ = gridZ;
		this.vao = vao;
		this.terrainData = terrainData;
		this.heights = heights;
	}
	
	public float getHeightOfTerrain(float worldX, float worldZ){
		float terrainX = worldX - gridX;
		float terrainZ = worldZ - gridZ;
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
		return gridX;
	}

	public float getZ() {
		return gridZ;
	}

	public Vao getVao() {
		return vao;
	}

	public TerrainTexture getTerrainData() {
		return terrainData;
	}
}

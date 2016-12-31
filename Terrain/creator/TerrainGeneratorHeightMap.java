package creator;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

import fileSystem.ResFile;
import openGL.Vao;

public class TerrainGeneratorHeightMap {
	
	public static final float SIZE = 800;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
	
	private Vao vao;
	private float[][] heights;
	
	public TerrainGeneratorHeightMap(ResFile terrainFile){
		BufferedImage heightMap = loadHeightMap(terrainFile);
		int vertexCount = heightMap.getHeight();
		vao = generateVao(heightMap, vertexCount);
	}

	
	public Vao getVao(){
		return vao;
	}
	
	public float[][] getHeights(){
		return heights;
	}
	
	private BufferedImage loadHeightMap(ResFile terrainFile){
		ResFile heightMapFile = new ResFile(terrainFile, "maps", "height.png");
		BufferedImage heightMap = null;
		try {
			heightMap = ImageIO.read(heightMapFile.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return heightMap;
	}
	
	private Vao generateVao(BufferedImage heightMap, int vertexCount){
		int count = vertexCount * vertexCount;
		heights = new float[vertexCount][vertexCount];
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(vertexCount-1)*(vertexCount-1)];
		int vertexPointer = 0;
		for(int i=0;i<vertexCount;i++){
			for(int j=0;j<vertexCount;j++){
				float height = getHeight(j,i,heightMap);
				heights[j][i] = height;
				vertices[vertexPointer*3] = (float)j/((float)vertexCount - 1) * SIZE;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)vertexCount - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i, heightMap);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)vertexCount - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)vertexCount - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<vertexCount-1;gz++){
			for(int gx=0;gx<vertexCount-1;gx++){
				int topLeft = (gz*vertexCount)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*vertexCount)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		Vao vao = Vao.create();
		vao.bind();
		vao.createIndexBuffer(indices);
		vao.createFloatAttribute(0, vertices, 3);
		vao.createFloatAttribute(1, textureCoords, 2);
		vao.createFloatAttribute(2, normals, 3);
		return vao;
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
}

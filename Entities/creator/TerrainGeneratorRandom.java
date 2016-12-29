package creator;

import java.util.Random;
import org.lwjgl.util.vector.Vector3f;

import openGL.Vao;

public class TerrainGeneratorRandom {
	
	private static final float SIZE = 800;
	private static final int VERTEX_COUNT = 128;
	
	private static final float AMPLITUDE = 60f;
	private static final int OCTAVES = 3;
	private static final float ROUGHNESS = 0.1f;
	
	private Random random = new Random();
	private int seed;
	
	private Vao vao;
	private float[][] heights;
	
	
	public TerrainGeneratorRandom(){
		seed = 1000000000;
		vao = generateVao(VERTEX_COUNT);
	}
	
	public Vao getVao(){
		return vao;
	}
	
	public float[][] getHeights(){
		return heights;
	}
	
	private Vao generateVao(int vertexCount){
		int count = vertexCount * vertexCount;
		heights = new float[vertexCount][vertexCount];
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(vertexCount-1)*(vertexCount-1)];
		int vertexPointer = 0;
		for(int i=0;i<vertexCount;i++){
			for(int j=0;j<vertexCount;j++){
				float height = generateHeight(j,i);
				heights[j][i] = height;
				vertices[vertexPointer*3] = (float)j/((float)vertexCount - 1) * SIZE;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)vertexCount - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i);
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
	
	private float generateHeight(int x, int z){
		float total = 0;
		float d = (float) Math.pow(2, OCTAVES-1);
		for (int i = 0; i < OCTAVES; i++){
			float freq = (float) (Math.pow(2, i)) / d;
			float amp = (float) (Math.pow(ROUGHNESS, i)) * AMPLITUDE;
			total += getInterpolatedNoise(x * freq, z * freq) * amp;
		}
		return total;
	}
	
	private float getNoise(int x, int z){
		random.setSeed(x * 49632 + z * 325176 + seed);
		return random.nextFloat() * 2f - 1f;
	}
	
	private float getSmoothNoise(int x, int z){
		float corners = (getNoise(x-1,z-1) + getNoise(x-1,z+1) + getNoise(x+1,z-1) + getNoise(x+1,z+1)) / 16f; 
		float sides = (getNoise(x-1,z) + getNoise(x+1,z) + getNoise(x,z-1) + getNoise(x,z+1)) / 8f; 
		float center = getNoise(x,z) / 4f;
		return corners + sides + center;
	}
	
	private float cosInterp(float a, float b, float blend){
		double theta = blend * Math.PI;
		float f = (float) (1 - Math.cos(theta)) * 0.5f;
		return a * (1-f) + b * f;
	}
	
	private float getInterpolatedNoise(float x, float z){
		int intX = (int) x;
		int intZ = (int) z;
		float fracX = x - intX;
		float fracZ = z - intZ;
		
		float v1 = getSmoothNoise(intX, intZ);
		float v2 = getSmoothNoise(intX + 1, intZ);
		float v3 = getSmoothNoise(intX, intZ + 1);
		float v4 = getSmoothNoise(intX + 1, intZ + 1);
		
		float i1 = cosInterp(v1, v2, fracX);
		float i2 = cosInterp(v3, v4, fracX);
		
		return cosInterp(i1, i2, fracZ);
	}
	
	private Vector3f calculateNormal(int x, int z){
		float heightL = generateHeight(x-1, z);
		float heightR = generateHeight(x+1, z);
		float heightB = generateHeight(x, z-1);
		float heightT = generateHeight(x, z+1);
		Vector3f normal = new Vector3f(heightL-heightR, 2f, heightB-heightT);
		normal.normalise();
		return normal;
	}
}

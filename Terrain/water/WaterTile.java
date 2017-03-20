package water;

public class WaterTile {

	public static final float TILE_SIZE = 30f;
	
	private float height;
	private float x, z;
	
	public WaterTile(float centerX, float centerZ, float height){
		this.height = height;
		this.x = centerX;
		this.z = centerZ;
	}

	public float getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}	
}

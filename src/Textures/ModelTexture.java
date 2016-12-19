package textures;

public class ModelTexture {

	private int textureID;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	private boolean transparency = false;
	private boolean fakeLighting = false;
	
	public ModelTexture(int id){
		this.textureID = id;
	}
	
	public int getID(){
		return textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public boolean isTransparency() {
		return transparency;
	}

	public void setTransparency(boolean transparency) {
		this.transparency = transparency;
	}

	public boolean isFakeLighting() {
		return fakeLighting;
	}

	public void setFakeLighting(boolean fakeLighting) {
		this.fakeLighting = fakeLighting;
	}
}

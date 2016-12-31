package texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fileSystem.ResFile;

public class Texture {

	public final int ID;
	public final int size;
	private final int type;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	private boolean transparency = false;
	private boolean fakeLight = false;
	
	private int atlasDimension = 1;

	protected Texture(int textureId, int size) {
		this.ID = textureId;
		this.size = size;
		this.type = GL11.GL_TEXTURE_2D;
	}

	protected Texture(int textureId, int type, int size) {
		this.ID = textureId;
		this.size = size;
		this.type = type;
	}

	public void bindToUnit(int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(type, ID);
	}

	public void delete() {
		GL11.glDeleteTextures(ID);
	}

	public static TextureBuilder newTexture(ResFile textureFile) {
		return new TextureBuilder(textureFile);
	}

	public static Texture newCubeMap(ResFile[] textureFiles, int size) {
		int cubeMapId = TextureUtils.loadCubeMap(textureFiles);
		return new Texture(cubeMapId, GL13.GL_TEXTURE_CUBE_MAP, size);
	}
	
	public static Texture newEmptyCubeMap(int size) {
		int cubeMapId = TextureUtils.createEmptyCubeMap(size);
		return new Texture(cubeMapId, GL13.GL_TEXTURE_CUBE_MAP, size);
	}

	public boolean isTransparency() {
		return transparency;
	}

	public void setTransparency(boolean transparency) {
		this.transparency = transparency;
	}

	public boolean isFakeLight() {
		return fakeLight;
	}

	public void setFakeLight(boolean fakeLight) {
		this.fakeLight = fakeLight;
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

	public int getAtlasDimension() {
		return atlasDimension;
	}

	public void setAtlasDimension(int atlasDimension) {
		this.atlasDimension = atlasDimension;
	}
}

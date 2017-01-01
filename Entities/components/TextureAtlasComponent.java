package components;

import architecture.Component;
import architecture.ComponentType;
import entity.Entity;

public class TextureAtlasComponent implements Component {
	
	private Entity owner;
	private int textureAtlasDimension;
	private int textureIndex;
	
	public TextureAtlasComponent(Entity owner, int textureAtlasDimension, int textureIndex) {
		this.owner = owner;
		this.textureAtlasDimension = textureAtlasDimension;
		this.textureIndex = textureIndex;
	}

	@Override
	public void update() {}
	
	public float getTextureXOffset(){
		int column = textureIndex % textureAtlasDimension;
		return (float) column / (float) textureAtlasDimension;
	}

	public float getTextureYOffset(){
		int row = textureIndex / textureAtlasDimension;
		return (float) row / (float) textureAtlasDimension;
	}

	public int getTextureAtlasDimension() {
		return textureAtlasDimension;
	}

	public void setTextureAtlasDimension(int textureAtlasDimension) {
		this.textureAtlasDimension = textureAtlasDimension;
	}

	public int getTextureIndex() {
		return textureIndex;
	}

	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.TEXTURE_ATLAS;
	}
}

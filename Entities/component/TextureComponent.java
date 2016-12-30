package component;

import entity.Entity;
import texture.Texture;

public class TextureComponent implements Component{

	private Entity owner;
	private Texture texture;
	
	public TextureComponent(Entity owner, Texture texture) {
		this.owner = owner;
		this.texture = texture;
	}
	
	@Override
	public void update() {
		
	}

	public Texture getTexture(){
		return texture;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.TEXTURE;
	}
	
	

}

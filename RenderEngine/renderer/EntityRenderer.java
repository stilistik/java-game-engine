package renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import component.ModelComponent;
import component.PlayerComponent;
import component.TextureAtlasComponent;
import entity.Entity;
import shader.EntityShader;
import texture.Texture;

public class EntityRenderer {
		
	private EntityShader shader;
	
	public EntityRenderer(EntityShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
	}
	
	public void render(Map<ModelComponent, List<Entity>> entities){
		for (ModelComponent modelComponent : entities.keySet()){
			prepareModel(modelComponent);
			List<Entity> batch = entities.get(modelComponent);
			for (Entity entity : batch){
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, modelComponent.getModel().getVao().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindModel(modelComponent);
		}
	}
	
	private void prepareModel(ModelComponent modelComponent){
		modelComponent.getModel().getVao().bind(0,1,2);
		Texture texture = modelComponent.getModel().getTexture();
		if (texture.isTransparency()){
			MasterRenderer.disableCulling();
		}
		shader.loadFakeLightingVariable(texture.isFakeLight());
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		texture.bindToUnit(0);
	}
	
	private void unbindModel(ModelComponent modelComponent){
		MasterRenderer.enableCulling();
		modelComponent.getModel().getVao().unbind(0,1,2);
	}
	
	private void prepareInstance(Entity entity){
		shader.loadTransformationMatrix(entity.getTransformationMatrix());
		TextureAtlasComponent tac = entity.getComponent(TextureAtlasComponent.class);
		if (tac != null){
			shader.loadAtlasDimension(tac.getTextureAtlasDimension());
			shader.loadAtlasOffsets(tac.getTextureXOffset(), tac.getTextureYOffset());
		}else{
			shader.loadAtlasDimension(1);
			shader.loadAtlasOffsets(0, 0);
		}
	}
}

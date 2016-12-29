package renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.DynamicEntity;
import model.Model;
import shader.DynamicEntityShader;
import texture.Texture;

public class DynamicEntityRenderer {
		
	private DynamicEntityShader shader;
	
	public DynamicEntityRenderer(DynamicEntityShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
	}
	
	public void render(Map<Model, List<DynamicEntity>> entities){
		for (Model model : entities.keySet()){
			prepareModel(model);
			List<DynamicEntity> batch = entities.get(model);
			for (DynamicEntity entity : batch){
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVao().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindModel(model);
		}
	}
	
	private void prepareModel(Model model){
		model.getVao().bind(0,1,2);
		Texture texture = model.getTexture();
		if (texture.isTransparency()){
			MasterRenderer.disableCulling();
		}
		shader.loadAtlasDimension(texture.getAtlasDimension());
		shader.loadFakeLightingVariable(texture.isFakeLight());
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		texture.bindToUnit(0);
	}
	
	private void unbindModel(Model model){
		MasterRenderer.enableCulling();
		model.getVao().unbind(0,1,2);
	}
	
	private void prepareInstance(DynamicEntity entity){
		shader.loadTransformationMatrix(entity.getTransformationMatrix());
		shader.loadAtlasOffsets(entity.getTextureXOffset(), entity.getTextureYOffset());
	}
}

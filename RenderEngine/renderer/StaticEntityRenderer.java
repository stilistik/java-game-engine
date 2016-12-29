package renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.StaticEntity;
import model.Model;
import shader.StaticEntityShader;
import texture.Texture;

public class StaticEntityRenderer {
		
	private StaticEntityShader shader;
	
	public StaticEntityRenderer(StaticEntityShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
	}
	
	public void render(Map<Model, List<StaticEntity>> entities){
		for (Model model : entities.keySet()){
			prepareModel(model);
			List<StaticEntity> batch = entities.get(model);
			for (StaticEntity entity : batch){
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
	
	private void prepareInstance(StaticEntity entity){
		Matrix4f transformationMatrix = entity.getTransformationMatrix();
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadAtlasOffsets(entity.getTextureXOffset(), entity.getTextureYOffset());
	}
}

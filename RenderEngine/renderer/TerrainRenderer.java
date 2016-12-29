package renderer;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import openGL.Vao;
import shader.TerrainShader;
import terrain.Terrain;
import terrain.TerrainData;
import tools.Maths;

public class TerrainRenderer {
	
	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render(List<Terrain> terrains){
		for (Terrain terrain : terrains){
			prepareTerrain(terrain);
			loadModelMatrix(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	private void prepareTerrain(Terrain terrain){
		Vao vao  = terrain.getModel();
		vao.bind(0,1,2);
		bindTextures(terrain);
		shader.loadShineVariables(1, 0);
	}
	
	private void bindTextures(Terrain terrain){
		TerrainData terrainData = terrain.getTexturePack();
		int nTextures = terrainData.getNumberOfTextures();
		shader.loadNumberOfTextures(nTextures);
		int tex_index = 0;		
		for (int i = 0; i < nTextures; i++){
			terrainData.getTexture(i).bindToUnit(tex_index);
			tex_index++;
		}
		for (int i = 0; i < nTextures; i++){
			terrainData.getTextureMap(i).bindToUnit(tex_index);
			tex_index++;
		}
		terrainData.getHeightMapTexture().bindToUnit(tex_index);
	}
	
	private void unbindTexturedModel(){
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Terrain terrain){
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
		shader.loadTransformationMatrix(transformationMatrix);
	}

}

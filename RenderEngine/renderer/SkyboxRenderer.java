package renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import player.Camera;
import shader.SkyboxShader;
import skybox.Skybox;


public class SkyboxRenderer {
	
	private Skybox skybox = new Skybox();
	private SkyboxShader shader;
	
	public SkyboxRenderer(SkyboxShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		this.shader.start();
		this.shader.connectTextureUnits();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();		
	}
	
	public void render(Camera camera, float r, float g, float b){
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadFogColor(r, g, b);
		skybox.getVao().bind(0);
		bindTextures();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, skybox.getVertexCount());
		skybox.getVao().unbind(0);
		shader.stop();
	}
	
	private void bindTextures(){
		skybox.getTexture(0).bindToUnit(0);
		skybox.getTexture(1).bindToUnit(1);
		shader.loadBlendFactor(0);
	}
}

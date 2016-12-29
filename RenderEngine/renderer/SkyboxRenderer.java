package renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import data.ResFile;
import model.Model;
import openGL.Vao;
import player.Camera;
import shader.SkyboxShader;
import texture.Texture;


public class SkyboxRenderer {
	
	private static final int SIZE = 500;
	private static final float[] VERTICES = {        
	    -SIZE,  SIZE, -SIZE,
	    -SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	    -SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE
	};
	
	private static ResFile cloudy = new ResFile("res/skybox/cloudy/texture");
	private static ResFile night = new ResFile("res/skybox/night/texture");

	
	private static ResFile[] CLOUDY_TEXTURE = {new ResFile(cloudy, "right.png"),
											  new ResFile(cloudy, "left.png"),
											  new ResFile(cloudy, "top.png"),
											  new ResFile(cloudy, "bottom.png"),
											  new ResFile(cloudy, "back.png"),
											  new ResFile(cloudy, "front.png")
											  };
	
	private static ResFile[] NIGHT_TEXTURE =  {new ResFile(night, "right.png"),
											   new ResFile(night, "left.png"),
											   new ResFile(night, "top.png"),
											   new ResFile(night, "bottom.png"),
											   new ResFile(night, "back.png"),
											   new ResFile(night, "front.png")
			  								   };
	private Model model;
	private int textureID;
	private int nightTextureID;
	private SkyboxShader shader;
	
	public SkyboxRenderer(SkyboxShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		this.shader.start();
		this.shader.connectTextureUnits();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
		model = createSkybox();
		
	}
	
	private Model createSkybox(){
		Vao vao = Vao.create();
		vao.bind();
		vao.createFloatAttribute(0, VERTICES, 3);
		Texture texture = Texture.newCubeMap(CLOUDY_TEXTURE, SIZE);
		return new Model(vao, texture);
	}
	
	public void render(Camera camera, float r, float g, float b){
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadFogColor(r, g, b);
		model.getVao().bind(0);
		bindTextures();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, VERTICES.length/3);
		model.getVao().unbind(0);
		shader.stop();
	}
	
	private void bindTextures(){
		model.getTexture().bindToUnit(0);
		shader.loadBlendFactor(0);
	}
}

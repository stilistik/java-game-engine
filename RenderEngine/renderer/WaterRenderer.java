package renderer;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import openGL.Vao;
import shader.WaterShader;
import tools.Maths;
import water.WaterTile;

public class WaterRenderer {

	private Vao vao;
	private WaterShader shader;
	
	public WaterRenderer(WaterShader shader){
		this.shader = shader;
		createVao();
	}
	
	public void render(List<WaterTile> waterTiles, Camera camera){
		for (WaterTile tile : waterTiles){
			vao.bind(0);
			Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(tile.getX(), tile.getHeight(), tile.getZ()), new Vector3f(0,0,0), WaterTile.TILE_SIZE);
			shader.loadTransformationMatrix(transformationMatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
			vao.unbind(0);
		}
	}
	
	private void createVao(){
		float vertices[] = { -1, 0, -1, -1, 0, 1, 1, 0, -1, 1, 0, -1, -1, 0, 1, 1, 0, 1 };
		vao = Vao.create();
		vao.bind();
		vao.createFloatAttribute(0, vertices, 3);
		vao.unbind();
	}
}

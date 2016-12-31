package skybox;

import java.util.ArrayList;
import java.util.List;

import file.ResFile;
import openGL.Vao;
import texture.Texture;

public class Skybox {
	
	private static final int SIZE = 500;
	
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
	
	
	private Vao vao;
	private List<Texture> textures;
	
	public Skybox(){
		createVao();
		loadTextures();
	}
	
	private void createVao(){
		vao = Vao.create();
		vao.bind();
		vao.createFloatAttribute(0, getVertices(), 3);
	}
	
	private void loadTextures(){
		textures = new ArrayList<Texture>();
		textures.add(Texture.newCubeMap(CLOUDY_TEXTURE, SIZE));
		textures.add(Texture.newCubeMap(NIGHT_TEXTURE, SIZE));
	}
	
	public Texture getTexture(int index){
		return textures.get(index);
	}
	
	public Vao getVao(){
		return vao;
	}
	
	public int getVertexCount(){
		return getVertices().length/3;
	}
	
	private float[] getVertices(){
		float[] vertices = {        
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
		return vertices;
	}
	
}

package shader;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import components.LightComponent;
import components.TransformationComponent;
import entity.Entity;
import tools.Maths;

public class TerrainShader extends ShaderProgram {

	private static final String VERTEX_FILE = SHADER_LOCATION + "terrainVertexShader.txt";
	private static final String FRAGMENT_FILE = SHADER_LOCATION + "terrainFragmentShader.txt";
	
	private static final int MAX_LIGHTS = 20;
	private static final int MAX_TEXTURES = 4;
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_lightAttenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	private int location_nTextures;
	private int location_textures[];
	private int location_textureMaps[];
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");		
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColor = super.getUniformLocation("skyColor");
		
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColor = new int[MAX_LIGHTS];
		location_lightAttenuation = new int[MAX_LIGHTS];
		for (int i = 0; i < MAX_LIGHTS; i++){
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_lightAttenuation[i] = super.getUniformLocation("lightAttenuation[" + i + "]");
		}
		
		location_nTextures = super.getUniformLocation("nTextures");
		location_textures = new int[MAX_TEXTURES];
		location_textureMaps = new int[MAX_TEXTURES];
		for (int i = 0; i < MAX_TEXTURES; i++){
			location_textures[i] = super.getUniformLocation("textures[" + i + "]");
			location_textureMaps[i] = super.getUniformLocation("textureMaps[" + i + "]");
		}
	}	

	public void connectTextureUnits(){
		for (int i = 0; i < MAX_TEXTURES; i++){
			super.loadInt(location_textures[i], i);
		}
		for (int i = 0; i < MAX_TEXTURES; i++){
			super.loadInt(location_textureMaps[i], MAX_TEXTURES+i);
		}
	}
	
	public void loadNumberOfTextures(int n){
		super.loadInt(location_nTextures, n);
	}
	
	public void loadSkyColor(float r, float g, float b){
		super.loadVector3f(location_skyColor, new Vector3f(r,g,b));
	}
	
	public void loadShineVariables(float damper, float reflectivity){
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadLights(List<Entity> lights){
		for (int i = 0; i < MAX_LIGHTS; i++){
			if (i < lights.size()){
				super.loadVector3f(location_lightPosition[i], lights.get(i).getComponent(TransformationComponent.class).getPosition());
				super.loadVector3f(location_lightColor[i], lights.get(i).getComponent(LightComponent.class).getColor());
				super.loadVector3f(location_lightAttenuation[i], lights.get(i).getComponent(LightComponent.class).getAttenuation());
			}else{
				super.loadVector3f(location_lightPosition[i], new Vector3f(0,0,0));
				super.loadVector3f(location_lightColor[i], new Vector3f(0,0,0));
				super.loadVector3f(location_lightAttenuation[i], new Vector3f(1,0,0));
			}
		}
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
}

package shader;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import light.Light;
import player.Camera;
import tools.Maths;

public class DynamicEntityShader extends ShaderProgram{

	private static final String VERTEX_FILE = SHADER_LOCATION + "entityVertexShader.txt";
	private static final String FRAGMENT_FILE = SHADER_LOCATION + "entityFragmentShader.txt";
	
	public static final int MAX_LIGHTS = 6;
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_lightAttenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLighting;
	private int location_skyColor;
	private int location_atlasDimension;
	private int location_atlasOffsets;
	
	
	public DynamicEntityShader() {
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
		location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColor = super.getUniformLocation("skyColor");
		location_atlasDimension = super.getUniformLocation("atlasDimension");
		location_atlasOffsets = super.getUniformLocation("atlasOffsets");
		
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColor = new int[MAX_LIGHTS];
		location_lightAttenuation = new int [MAX_LIGHTS];
		for (int i = 0; i < MAX_LIGHTS; i++){
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_lightAttenuation[i] = super.getUniformLocation("lightAttenuation[" + i + "]");
		}
	}	
	
	public void loadAtlasDimension(int dimension){
		super.loadFloat(location_atlasDimension, dimension);
	}
	
	public void loadAtlasOffsets(float x, float y){
		super.loadVector2f(location_atlasOffsets, new Vector2f(x, y));
	}
	
	public void loadSkyColor(float r, float g, float b){
		super.loadVector3f(location_skyColor, new Vector3f(r,g,b));
	}
	
	public void loadFakeLightingVariable(boolean useFake){
		super.loadBoolean(location_useFakeLighting, useFake);
	}
	
	public void loadShineVariables(float damper, float reflectivity){
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadLights(List<Light> lights){
		for (int i = 0; i < MAX_LIGHTS; i++){
			if (i < lights.size()){
				super.loadVector3f(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector3f(location_lightColor[i], lights.get(i).getColor());
				super.loadVector3f(location_lightAttenuation[i], lights.get(i).getAttenuation());
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

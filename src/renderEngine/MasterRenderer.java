package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.EntityRenderer;
import entities.EntityShader;
import entities.Light;
import models.TexturedModel;
import skybox.SkyboxRenderer;
import skybox.SkyboxShader;
import terrain.Terrain;
import terrain.TerrainRenderer;
import terrain.TerrainShader;
import tools.Maths;

public class MasterRenderer {
	
	private static final float RED = 0.5f;
	private static final float GREEN =  0.62f; 
	private static final float BLUE = 0.7f;
		
	private Frustum frustum;
	
	private EntityShader entityShader;
	private EntityRenderer entityRenderer;

	private TerrainShader terrainShader;
	private TerrainRenderer terrainRenderer;
	
	private SkyboxRenderer skyboxRenderer;
	private SkyboxShader skyboxShader;
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public MasterRenderer(Loader loader){
		enableCulling();
		frustum = new Frustum();
		entityShader = new EntityShader();
		entityRenderer = new EntityRenderer(entityShader, frustum.getProjectionMatrix());
		terrainShader = new TerrainShader();
		terrainRenderer = new TerrainRenderer(terrainShader, frustum.getProjectionMatrix());
		skyboxShader = new SkyboxShader();
		skyboxRenderer = new SkyboxRenderer(skyboxShader, loader, frustum.getProjectionMatrix());
	}
	
	public void render(List<Light> lights, Camera camera){
		prepare();
		entityShader.start();
		entityShader.loadSkyColor(RED, GREEN, BLUE);
		entityShader.loadLights(lights);
		entityShader.loadViewMatrix(camera);
		entityRenderer.render(entities);
		entityShader.stop();
		terrainShader.start();
		terrainShader.loadSkyColor(RED, GREEN, BLUE);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		skyboxRenderer.render(camera, RED, GREEN, BLUE);
		
		entities.clear();
		terrains.clear();
	}
	
	public void processTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity, Camera camera){
		if (cullFrustum(entity, camera))
			return;
		TexturedModel model = entity.getModel();
		List<Entity> batch = entities.get(model);
		if(batch != null){
			batch.add(entity);
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(model, newBatch);
		}
	}
	
	private boolean cullFrustum(Entity entity, Camera camera){
		Matrix4f mat = Maths.createViewMatrix(camera);
		List<Vector4f> fp_viewSpace = frustum.getFrustumPlanes();	
		
		float xVal[] = entity.getAABB().getXValues();
		float yVal[] = entity.getAABB().getYValues();
		float zVal[] = entity.getAABB().getZValues();
		
		for (Vector4f normal_viewSpace : fp_viewSpace){
			int px = (normal_viewSpace.x > 0) ? 1 : 0;
			int py = (normal_viewSpace.y > 0) ? 1 : 0;
			int pz = (normal_viewSpace.z > 0) ? 1 : 0;
			
			Vector4f v = new Vector4f(xVal[px], yVal[py], zVal[pz], 1);
			Matrix4f.transform(mat, v, v);
			
			float distanceToPlane = normal_viewSpace.x * v.x +
									normal_viewSpace.y * v.y +
									normal_viewSpace.z * v.z;
			
			if (distanceToPlane < -normal_viewSpace.w){
				return true;
			}				
		}
		return false;
	}
	
	public static void enableCulling(){
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling(){
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void prepare(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
	}
	
	public void cleanUp(){
		entityShader.cleanUp();
		terrainShader.cleanUp();
		skyboxShader.cleanUp();
	}
}

package renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import Camera.Camera;
import component.CollisionComponent;
import component.ModelComponent;
import entity.Entity;
import shader.EntityShader;
import shader.SkyboxShader;
import shader.TerrainShader;
import terrain.Terrain;
import tools.Maths;

public class MasterRenderer {
	
	private static final float RED = 0.3f;
	private static final float GREEN =  0.42f; 
	private static final float BLUE = 0.5f;
		
	private Frustum frustum;
	
	private EntityShader entityShader;
	private EntityRenderer entityRenderer;

	private TerrainShader terrainShader;
	private TerrainRenderer terrainRenderer;
	
	private SkyboxRenderer skyboxRenderer;
	private SkyboxShader skyboxShader;
	
	private Map<ModelComponent, List<Entity>> entities = new HashMap<ModelComponent, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public MasterRenderer(){
		enableCulling();
		frustum = new Frustum();	
		entityShader = new EntityShader();
		entityRenderer = new EntityRenderer(entityShader, frustum.getProjectionMatrix());
		terrainShader = new TerrainShader();
		terrainRenderer = new TerrainRenderer(terrainShader, frustum.getProjectionMatrix());
		skyboxShader = new SkyboxShader();
		skyboxRenderer = new SkyboxRenderer(skyboxShader, frustum.getProjectionMatrix());
	}
	
	public void render(List<Entity> lights, Camera camera){
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

		clearMaps();
	}
	
	private void clearMaps(){
		entities.clear();
		terrains.clear();
	}
	
	public void processTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity, Camera camera){
		if (cullFrustum(entity, camera)){
			return;
		}
		ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
		List<Entity> batch = entities.get(modelComponent);
		if(batch != null){
			batch.add(entity);
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(modelComponent, newBatch);
		}
	}
	
	private boolean cullFrustum(Entity entity, Camera camera){
		Matrix4f mat = Maths.createViewMatrix(camera);
		List<Vector4f> fp_viewSpace = frustum.getFrustumPlanes();	
		
		CollisionComponent cc = entity.getComponent(CollisionComponent.class);
		
		float xVal[] = cc.getAABB().getXValues();
		float yVal[] = cc.getAABB().getYValues();
		float zVal[] = cc.getAABB().getZValues();
		
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
		terrainShader.cleanUp();
		skyboxShader.cleanUp();
	}
}

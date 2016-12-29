package renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import entities.DynamicEntity;
import entities.Entity;
import entities.StaticEntity;
import light.Light;
import model.Model;
import player.Camera;
import shader.DynamicEntityShader;
import shader.SkyboxShader;
import shader.StaticEntityShader;
import shader.TerrainShader;
import terrain.Terrain;
import tools.Maths;

public class MasterRenderer {
	
	private static final float RED = 0.5f;
	private static final float GREEN =  0.62f; 
	private static final float BLUE = 0.7f;
		
	private Frustum frustum;
	
	private StaticEntityShader staticEntityShader;
	private StaticEntityRenderer staticEntityRenderer;
	
	private DynamicEntityShader dynamicEntityShader;
	private DynamicEntityRenderer dynamicEntityRenderer;

	private TerrainShader terrainShader;
	private TerrainRenderer terrainRenderer;
	
	private SkyboxRenderer skyboxRenderer;
	private SkyboxShader skyboxShader;
	
	private Map<Model, List<DynamicEntity>> dynamicEntities = new HashMap<Model, List<DynamicEntity>>();
	private Map<Model, List<StaticEntity>> staticEntities = new HashMap<Model, List<StaticEntity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public MasterRenderer(){
		enableCulling();
		frustum = new Frustum();
		staticEntityShader = new StaticEntityShader();
		staticEntityRenderer = new StaticEntityRenderer(staticEntityShader, frustum.getProjectionMatrix());
		dynamicEntityShader = new DynamicEntityShader();
		dynamicEntityRenderer = new DynamicEntityRenderer(dynamicEntityShader, frustum.getProjectionMatrix());		
		terrainShader = new TerrainShader();
		terrainRenderer = new TerrainRenderer(terrainShader, frustum.getProjectionMatrix());
		skyboxShader = new SkyboxShader();
		skyboxRenderer = new SkyboxRenderer(skyboxShader, frustum.getProjectionMatrix());
	}
	
	public void render(List<Light> lights, Camera camera){
		prepare();

		staticEntityShader.start();
		staticEntityShader.loadSkyColor(RED, GREEN, BLUE);
		staticEntityShader.loadLights(lights);
		staticEntityShader.loadViewMatrix(camera);
		staticEntityRenderer.render(staticEntities);
		staticEntityShader.stop();
		
		dynamicEntityShader.start();
		dynamicEntityShader.loadSkyColor(RED, GREEN, BLUE);
		dynamicEntityShader.loadLights(lights);
		dynamicEntityShader.loadViewMatrix(camera);
		dynamicEntityRenderer.render(dynamicEntities);
		dynamicEntityShader.stop();
		
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
		staticEntities.clear();
		dynamicEntities.clear();
		terrains.clear();
	}
	
	public void processTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	
	public void processStaticEntity(StaticEntity entity, Camera camera){
		if (cullFrustum(entity, camera)){
			return;
		}
		Model model = entity.getModel();
		List<StaticEntity> batch = staticEntities.get(model);
		if(batch != null){
			batch.add(entity);
		}else{
			List<StaticEntity> newBatch = new ArrayList<StaticEntity>();
			newBatch.add(entity);
			staticEntities.put(model, newBatch);
		}
	}
	
	public void processDynamicEntity(DynamicEntity entity, Camera camera){
		if (cullFrustum(entity, camera)){
			return;
		}
		Model model = entity.getModel();
		List<DynamicEntity> batch = dynamicEntities.get(model);
		if(batch != null){
			batch.add(entity);
		}else{
			List<DynamicEntity> newBatch = new ArrayList<DynamicEntity>();
			newBatch.add(entity);
			dynamicEntities.put(model, newBatch);
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
		staticEntityShader.cleanUp();
		dynamicEntityShader.cleanUp();
		terrainShader.cleanUp();
		skyboxShader.cleanUp();
	}
}

package renderer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Frustum {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;
	private List<Vector4f> frustumPlanes;
	
	public Frustum(){
		createProjectionMatrix();
		createFrustumPlanes();
	}	
	
	private void createProjectionMatrix(){
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;		
	}
	
	private void createFrustumPlanes(){
		frustumPlanes = new ArrayList<Vector4f>();
		frustumPlanes.add(new Vector4f(-1,0,0,1));
		frustumPlanes.add(new Vector4f(1,0,0,1));
		frustumPlanes.add(new Vector4f(0,0,1,1));
		frustumPlanes.add(new Vector4f(0,0,-1,1));
		frustumPlanes.add(new Vector4f(0,1,0,1));
		frustumPlanes.add(new Vector4f(0,-1,0,1));
		
		Matrix4f mat = new Matrix4f(projectionMatrix);
		mat.invert();
		
		for (Vector4f n : frustumPlanes){
			Matrix4f.transform(mat, n, n);
			normalizeNormals(n);
		}
	}
	
	private void normalizeNormals(Vector4f normal){
		Vector3f n = new Vector3f(normal.x, normal.y, normal.z);
		n.normalise();
		normal.x = n.x;
		normal.y = n.y;
		normal.z = n.z;
	}
	
	public Matrix4f getProjectionMatrix(){
		return projectionMatrix;
	}
	
	public List<Vector4f> getFrustumPlanes(){
		return frustumPlanes;
	}

}

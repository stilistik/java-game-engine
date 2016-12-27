package collision;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import tools.Maths;

public class AABB {

	/**
	 * min and max contain the [xyz] EndPoint - coordinates of the AABB in world space.
	 * vertexPositions contains the Vertices of the AABB in model space. They are transformed
	 * by the entity's transformation matrix to obtain an OBB in worldspace, which is then used
	 * to determine the AABB in world space.
	 */
	
	private EndPoint[] min = new EndPoint[3];
	private EndPoint[] max = new EndPoint[3];
	
	private List<Vector4f> vertexPositions;

	public AABB(float[] modelSpace_AABB, Vector3f position, float rx, float ry, float rz, float scale){
		Matrix4f t = Maths.createTransformationMatrix(position, rx, ry, rz, scale);
		storeModelSpaceVertices(modelSpace_AABB);
		createEndPoints();
		update(t);
	}
	
	private void storeModelSpaceVertices(float[] ms){
		vertexPositions = new ArrayList<Vector4f>();
		vertexPositions.add(new Vector4f(ms[0], ms[2], ms[4], 1));
		vertexPositions.add(new Vector4f(ms[0], ms[2], ms[5], 1));
		vertexPositions.add(new Vector4f(ms[1], ms[2], ms[4], 1));
		vertexPositions.add(new Vector4f(ms[1], ms[2], ms[5], 1));
		vertexPositions.add(new Vector4f(ms[0], ms[3], ms[4], 1));
		vertexPositions.add(new Vector4f(ms[0], ms[3], ms[5], 1));
		vertexPositions.add(new Vector4f(ms[1], ms[3], ms[4], 1));
		vertexPositions.add(new Vector4f(ms[1], ms[3], ms[5], 1));
	}
	
	private void createEndPoints(){
		min[0] = new EndPoint(this, Float.MAX_VALUE, true);
		min[1] = new EndPoint(this, Float.MAX_VALUE, true);
		min[2] = new EndPoint(this, Float.MAX_VALUE, true);
		max[0] = new EndPoint(this, -Float.MAX_VALUE, false);
		max[1] = new EndPoint(this, -Float.MAX_VALUE, false);
		max[2] = new EndPoint(this, -Float.MAX_VALUE, false);
	}
	
	public void update(Matrix4f t){	
		resetEndPointValues();
		for (Vector4f v : vertexPositions){
			Vector4f n = new Vector4f();
			Matrix4f.transform(t, v, n);
			if (n.x < min[0].value)
				min[0].value = n.x;
			if (n.x > max[0].value)
				max[0].value = n.x;
			if (n.y < min[1].value)
				min[1].value = n.y;
			if (n.y > max[1].value)
				max[1].value = n.y;
			if (n.z < min[2].value)
				min[2].value = n.z;
			if (n.z > max[2].value)
				max[2].value = n.z;
		}
	}
	
	private void resetEndPointValues(){
		min[0].value = Float.MAX_VALUE;
		min[1].value = Float.MAX_VALUE;
		min[2].value = Float.MAX_VALUE;
		max[0].value = -Float.MAX_VALUE;
		max[1].value = -Float.MAX_VALUE;
		max[2].value = -Float.MAX_VALUE;
	}
	
	public EndPoint[] getMin(){
		return min;
	}
	
	public EndPoint[] getMax(){
		return max;
	}

	public float[] getXValues(){
		float xVal[] = { min[0].value, max[0].value };
		return xVal;
	}
	
	public float[] getYValues(){
		float yVal[] = { min[1].value, max[1].value };
		return yVal;
	}
	
	public float[] getZValues(){
		float zVal[] = { min[2].value, max[2].value };
		return zVal;
	}
	
}

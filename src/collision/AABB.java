package collision;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import toolBox.Maths;

public class AABB {
	
	private float intervals[] = new float[6];
	private List<Vector4f> vertexPositions;

	public AABB(float[] modelSpace_AABB, Vector3f position, float rx, float ry, float rz, float scale){
		Matrix4f t = Maths.createTransformationMatrix(position, rx, ry, rz, scale);
		storeModelSpaceVertices(modelSpace_AABB);
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
	
	public void update(Matrix4f t){
		intervals[0] =  Float.MAX_VALUE;
		intervals[1] = -Float.MAX_VALUE;
		intervals[2] =  Float.MAX_VALUE;
		intervals[3] = -Float.MAX_VALUE;
		intervals[4] =  Float.MAX_VALUE;
		intervals[5] = -Float.MAX_VALUE;

		for (Vector4f v : vertexPositions){
			Vector4f n = new Vector4f();
			Matrix4f.transform(t, v, n);
			if (n.x < intervals[0])
				intervals[0] = n.x;
			if (n.x > intervals[1])
				intervals[1] = n.x;
			if (n.y < intervals[2])
				intervals[2] = n.x;
			if (n.y > intervals[3])
				intervals[3] = n.x;
			if (n.z < intervals[4])
				intervals[4] = n.x;
			if (n.z > intervals[5])
				intervals[5] = n.x;
		}
	}
	
	public float[] getIntervals(){
		return intervals;
	}
	

}

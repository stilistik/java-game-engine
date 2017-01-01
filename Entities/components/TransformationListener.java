package components;

import org.lwjgl.util.vector.Matrix4f;

public interface TransformationListener {
	
	public void transformationChanged(Matrix4f transformationMatrix);

}

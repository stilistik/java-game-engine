package light;

import org.lwjgl.util.vector.Vector3f;

public class Sun extends Light{

	public Sun(Vector3f position, Vector3f color) {
		super(position, color);
	}
	
	@Override
	public float getDistanceToPlayer(){
		return 0;
	}

}

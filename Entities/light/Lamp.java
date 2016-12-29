package light;

import org.lwjgl.util.vector.Vector3f;

import entities.StaticEntity;
import model.Model;



public class Lamp extends StaticEntity {
	
	private Light light;

	public Lamp(Model model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float[] boundingBox,
				Vector3f lightColor, Vector3f lightAttenuation, float lightOffset) {
		super(model, position, rotX, rotY, rotZ, scale, boundingBox);
		Vector3f lightPosition = new Vector3f(position.x, position.y + lightOffset, position.z);
		light = new Light(lightPosition, lightColor, lightAttenuation);
	}
	
	public Light getLight(){
		return light;
	}

}

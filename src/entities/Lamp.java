package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Lamp extends Entity {
	
	private Light light;

	public Lamp(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
				Vector3f lightColor, Vector3f lightAttenuation, float lightOffset) {
		super(model, position, rotX, rotY, rotZ, scale);
		Vector3f lightPosition = new Vector3f(position.x, position.y + lightOffset, position.z);
		light = new Light(lightPosition, lightColor, lightAttenuation);
	}
	
	public Light getLight(){
		return light;
	}

}

package entities;

import org.lwjgl.util.vector.Vector3f;

import model.Model;

public class StaticEntity extends Entity{

	public StaticEntity(Model model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
			float[] boundingBox) {
		super(model, position, rotX, rotY, rotZ, scale, boundingBox);
	}
}

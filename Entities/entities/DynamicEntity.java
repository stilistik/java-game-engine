package entities;

import org.lwjgl.util.vector.Vector3f;

import interfaces.Moving;
import model.Model;
import tools.Maths;

public class DynamicEntity extends Entity implements Moving{

	public DynamicEntity(Model model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
			float[] boundingBox) {
		super(model, position, rotX, rotY, rotZ, scale, boundingBox);
	}

	@Override
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	@Override
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	@Override
	public void updateTransformation() {
		this.transformationMatrix = Maths.createTransformationMatrix(position, rotX, rotY, rotZ, scale);
	}

}

package interfaces;

public interface Moving {
	
	abstract void increasePosition(float dx, float dy, float dz);
	abstract void increaseRotation(float dx, float dy, float dz);
	abstract void updateTransformation();

}

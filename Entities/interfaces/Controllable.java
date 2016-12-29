package interfaces;

public interface Controllable {
	
	abstract void checkInputs();
	abstract void move();
	abstract void increasePosition(float dx, float dy, float dz);
	abstract void increaseRotation(float dx, float dy, float dz);
	abstract void updateTransformation();

}

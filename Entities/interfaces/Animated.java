package interfaces;

public interface Animated {
	
	abstract void getRootJoint();
	abstract void doAnimation();
	abstract void update();
	abstract void getJointTransforms();
	abstract void addJointsToArray();

}

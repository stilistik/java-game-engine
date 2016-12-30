package component;

import entity.Entity;

public interface Component {
	
	public abstract void update();
	public abstract ComponentType getType();

}

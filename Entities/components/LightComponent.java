package components;

import org.lwjgl.util.vector.Vector3f;

import architecture.Component;
import architecture.ComponentType;
import entity.Entity;

public class LightComponent implements Component {
	
	private Entity entity;
	private Vector3f color;
	private Vector3f attenuation;
	private float distanceToPlayer;
	
	public LightComponent(Entity entity, Vector3f color, Vector3f attenuation){
		this.entity = entity;
		this.color = color; 
		this.attenuation = attenuation;
	}
	
	@Override
	public void update() {}
	
	public Vector3f getAttenuation(){
		return attenuation;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	public void setDistanceToPlayer(float d){
		distanceToPlayer = d;
	}
	
	public float getDistanceToPlayer(){
		return distanceToPlayer;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.LIGHT;
	}

}

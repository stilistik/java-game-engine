package components;

import org.lwjgl.util.vector.Matrix4f;

import architecture.Component;
import architecture.ComponentType;
import collision.AABB;
import entity.Entity;

public class CollisionComponent implements Component {

	private Entity owner;
	private AABB aabb;
	
	public CollisionComponent(Entity owner, float[] boundingBox){
		this.owner = owner;
		this.aabb = new AABB(boundingBox, owner.getComponent(TransformationComponent.class).getTransformationMatrix());
	}
	
	@Override
	public void update() {
		aabb.update(owner.getComponent(TransformationComponent.class).getTransformationMatrix());
	}

	public AABB getAABB(){
		return aabb;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.COLLISION;
	}
}

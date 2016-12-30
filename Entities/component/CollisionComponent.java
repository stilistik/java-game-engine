package component;

import org.lwjgl.util.vector.Vector3f;

import collision.AABB;
import entity.Entity;

public class CollisionComponent implements Component {

	private Entity owner;
	private AABB aabb;
	
	public CollisionComponent(Entity owner, float[] boundingBox){
		this.owner = owner;
		this.aabb = new AABB(boundingBox, owner.getTransformationMatrix());
	}
	
	@Override
	public void update() {
		aabb.update(owner.getTransformationMatrix());
	}

	public AABB getAABB(){
		return aabb;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.COLLISION;
	}
}

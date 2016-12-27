package collision;

public class EndPoint {
	
	public AABB owner;
	public float value;
	public boolean isMin;
	
	public EndPoint(AABB owner, float value, boolean isMin){
		this.owner = owner;
		this.value = value;
		this.isMin = isMin;
	}
}

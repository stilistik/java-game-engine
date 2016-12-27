package collision;

import java.util.List;

public class Box {

	private EndPoint min[] = new EndPoint[3];
	private EndPoint max[] = new EndPoint[3];
	
	public Box(EndPoint[] min, EndPoint[] max){
		this.min = min;
		this.max = max;
	}
}

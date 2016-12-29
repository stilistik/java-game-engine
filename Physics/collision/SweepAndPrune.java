package collision;

import java.util.ArrayList;
import java.util.List;

public class SweepAndPrune {
	
	private List<Box> boxes;
	private List<EndPoint> xEndPoints;
	private List<EndPoint> yEndPoints;
	private List<EndPoint> zEndPoints;
	
	public SweepAndPrune(){
		boxes = new ArrayList<Box>();
		xEndPoints = new ArrayList<EndPoint>();
		yEndPoints = new ArrayList<EndPoint>();
		zEndPoints = new ArrayList<EndPoint>();
	}
	
	public void update(AABB box){
		
	}

}

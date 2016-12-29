package creator;

import java.util.Random;

public class HeightGenerator {
	
	private static final float AMPLITUDE = 70f;
	
	private Random random = new Random();
	private int seed;
	
	public HeightGenerator(){
		this.seed = 10000000;
	}
	
	public float generateHeight(int x, int y){
		return 1;
	}

}

package objects;
import java.awt.Point;

import tools.Forest;

public class Pine extends Forest {
	
	
	private static double probCatchFire = 0.05;
	private static int burningPeriod = 10*3;
	

	public Pine(Point position) {
		super(position, burningPeriod, probCatchFire);
	}
	
}

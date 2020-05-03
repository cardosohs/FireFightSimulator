package objects;
import java.awt.Point;

import tools.Forest;

public class Eucaliptus extends Forest {
	
	
	private static double probCatchFire = 0.1;
	private static int burningPeriod = 6*3;
	

	public Eucaliptus(Point position) {
		super(position, burningPeriod, probCatchFire);
	}
	
}
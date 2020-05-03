package objects;
import java.awt.Point;

import tools.Forest;

public class Grass extends Forest {
	
	
	private static double probCatchFire = 0.2;
	private static int burningPeriod = 3*3;
	

	public Grass(Point position) {
		super(position, burningPeriod, probCatchFire);
	}

}
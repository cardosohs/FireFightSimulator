package pt.iul.poo.firefight.objects;
import java.awt.Point;

import pt.iul.poo.firefight.main.GerarValores;
import pt.iul.poo.firefight.tools.Forest;

public class Eucaliptus extends Forest {
	
	
	private static double probCatchFire = 0.1;
	private static int burningPeriod = 55;
	private static double density = GerarValores.geraDensidade();
	

	public Eucaliptus(Point position) {
		super(position, burningPeriod, probCatchFire, density);
	}
	
}
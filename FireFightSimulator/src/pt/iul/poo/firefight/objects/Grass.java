package pt.iul.poo.firefight.objects;
import java.awt.Point;

import pt.iul.poo.firefight.main.GerarValores;
import pt.iul.poo.firefight.tools.Forest;

public class Grass extends Forest {
	
	
	private static double probCatchFire = 0.2;
	private static int burningPeriod = 35;
	private static double density = GerarValores.geraDensidade();
	

	public Grass(Point position) {
		super(position, burningPeriod, probCatchFire, density);
	}

}
package pt.iul.poo.firefight.objects;
import java.awt.Point;

import pt.iul.poo.firefight.main.GerarValores;
import pt.iul.poo.firefight.tools.Forest;

public class Pine extends Forest {
	
	
	private static double probCatchFire = 0.05;
	private static int burningPeriod = 65;
	private static double density = GerarValores.geraDensidade();
	

	public Pine(Point position) {
		super(position, burningPeriod, probCatchFire, density);
	}
	
}

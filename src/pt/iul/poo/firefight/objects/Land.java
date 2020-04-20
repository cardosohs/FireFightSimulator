package pt.iul.poo.firefight.objects;
import java.awt.Point;

import pt.iul.poo.firefight.tools.FireFightObject;

public class Land extends FireFightObject {

	
	public Land(Point position) {
		super(position);
	}
	
	@Override
	public int getLayer() {
		return 2;
	}
	

}
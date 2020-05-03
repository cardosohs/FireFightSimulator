package objects;
import java.awt.Point;

import tools.FireFightObject;

public class Land extends FireFightObject {

	
	public Land(Point position) {
		super(position);
	}
	
	@Override
	public int getLayer() {
		return 2;
	}
	

}
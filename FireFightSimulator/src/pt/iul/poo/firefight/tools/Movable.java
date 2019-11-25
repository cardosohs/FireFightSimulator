package pt.iul.poo.firefight.tools;

import java.awt.Point;

import pt.iul.poo.firefight.utils.Direction;

public interface Movable {
	
	
	Point moveTo (Direction d);
	
	void move (Direction d);

}

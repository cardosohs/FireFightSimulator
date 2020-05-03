package tools;

import java.awt.Point;

import utils.Direction;

public interface Movable {
	
	
	Point moveTo (Direction d);
	
	void move (Direction d);

}

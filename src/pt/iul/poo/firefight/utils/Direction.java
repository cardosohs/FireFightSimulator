package pt.iul.poo.firefight.utils;

import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 * Cardinal Directions
 * 
 * @author POO2016
 * 
 *
 */
public enum Direction {
	
	
	LEFT(new Point(-1,0)), UP(new Point(0,-1)), RIGHT(new Point(1,0)), DOWN(new Point(0,1));

	
	/**
	 * Direction's 2D Point (x,y)
	 * 
	 */
	private Point vector;

	/**
	 * Creates a new Enum Direction with given 2D Point
	 * 
	 * @param Point 2D Point
	 * 
	 */
	Direction(Point vector) {
		this.vector = vector;
	}
	
	/**
	 * Returns this Enum Direction's 2D Point
	 * @return 2D Point
	 * 
	 */
	public Point asVector() {
		return vector;
	}
	
	/**
	 * Returns the Enum Direction's name matching the pressed key's key code
	 * 
	 * @param keyCode key code from pressed key
	 * @return Direction's name
	 * 
	 */
	public static Direction directionFor(int keyCode) {
		switch(keyCode){
			case KeyEvent.VK_DOWN: 
				return DOWN;
			case KeyEvent.VK_UP:
				return UP;
			case KeyEvent.VK_LEFT:
				return LEFT;
			case KeyEvent.VK_RIGHT:
				return RIGHT;
		}
		throw new IllegalArgumentException();
	}
	

	/**
	 * Asserts if given key code correspond to a direction key
	 * 
	 * @param lastKeyPressed key code from pressed key
	 * @return true if given key code is Integer between 37 and 40, inclusive
	 * 
	 */
	public static boolean isDirection(int lastKeyPressed) {		
		return lastKeyPressed >= KeyEvent.VK_LEFT && lastKeyPressed <= KeyEvent.VK_DOWN;
	}
	
	

	/**
	 * Returns a 2D Point matching the movement from the first to the second given 2D Points
	 * 
	 * @param position Starting 2D Point (x,y)
	 * @param to Ending 2D Point (x,y)
	 * @return a 2D Point
	 * 
	 */
	public static Point movementVector(Point position, Point to) {
		Point d = new Point(position.x - to.x, position.y - to.y);
		if (Math.abs(d.x) > Math.abs(d.y)) {
			d.x = (int) Math.signum(d.x);
			d.y = 0;
		} else if (Math.abs(d.x) <= Math.abs(d.y)) {
			d.y = (int) Math.signum(d.y);
			d.x = 0;
		}
		return d;
	}
	
}

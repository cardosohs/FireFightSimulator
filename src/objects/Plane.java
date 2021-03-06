package objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import gui.ImageMatrixGUI;
import main.FireFightSimulator;
import objects.Burnt;
import objects.Fire;
import objects.Plane;
import tools.FireFightObject;
import tools.FireFightSupport;
import utils.Direction;


public class Plane extends FireFightSupport implements Observer{
	
	
	public static final int planespeed = 2;
	
	
	public Plane(Point position) {
		super(position);
		ImageMatrixGUI.getInstance().addObserver(this);
	}
	
	
	@Override
	public int getLayer() {
		return 6;
	}
	
	
	@Override
	public void move (Direction direction) {
		Point whereIAm = getPosition();
		Point whereIllBe = new Point (whereIAm.x, whereIAm.y - planespeed);
		setPosition(whereIllBe);
		putOutFire(whereIAm, whereIllBe);
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
		int key = (int)arg1;
		
		if (Direction.isDirection(key) && isWithinBoundaries(getPosition())) {
			move(Direction.UP);
				if(!isWithinBoundaries(getPosition())) {
					FireFightSimulator.getInstance().getAllObjects().remove(this);
				}
		}
		FireFightSimulator.getInstance().updateGUI();
	}
	
	
	public static Point startPosition() {
		return new Point (FireFightSimulator.columnWithMostFire(), (int)ImageMatrixGUI.getInstance().getGridDimension().getHeight() - 1);
	}

	
	private void putOutFire(Point here, Point there) {
		List<Fire> toPutOut = burningTilesInBetween(here, there);
		for (Fire f : toPutOut) {
			FireFightSimulator.getInstance().getAllObjects().remove((Fire)f);
			FireFightSimulator.getInstance().getAllObjects().add(new Burnt(new Point(f.getPosition())));
		}
	}
	
	
	private List<Fire> burningTilesInBetween (Point here, Point there) {
		List <Fire> result = new ArrayList<>();
		List <Point> temp = positionsInBetween(here, there);
			for(Point p : temp) {
				FireFightObject obj = FireFightObject.getTileFromMainList(p);
				if(obj instanceof Fire) {
					result.add((Fire)obj);
				}
			}
		return result;
	}
	
	
	private List<Point> positionsInBetween (Point here, Point there){
		List <Point> result = new ArrayList<>();
			for(int i = (int)here.getY(); i >= (int)there.getY(); i--) {
				result.add(new Point((int)here.getX(), i));
			}
		return result;
	}
	
	
	public static Plane getPlaneFromMainList() {
		Plane temp = null;
		for (FireFightObject f :  FireFightSimulator.getInstance().getAllObjects()) {
			if (f instanceof Plane) {
				temp = (Plane)f;
			}
		}
		return temp;
	}
	
}

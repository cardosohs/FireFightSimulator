package pt.iul.poo.firefight.objects;

import java.awt.Point;

import pt.iul.poo.firefight.gui.utils.Direction;
import pt.iul.poo.firefight.main.FireSimulator;
import pt.iul.poo.firefight.objects.Bulldozer;
import pt.iul.poo.firefight.tools.*;


public class Bulldozer extends FireFightSupport {
	

	private Direction nextDirection = Direction.UP;
	private boolean firemanIsInside = false;

	
	public Bulldozer(Point position) {
		super(position);
	}
	
	
	@Override
	public String getName() {
		switch(nextDirection) {
			case LEFT: return "bulldozer_left";
			case RIGHT: return "bulldozer_right";
			case UP: return "bulldozer_up";
			case DOWN: return "bulldozer_down";
		}
		return null;
	}
	
	
	public boolean firemanReadyToGetInside() {
		return Fireman.getFiremanFromMainList().getPosition().equals(getPosition());
	}
	
	
	public void letFiremanIn(boolean b) {
		firemanIsInside = b;
	}
	
	
	public boolean firemanIsInside() {
		return firemanIsInside;
	}
	
	
	@Override
	public void move(Direction direction) {
		nextDirection = direction;
		Point whereIllBe = super.moveTo(direction);
		if (isWithinBoundaries(whereIllBe) && !isPositionOnFire(whereIllBe)) {
			setPosition(whereIllBe);
			takeDownTrees(whereIllBe);	
			cleanTerrain(whereIllBe);
		}
	}
	
			
	private void takeDownTrees(Point p) {
		Forest forest = Forest.getForestFromMainList(p);
		FireSimulator.getInstance().getAllObjects().remove(forest);
		FireSimulator.getInstance().getAllObjects().add(new Land(new Point(p)));
	}
	
	
	private void cleanTerrain(Point p) {
		Burnt burnt = Burnt.getBurntFromMainList(p);
		FireSimulator.getInstance().getAllObjects().remove(burnt);
		FireSimulator.getInstance().getAllObjects().add(new Land(new Point(p)));
	}
	
	
	public static Bulldozer getBulldozerFromMainList() {
		Bulldozer temp = null;
		for (FireFightObject f : FireSimulator.getInstance().getAllObjects()) {
			if (f instanceof Bulldozer) {
				temp = (Bulldozer)f;
			}
		}
		return temp;
	}
	
}

package pt.iul.poo.firefight.tools;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import pt.iul.poo.firefight.gui.ImageMatrixGUI;
import pt.iul.poo.firefight.utils.Direction;
import pt.iul.poo.firefight.objects.Burnt;
import pt.iul.poo.firefight.objects.Fire;


public class FireFightSupport extends FireFightObject implements Movable {
	
	
	protected int layer = 4;
	
	
	public FireFightSupport (Point position) {
		super(position);
	}
	
	@Override
	public int getLayer() {
		return layer;
	}
	
	
	@Override
	public void move(Direction direction) {
		Point whereIllBe = moveTo(direction);
		if (isWithinBoundaries(whereIllBe) && !isPositionOnFire(whereIllBe)) {
			setPosition(whereIllBe);
		}
	}
	
	@Override
	public Point moveTo (Direction direction) {
		Point d = direction.asVector();
		Point whereIAm = getPosition();
			return new Point (whereIAm.x + d.x, whereIAm.y + d.y);
	}

	
	public static boolean isWithinBoundaries(Point p1) {
		Point rectanglePoint = new Point (0,0);
		Dimension rectangleDimension = ImageMatrixGUI.getInstance().getGridDimension();
		Rectangle boundaries = new Rectangle (rectanglePoint, rectangleDimension);
			if (boundaries.contains(p1)) {
				return true;
			}
		return false;
	}
	
	
	public static boolean isPositionOnFire (Point p2) {
		return FireFightObject.getTileFromMainList(p2) instanceof Fire;
	}

	
	public boolean mayOverlap (Point p3) {
		return FireFightObject.getTileFromMainList(p3) instanceof Burnt;
	}
	
}

package pt.iul.poo.firefight.objects;

import java.awt.Point;


import pt.iul.poo.firefight.utils.Direction;
import pt.iul.poo.firefight.main.FireFightSimulator;
import pt.iul.poo.firefight.tools.FireFightObject;
import pt.iul.poo.firefight.tools.FireFightSupport;


public class Fireman extends FireFightSupport {
	
	
	public Fireman(Point position) {
		super(position);
		if(position.getX() < 0 || position.getX() > 10)
			throw new IllegalArgumentException("O valor da posição do Fireman no eixo dos x não é válido!");
		if(position.getY() < 0 || position.getY() > 10)
			throw new IllegalArgumentException("O valor da posição do Fireman no eixo dos y não é válido!");
	}
	
	
	private void setLayer(int newlayer) {
		super.layer = newlayer;
	}
	

	@Override
	public void move(Direction direction) {
		Point whereIllBe = super.moveTo(direction);
		super.move(direction);
			if (mayOverlap(whereIllBe)) {
				super.layer += 2;
			}
				if (isPositionOnFire(whereIllBe)) {
					putOutFire(whereIllBe);
				}
	}
	
	
	private void putOutFire(Point p) {
		Fire fire = Fire.getFireFromMainList(p);
		FireFightSimulator.getInstance().getAllObjects().remove(fire);
	}
	
	
	public static void callPlane() {
		FireFightSimulator.getInstance().getAllObjects().add(new Plane(new Point(Plane.startPosition())));	
	}
	
	public void hideFireman() {
		setLayer(-1);
	}
	
	public void showFireman(Point p) {
		setLayer(5);
		setPosition(p);
	}

	
	public static Fireman getFiremanFromMainList() {
		Fireman temp = null;
		for (FireFightObject f :  FireFightSimulator.getInstance().getAllObjects()) {
			if (f instanceof Fireman) {
				temp = (Fireman)f;
			}
		}
		return temp;
	}
	
}

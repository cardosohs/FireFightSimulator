package tools;

import java.awt.Point;
import java.util.Random;

import main.FireFightSimulator;
import objects.Burnt;
import objects.Fire;


public class Forest extends FireFightObject{
	

	protected double probCatchFire;
	protected int burningPeriod;
	
	
	public Forest (Point position, int burningPeriod, double probCatchFire) {
		super(position);
		this.burningPeriod = burningPeriod;
		this.probCatchFire = probCatchFire;
	}
	
	
	@Override
	public int getLayer() {
		return 3;
	}
	
	
	public double getProbCatchFire() {
		return probCatchFire;
	}
	
	
	public int getBurningPeriod() {
		return burningPeriod;
	}
	
	
	
	public boolean mayCatchFire() {
		Random rdm = new Random();
		Double randomDb = rdm.nextDouble();
		if (probCatchFire > randomDb && !isBurnt() && !isOnFire()) {
			return true;
		}
		return false;
	}
	
	
	public boolean mayGetBurnt() {
		Fire fire = Fire.getFireFromMainList(getPosition());
			if(getBurningPeriod() <= fire.getCounter()) {
				return true;
			}
		return false;
	}
	
	
	public boolean isBurnt() {
		return FireFightObject.getTileFromMainList(getPosition()) instanceof Burnt;
	}
	
	
	public boolean isOnFire() {	
		return FireFightObject.getTileFromMainList(getPosition()) instanceof Fire;
	}
	
	

	public static Forest getForestFromMainList(Point position) {
		Forest temp = null;
		for (FireFightObject f : FireFightSimulator.getInstance().getAllObjects()) {
			if (f.getPosition().equals(position)) {
				if (f instanceof Forest)
					temp = (Forest)f;
			}
		}
		return temp;
	}
	
}

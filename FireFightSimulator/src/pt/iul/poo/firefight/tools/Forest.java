package pt.iul.poo.firefight.tools;

import java.awt.Point;
import java.util.Random;
import pt.iul.poo.firefight.main.FireSimulator;
import pt.iul.poo.firefight.objects.Burnt;
import pt.iul.poo.firefight.objects.Fire;


public class Forest extends FireFightObject{
	

	protected double probCatchFire;
	protected int burningPeriod;
	protected double density;
	
	
	

	public Forest (Point position, int burningPeriod, double probCatchFire, double density) {
		super(position);
		this.burningPeriod = burningPeriod;
		this.probCatchFire = probCatchFire;
		this.density = density;
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
	
	
	//TRATAR DAS ALEATORIAS
	public boolean mayCatchFire() {
		Random rdm = new Random();
		Double randomDb = rdm.nextDouble();
//		FireSimulator.temperaturaAtual
//		FireSimulator.humidadeAtual
		
		if ((probCatchFire*(1/FireSimulator.humidadeAtual)) > randomDb && !isBurnt() && !isOnFire()) {
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
		for (FireFightObject f : FireSimulator.getInstance().getAllObjects()) {
			if (f.getPosition().equals(position)) {
				if (f instanceof Forest)
					temp = (Forest)f;
			}
		}
		return temp;
	}
	
	public double getDensity() {
		return density;
	}


	public void setDensity(double density) {
		this.density = density;
	}

}

package objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import main.FireFightSimulator;
import tools.FireFightObject;
import tools.Forest;


public class Burnt extends FireFightObject {

	
	public Burnt(Point position) {
		super(position);
	}
	
	
	@Override
	public int getLayer() {
		return 5;
	}
	
	
	public static void consumeAll() {
		List<FireFightObject> temp = new ArrayList<FireFightObject>(FireFightSimulator.getInstance().getAllObjects());
		for (FireFightObject f : temp) {
			if (f instanceof Forest) {
				if(((Forest)f).isOnFire() && ((Forest)f).mayGetBurnt()){
					Fire fire = Fire.getFireFromMainList(f.getPosition());
					FireFightSimulator.getInstance().getAllObjects().remove(fire);
					FireFightSimulator.getInstance().getAllObjects().add(new Burnt(new Point(f.getPosition())));
				}
			}
		}
	}
	
	
	public static Burnt getBurntFromMainList(Point position) {
		Burnt temp = null;
		for (FireFightObject f : FireFightSimulator.getInstance().getAllObjects()) {
			if (f.getPosition().equals(position)) {
				if (f instanceof Burnt)
					temp = (Burnt)f;
			}
		}
		return temp;
	}

	
	
}

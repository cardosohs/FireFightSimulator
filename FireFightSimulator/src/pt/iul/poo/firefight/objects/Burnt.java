package pt.iul.poo.firefight.objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import pt.iul.poo.firefight.main.FireSimulator;
import pt.iul.poo.firefight.tools.FireFightObject;
import pt.iul.poo.firefight.tools.Forest;


public class Burnt extends FireFightObject {

	
	public Burnt(Point position) {
		super(position);
	}
	
	
	@Override
	public int getLayer() {
		return 5;
	}
	
	
	public static void consumeAll() {
		List<FireFightObject> temp = new ArrayList<FireFightObject>(FireSimulator.getInstance().getAllObjects());
		for (FireFightObject f : temp) {
			if (f instanceof Forest) {
				if(((Forest)f).isOnFire() && ((Forest)f).mayGetBurnt()){
					Fire fire = Fire.getFireFromMainList(f.getPosition());
					FireSimulator.getInstance().getAllObjects().remove(fire);
					FireSimulator.getInstance().getAllObjects().add(new Burnt(new Point(f.getPosition())));
				}
			}
		}
	}
	
	
	public static Burnt getBurntFromMainList(Point position) {
		Burnt temp = null;
		for (FireFightObject f : FireSimulator.getInstance().getAllObjects()) {
			if (f.getPosition().equals(position)) {
				if (f instanceof Burnt)
					temp = (Burnt)f;
			}
		}
		return temp;
	}

	
	
}

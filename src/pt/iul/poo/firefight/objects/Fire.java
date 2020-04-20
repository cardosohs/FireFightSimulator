package pt.iul.poo.firefight.objects;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import pt.iul.poo.firefight.main.FireFightSimulator;
import pt.iul.poo.firefight.tools.FireFightObject;
import pt.iul.poo.firefight.tools.Forest;

public class Fire extends FireFightObject {
	
	
	private int counter = 0;
	
	
	public Fire (Point position) {
		super(position);
	}
	
	
	@Override
	public int getLayer() {
		return 4;
	}
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
	public static void spreadAllFires() {
		List<FireFightObject> temp = new ArrayList<FireFightObject>(FireFightSimulator.getInstance().getAllObjects());
		for (FireFightObject f : temp) {
			if (f instanceof Fire) {
				 ((Fire)f).spread();
			}
		}
	}
	
	
	private void spread() {
		List <Point> pontos = this.getSurroundingTilesPosition();
		for (Point p : pontos) {
			FireFightObject obj = FireFightObject.getTileFromMainList(p);
			if(obj instanceof Forest) {
				if(((Forest)obj).mayCatchFire()){
					FireFightSimulator.getInstance().getAllObjects().add(new Fire (new Point (obj.getPosition())));
				}
			}
		}
	}
	
	
	public static void incrementCounter(){
		for (FireFightObject f : FireFightSimulator.getInstance().getAllObjects())
			if (f instanceof Fire)
				((Fire)f).setCounter(((Fire)f).getCounter() + 1);
	}
	
	
	public static Fire getFireFromMainList(Point position) {
		Fire temp = null;
		for (FireFightObject f : FireFightSimulator.getInstance().getAllObjects()) {
			if (f.getPosition().equals(position)) {
				if (f instanceof Fire)
					temp = (Fire)f;
			}
		}
		return temp;
	}
	
}

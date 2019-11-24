package pt.iul.poo.firefight.objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import pt.iul.poo.firefight.main.FireSimulator;
import pt.iul.poo.firefight.tools.FireFightObject;
import pt.iul.poo.firefight.tools.Forest;

public class Fire extends FireFightObject {

	private double counter = 0;

	public Fire(Point position) {
		super(position);
	}

	@Override
	public int getLayer() {
		return 4;
	}

	public double getCounter() {
		return counter;
	}

	public void setCounter(double counter) {
		this.counter = counter;
	}

	public static void spreadAllFires() {
		List<FireFightObject> temp = new ArrayList<FireFightObject>(FireSimulator.getInstance().getAllObjects());
		for (FireFightObject f : temp) {
			if (f instanceof Fire) {
				((Fire) f).spread();
			}
		}
	}

	// ADICIONAR ALEATORIAS
	private void spread() {
//		List <Point> pontos = this.getSurroundingTilesPosition();
//		//List<Point> dirVento = this.florestaDirVento();
//		for (Point p : pontos) {
		FireFightObject obj = FireFightObject.getTileFromMainList(florestaDirVento());
		if (obj instanceof Forest) {
			if (((Forest) obj).mayCatchFire()) {
				FireSimulator.getInstance().getAllObjects().add(new Fire(new Point(obj.getPosition())));
			}
		}
	}

	private Point florestaDirVento() {
		// verfica qual destes é que está na direcção do vento
		Point coordenadaAfetada = getCoordenadaDirVento();
		Point posicao = getPosition();
		return new Point(coordenadaAfetada.x + posicao.x, coordenadaAfetada.y + posicao.y);

	}

	private Point getCoordenadaDirVento() {
		int vento = FireSimulator.ventoAtual;
		switch (vento) {
		case 0:
			Point norte = new Point(0, -1);
			return norte;
		case 1:
			Point este = new Point(1, 0);
			return este;
		case 2:
			Point sul = new Point(0, 1);
			return sul;
		case 3:
			Point oeste = new Point(-1, 0);
			return oeste;
		}
		return null;

	}

	public static void incrementCounter() {
		for (FireFightObject f : FireSimulator.getInstance().getAllObjects())
			if (f instanceof Fire) {
				((Fire) f).setCounter(((Fire) f).getCounter() + (FireSimulator.temperaturaAtual/44));
				//System.out.println(((Fire) f).getPosition() + "tem counter a "+ ((Fire) f).getCounter() + "variou "+(FireSimulator.temperaturaAtual/44)+" graus");
			}
	}

	public static Fire getFireFromMainList(Point position) {
		Fire temp = null;
		for (FireFightObject f : FireSimulator.getInstance().getAllObjects()) {
			if (f.getPosition().equals(position)) {
				if (f instanceof Fire)
					temp = (Fire) f;
			}
		}
		return temp;
	}

}

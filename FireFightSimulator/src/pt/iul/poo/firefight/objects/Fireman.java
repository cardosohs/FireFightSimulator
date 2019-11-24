package pt.iul.poo.firefight.objects;

import java.awt.Point;

import pt.iul.poo.firefight.gui.utils.Direction;
import pt.iul.poo.firefight.main.FireSimulator;
import pt.iul.poo.firefight.main.GerarValores;
import pt.iul.poo.firefight.tools.FireFightObject;
import pt.iul.poo.firefight.tools.FireFightSupport;
import pt.iul.poo.firefight.tools.Forest;


public class Fireman extends FireFightSupport {
	
	public static double waterPower=0;
	
	public Fireman(Point position) {
		super(position);
		/*alterado
		if(position.getX() < 0 || position.getX() > 10)
			throw new IllegalArgumentException("O valor da posição do Fireman no eixo dos x não é válido!");
		if(position.getY() < 0 || position.getY() > 10)
			throw new IllegalArgumentException("O valor da posição do Fireman no eixo dos y não é válido!");
		*/
		if(position.getX() < 0 || position.getX() > 15)
			throw new IllegalArgumentException("O valor da posição do Fireman no eixo dos x não é válido!");
		if(position.getY() < 0 || position.getY() > 15)
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
					setWaterPower();
					if(Forest.getForestFromMainList(whereIllBe).getDensity()<=0)
						putOutFire(whereIllBe);
					Forest.getForestFromMainList(whereIllBe).setDensity(Forest.getForestFromMainList(whereIllBe).getDensity()-getWaterPower());
				}
	}
	
	
	private void putOutFire(Point p) {
		
		Fire fire = Fire.getFireFromMainList(p);
		FireSimulator.getInstance().getAllObjects().remove(fire);
		
		//alteração para terminar o jogo... é somente uma prova de conceito e deverá ser melhorado
		int count = 0;
		for(FireFightObject obj : FireSimulator.getInstance().getAllObjects()) {
			if(obj instanceof Fire) {
				count+=1;
			}
		}
		if(count == 0) {
			System.out.println("ganhou");
			System.exit(0);
		}	
			
			
	}
	
	
	public static void callPlane() {
		FireSimulator.getInstance().getAllObjects().add(new Plane(new Point(Plane.startPosition())));	
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
		for (FireFightObject f :  FireSimulator.getInstance().getAllObjects()) {
			if (f instanceof Fireman) {
				temp = (Fireman)f;
			}
		}
		return temp;
	}


	public static double getWaterPower() {
		return waterPower;
	}


	public void setWaterPower() {
		this.waterPower = GerarValores.geraDensidade();
	}
	
}

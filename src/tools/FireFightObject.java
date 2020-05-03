package tools;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import gui.ImageMatrixGUI;
import gui.ImageTile;
import main.FireFightSimulator;
import objects.Bulldozer;
import objects.Eucaliptus;
import objects.Fire;
import objects.Fireman;
import objects.Grass;
import objects.Land;
import objects.Pine;
import utils.Direction;


public class FireFightObject implements ImageTile{
	
	
	private List<Point> surroundingTilesPosition = new ArrayList<>();
	private Point position;
	
	
	public FireFightObject(Point position) {
		this.position = position;
		setSurroundingTilesPosition();
	}
	
	
	@Override
	public String getName() {
		return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	protected void setPosition(Point point) {
		position = point;
	}	
	
	public List<Point> getSurroundingTilesPosition() {
		return surroundingTilesPosition;
	}
	
	
	
	public void setSurroundingTilesPosition () {
		int x = -1;
		int y = -1;
		Dimension dimension = ImageMatrixGUI.getInstance().getGridDimension();
		for (Direction d : Direction.values()) {
			x = (int) Math.max(Math.min((int)this.position.getX() + (int)d.asVector().getX(), dimension.getWidth()),0);
			y = (int) Math.max(Math.min((int)this.position.getY() + (int)d.asVector().getY(), dimension.getWidth()),0);
			surroundingTilesPosition.add(new Point(x,y));
		}
	}

	
	//Factory
	public static FireFightObject charToObject (int x, int y, char c) {
		switch (c) {
			case 'p': return new Pine(new Point (x,y));
			case 'e': return new Eucaliptus(new Point (x,y));
			case '.': return new Land(new Point (x,y));
			case '_': return new Grass(new Point (x,y));
		}
		return null;
	}
			
	
	//Factory
	public static FireFightObject stringToObject (int x, int y, String n){
		switch (n) {
			case "Bulldozer": return new Bulldozer(new Point (x,y));
			case "Fireman": return new Fireman(new Point (x,y));
			case "Fire": return new Fire(new Point (x,y));
		}
		return null;
	}
	
	
	
	public static FireFightObject getTileFromMainList (Point position) {
		FireFightObject temp = null;
		for (FireFightObject f : FireFightSimulator.getInstance().getAllObjects()) {
			if (f.getPosition().equals(position)) {
				temp = f;
			}
		}
		return temp;
	}
	
}

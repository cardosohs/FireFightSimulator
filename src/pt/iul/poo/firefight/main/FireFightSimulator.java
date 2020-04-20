package pt.iul.poo.firefight.main;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import pt.iul.poo.firefight.gui.ImageMatrixGUI;
import pt.iul.poo.firefight.gui.ImageTile;
import pt.iul.poo.firefight.utils.Direction;
import pt.iul.poo.firefight.objects.*;
import pt.iul.poo.firefight.tools.FireFightObject;


public class FireFightSimulator implements Observer {
	

	private static final String CONFIG_DIR = "levels";
	private static final String CONFIG_FILE = "landscape.txt";
	private List<FireFightObject> allObjects = new ArrayList<>();
	private Set<Point> allPositions = new HashSet<>();
	private Vector<Set<Point>> allColumns = new Stack<>();
	private static FireFightSimulator INSTANCE = null;

	
	private FireFightSimulator() { 
		try {
			readMap(CONFIG_DIR + "/" + CONFIG_FILE);
			storeAllPositions();
			createColumns();
			ImageMatrixGUI.getInstance().addObserver(this);
			ImageMatrixGUI.getInstance().go();
			updateGUI();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static FireFightSimulator getInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new FireFightSimulator();
		}
		return INSTANCE;
	}

	
	public List<FireFightObject> getAllObjects(){
		return allObjects;
	}
	
	public Set<Point> getAllPositions(){
		return allPositions;
	}
	
	public Vector<Set<Point>> getAllColumns(){
		return allColumns;
	}
	
	
	private void readMap(String fName) throws FileNotFoundException {
		
		Scanner sc = new Scanner (new File (fName));
		
		List<String> names = new ArrayList<>();
			
			int countLines = 0;
			String cName = "";
			int x = -1;
			int y = -1;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
					if(line.charAt(0) != '#') {
						for(int i = 0; i != line.length(); i++) {
							allObjects.add(FireFightObject.charToObject(i, countLines, line.charAt(i)));
						}
					}else{
						String [] temp = line.split(" ");
						cName = temp[1];
						names.add(cName);
						x = Integer.parseInt(temp[2]);
						y = Integer.parseInt(temp[3]);
						allObjects.add(FireFightObject.stringToObject(x, y, cName));
					}
				countLines++;
			}
		sc.close();
		
		try {
		
			checkIfFiremanIsOnMap(names);
		
		} catch (IllegalStateException f) {
			System.out.println("O Fireman não foi inicializado! Será criado um Fireman na posição (5,3)");
			allObjects.add(new Fireman(new Point(5,3)));
		}
	}
	
	
	private void checkIfFiremanIsOnMap(List<String> names) {
		if(!names.contains("Fireman")) {
			throw new IllegalStateException();
		}
	}
	

	@Override
	public void update(Observable arg0, Object arg1) {
	
			Fireman fireman = Fireman.getFiremanFromMainList();
			Bulldozer bulldozer = Bulldozer.getBulldozerFromMainList();
		
			int key = (int)arg1;
			
			if (Direction.isDirection(key) && !bulldozer.firemanIsInside()) {
				fireman.move(Direction.directionFor(key));
					if (bulldozer.firemanReadyToGetInside()) {
						fireman.hideFireman();
						bulldozer.letFiremanIn(true);
					}
			}
			
			if (Direction.isDirection(key) && bulldozer.firemanIsInside()) {
				bulldozer.move(Direction.directionFor(key));
			}
			
			if (key == KeyEvent.VK_ENTER && bulldozer.firemanIsInside()) {
				fireman.showFireman(bulldozer.getPosition());
				bulldozer.letFiremanIn(false);
			}
			
			if (key == KeyEvent.VK_A) {
				Fireman.callPlane();
			}
			
		Fire.spreadAllFires();
		Fire.incrementCounter();
		Burnt.consumeAll();
		updateGUI();
	}
	
	
	private List<ImageTile> convertList (List<FireFightObject> list){
		List<ImageTile> result = new ArrayList<>();
			for(FireFightObject f : list) {
				if (f instanceof ImageTile) {
					result.add((ImageTile)f);
				}
			}
		return result;
	}
	

	public void updateGUI() {
		ImageMatrixGUI.getInstance().clearImages();
		ImageMatrixGUI.getInstance().addImages(convertList(allObjects));
		ImageMatrixGUI.getInstance().update();
	}

	
	private void storeAllPositions () {
		for(FireFightObject f : allObjects) {
			allPositions.add(f.getPosition());
		}
	}


	private void createColumns () {
		int counter = 0;
		while(counter < 10) {
		Set<Point> temp = new HashSet<>();
			for(Point p : allPositions) {
				if(p.getX() == counter) {
					temp.add(p);
				}
			}
			allColumns.add(counter, temp);
			counter++;
		}
	}
	
	
	public static int countFireInColumnX (int x) {
		int counter = 0;
		Set<Point> temp = FireFightSimulator.getInstance().getAllColumns().get(x);
		for(Point p : temp) {
			if(FireFightObject.getTileFromMainList(p) instanceof Fire) {
				counter++;
			}
		}
		return counter;
	}
	
	
	public static int columnWithMostFire () {
		int numFire = 0;
		int biggest = 0;
		int counter = 0;
		while(counter < 10) {
			if(countFireInColumnX(counter) > numFire) {
				numFire = countFireInColumnX(counter);
				biggest = counter;
			}
			counter++;
		}
		return biggest;
	}
	
	
}

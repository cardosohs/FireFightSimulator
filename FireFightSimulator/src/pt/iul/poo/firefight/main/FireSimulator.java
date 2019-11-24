package pt.iul.poo.firefight.main;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
import pt.iul.poo.firefight.gui.utils.Direction;
import pt.iul.poo.firefight.objects.*;
import pt.iul.poo.firefight.tools.FireFightObject;
import pt.iul.poo.firefight.tools.Forest;
import pt.iul.poo.firefight.main.GerarValores;

//u
public class FireSimulator implements Observer {
	
	private final int GUIWIDTH = ImageMatrixGUI.getInstance().getN_SQUARES_WIDTH();
	private final int GUIHEIGHT = ImageMatrixGUI.getInstance().getN_SQUARES_HEIGHT();

	private static final String CONFIG_DIR = "levels";
	private static final String CONFIG_FILE = "landscape.txt";
	private List<FireFightObject> allObjects = new ArrayList<>();
	private Set<Point> allPositions = new HashSet<>();
	private Vector<Set<Point>> allColumns = new Stack<>();
	private static FireSimulator INSTANCE = null;
	private Point posicaoInicial = new Point(0, 0);
	private Point posicaoSeguinte = new Point(0, 0);
	public static double temperaturaAtual = 44.0;
	public static double humidadeAtual = 0.5;
	public static int ventoAtual = 0;
	
	
	public static FireSimulator getInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new FireSimulator();
		}
		return INSTANCE;
	}
	
	private FireSimulator() { 
		try {
			gameBar();
			writeMap(CONFIG_DIR + "/" + CONFIG_FILE);
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
	
	
	private void writeMap(String fName) {
		int flag = 0;
		int veg = 0;
		String str = "";
		for(int x = 0; x < GUIHEIGHT ; x++) {
			for(int y = 0; y < GUIWIDTH; y++) {
				veg=GerarValores.distibuicao();
				switch (veg) {
	            case 0:  
	            	str = str.concat(".");
	            	if(flag == 0) {
	            		posicaoInicial.setLocation(y, x);
	            	}if(flag==1) {
	            		posicaoSeguinte.setLocation(y, x);
	            	}
	            	flag++;
	                break;
	            case 1:
	            	str = str.concat("_");
	                break;
	            case 2:
	            	str = str.concat("e");
	                break;
	            case 3:  
	            	str = str.concat("p");
				}	
			}
			try {
				PrintWriter out = new PrintWriter(fName);
				out.println(str);
				out.close();
			} catch (FileNotFoundException e) {
				System.out.println("Erro ao criar o mapa");
			}
			str = str.concat(System.lineSeparator());
		}
		
		
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
			//String cName = "";
//			int x = -1;
//			int y = -1;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
					if(line.charAt(0) != '#') {
						for(int i = 0; i != line.length(); i++) {
							allObjects.add(FireFightObject.charToObject(i, countLines, line.charAt(i)));
						}
					}
				countLines++;
			}
			createRandomFires(2);
		sc.close();
		
		try {
		
			checkIfFiremanIsOnMap(names);
		
		} catch (IllegalStateException f) {
			allObjects.add(new Fireman(posicaoInicial));
			allObjects.add(new Bulldozer(posicaoSeguinte));
		}
		
	}
	
	//criar random fires código mais linha no ler ficheiro
	public void createRandomFires(int n) {
		
		List<Point> psss = new ArrayList<>();
		for(FireFightObject obj : getAllObjects()) {
			if(obj instanceof Forest) { 
				psss.add(obj.getPosition());
			}	
		}
		
		Collections.shuffle(psss);
		List<Point> burnVictim = psss.subList(0, n);
		//System.out.println(burnVictim.toString());
		for(Point p : burnVictim) {			
			allObjects.add(new Fire(p));
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
		atualizarCondicoes();
		Fire.spreadAllFires();
		Fire.incrementCounter();
		Burnt.consumeAll();
		updateGUI();
	}
	
	
	private void atualizarCondicoes() {
		FireSimulator.ventoAtual = GerarValores.geraVento();
		FireSimulator.humidadeAtual = GerarValores.geraHumidade(0.2, 0.8, humidadeAtual);
		FireSimulator.temperaturaAtual = temperaturaAtual+GerarValores.normal(0,2);
		temperaturaAtual= (temperaturaAtual>50)?50:temperaturaAtual;
		temperaturaAtual= (temperaturaAtual<25)?25:temperaturaAtual;
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
		gameBar();
	}

	
	private void storeAllPositions () {
		for(FireFightObject f : allObjects) {
			allPositions.add(f.getPosition());
		}
	}


	private void createColumns () {
		int counter = 0;
		//alterado
		//while(counter < 10) {
		while(counter < 15) {
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
		Set<Point> temp = FireSimulator.getInstance().getAllColumns().get(x);
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
		//alterado
		//while(counter < 10) {
		while(counter < 15) {
			if(countFireInColumnX(counter) > numFire) {
				numFire = countFireInColumnX(counter);
				biggest = counter;
			}
			counter++;
		}
		return biggest;
	}
	
	public void gameBar() {
		ImageMatrixGUI.getInstance().update();
		ImageMatrixGUI.getInstance().setStatusMessage("Direção do Vento: " + dirVento() + " | Temperatura: " 
				+ String.format("%.2f",FireSimulator.temperaturaAtual) + " | Humidade do Ar: " + String.format("%.2f",FireSimulator.humidadeAtual)
				+ " | Water Power: " + String.format("%.1f",Fireman.getWaterPower()));
	}
	
	public String dirVento() {
		int vento = FireSimulator.ventoAtual;
		switch(vento){		
		case 0:
			return "Norte";
		case 1:
			return "Este";
		case 2:
			return "Sul";
		case 3:
			return "Oeste";
		}
		return null;
	}

	public int getGUIWIDTH() {
		return GUIWIDTH;
	}

	public int getGUIHEIGHT() {
		return GUIHEIGHT;
	}
	
	
}

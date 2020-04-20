package pt.iul.poo.firefight.tools;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pt.iul.poo.firefight.utils.Direction;

class DirectionTest {
	
	
	Direction d1, d2, d3, d4;
	Point p1, p2, p3, p4, p5;
	int key1, key2, key3, key4, key5;
	

	@BeforeEach
	void setUp() throws Exception {
		
		d1 = Direction.LEFT;
		d2 = Direction.RIGHT;
		d3 = Direction.LEFT;
		
		p1 = new Point (-1,0); 
		p2 = new Point (1,0); 
		p3 = new Point (3,5); 
		p4 = new Point (-1,0);
		
		key1 = KeyEvent.VK_LEFT;
		key2 = KeyEvent.VK_RIGHT;
		key3 = KeyEvent.VK_ENTER;
		key4 = KeyEvent.VK_LEFT;
		
		}
	

	//Testar elementos do enumerado
	
	@Test
	void testEnumNullOrNotNull() {
		assertNotNull(d1);
		assertNotNull(d2);
		assertNotNull(d3); 
		assertNull(d4);
	}
	
	@Test
	void testEnumEqualities() {
		assertEquals(d1, d3);
		assertNotEquals(d1, d2);
	}
	
	@Test
	void testEnumToString() {
		assertEquals(d1.toString(), "LEFT");
		assertEquals(d2.toString(), "RIGHT");
		assertEquals(d3.toString(), "LEFT");
	}
	
	
	//Testar posições
	
	@Test
	void testPositionNullOrNotNull() {
		assertNotNull(p1);
		assertNotNull(p2);
		assertNotNull(p3);
		assertNull(p5);
	}
	
	@Test
	void testPositionEqualities() {
		assertEquals(p1, p4);
		assertNotEquals(p1, p2);
	}
	
	@Test
	void testPositionToString() {
		assertEquals(p1.toString(), "java.awt.Point[x=-1,y=0]");
		assertEquals(p2.toString(), "java.awt.Point[x=1,y=0]");
		assertEquals(p3.toString(), "java.awt.Point[x=3,y=5]");
		assertEquals(p4.toString(), "java.awt.Point[x=-1,y=0]");
	}
	
	
	
	//Testar chaves
	
	@Test
	void testKeyNotNulOrZero() {
		assertNotNull(key1);
		assertNotNull(key2);
		assertNotNull(key3);
		assertTrue(key5 == 0);
	}
	
	@Test
	void testKeyEqualities() {
		assertTrue(key1 == key4);
		assertFalse(key1 == key3);
	}
	
	
	//Testar métodos do enumerado
	
	@Test
	void testIsDirection () {
		assertTrue(Direction.isDirection(key1));
		assertFalse(Direction.isDirection(key3));
	}
	
	@Test
	void testMovementVector() {
		assertEquals(Direction.movementVector(p2, p1), p2);
		assertNotEquals(Direction.movementVector(p2, p3), p2);
	}
	
	@Test
	void testDirectionFor() {
		try { 
			assertEquals(Direction.directionFor(key1), Direction.directionFor(key4));
			assertNotEquals(Direction.directionFor(key1), Direction.directionFor(key2));
			assertNotEquals(Direction.directionFor(key1), Direction.directionFor(key3));
		}catch(IllegalArgumentException e) {
			System.out.println("Tecla inválida: " + KeyEvent.getKeyText(key3));
		}
	}

	
	
	
	
	
	
	
	
	
	
}

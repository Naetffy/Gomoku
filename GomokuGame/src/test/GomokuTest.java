package test;

import domain.*;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GomokuTest {

	String gameType;
	int size;
	int especialPercentage;
	Gomoku gomoku;

	@BeforeEach
	void setUp() throws Exception {
		size = 10;
		especialPercentage = 0;
		gameType = "Normal";
		try {
			gomoku = new Gomoku(gameType, size);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setLimits(15*15, -1);
			gomoku.setEspecialInfo(especialPercentage, especialPercentage);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		}
	}
	
	@Test
	void testGomokuConstructorShouldNotWork() {
		gameType = "tipoInvalido";
		try {
			gomoku = new Gomoku(gameType, size);
			fail("The test should fail");
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
	}
	
	@Test
	void testGomokuConstructorShouldWork() {
		gameType = "Normal";
		try {
			gomoku = new Gomoku(gameType, size);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
			fail("The test should pass");
		}
		gameType = "QuickTime";
		try {
			gomoku = new Gomoku(gameType, size);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
			fail("The test should pass");
		}
		gameType = "Limited";
		try {
			gomoku = new Gomoku(gameType, size);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
			fail("The test should pass");
		}
	}

	@Test
	void testGomokuConstructorPlayersShouldNotWork() {
		try {
			gomoku.setPlayers("InvalidPlayer", "Normal");
			fail("The test should fail");
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		}
	}

	@Test
	void testGomokuConstructorPlayersShouldWork() {
		try {
			gomoku.setPlayers("Normal", "Normal");
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("The test should pass");
		}
		try {
			gomoku.setPlayers("Normal", "ExpertMachine");
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("The test should pass");
		}
		try {
			gomoku.setPlayers("ScaryMachine", "AggressiveMachine");
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("The test should pass");
		}
	}

	@Test
	void testPutSomeTokensInSomePositions() {
		gomoku.play("NormalToken", 0, 0);
		gomoku.play("NormalToken", 0, 1);
		assertEquals(gomoku.getTokenColor(0, 0), new Color(0, 0, 0));
		assertEquals(gomoku.getTokenColor(0, 1), new Color(255, 255, 255));
	}

	@Test
	void testPutSomeTokensInSomePositionsAndNotChangeColor() {
		gomoku.play("NormalToken", 0, 0);
		gomoku.play("NormalToken", 0, 0);
		assertEquals(gomoku.getTokenColor(0, 0), new Color(0, 0, 0));
	}

	@Test
	void testNotPutSomeTokens() {
		gomoku.play("NormalToken", -1, 0);
		gomoku.play("NormalToken", 20, 20);
	}

	@Test
	void testAGameWithAWinner1() {
		gomoku.play("NormalToken", 0, 0);
		gomoku.play("NormalToken", 1, 0);
		gomoku.play("NormalToken", 0, 1);
		gomoku.play("NormalToken", 2, 0);
		gomoku.play("NormalToken", 0, 2);
		gomoku.play("NormalToken", 3, 0);
		gomoku.play("NormalToken", 0, 3);
		gomoku.play("NormalToken", 4, 0);
		assertEquals(null, gomoku.getWinner());
		gomoku.play("NormalToken", 0, 4);
		assertEquals("Mateo", gomoku.getWinner());
	}

	@Test
	void testAGameWithAWinner2() {
		gomoku.play("NormalToken", 0, 0);
		gomoku.play("NormalToken", 1, 0);
		gomoku.play("NormalToken", 0, 1);
		gomoku.play("NormalToken", 2, 0);
		gomoku.play("NormalToken", 0, 2);
		gomoku.play("NormalToken", 3, 0);
		gomoku.play("NormalToken", 0, 3);
		gomoku.play("NormalToken", 4, 0);
		gomoku.play("NormalToken", 0, 5);
		assertEquals(null, gomoku.getWinner());
		gomoku.play("NormalToken", 5, 0);
		assertEquals("Murcia", gomoku.getWinner());
	}

	@Test
	void testAGameWithAWinner3() {
		gomoku.play("NormalToken", 0, 0);
		gomoku.play("NormalToken", 1, 0);
		gomoku.play("NormalToken", 1, 1);
		gomoku.play("NormalToken", 2, 0);
		gomoku.play("NormalToken", 2, 2);
		gomoku.play("NormalToken", 3, 0);
		gomoku.play("NormalToken", 3, 3);
		gomoku.play("NormalToken", 4, 0);
		assertEquals(null, gomoku.getWinner());
		gomoku.play("NormalToken", 4, 4);
		assertEquals("Mateo", gomoku.getWinner());
	}

	@Test
	void testAGameWithAWinner4() {
		gomoku.play("NormalToken", 0, 4);
		gomoku.play("NormalToken", 0, 0);
		gomoku.play("NormalToken", 1, 3);
		gomoku.play("NormalToken", 1, 1);
		gomoku.play("NormalToken", 2, 2);
		gomoku.play("NormalToken", 2, 9);
		gomoku.play("NormalToken", 3, 1);
		gomoku.play("NormalToken", 3, 3);
		assertEquals(null, gomoku.getWinner());
		gomoku.play("NormalToken", 4, 0);
		assertEquals("Mateo", gomoku.getWinner());
	}

	@Test
	void testAGameWithAWinner5() {
		gomoku.play("NormalToken", 0, 4);
		gomoku.play("NormalToken", 0, 0);
		gomoku.play("NormalToken", 1, 3);
		gomoku.play("NormalToken", 1, 1);
		gomoku.play("NormalToken", 2, 2);
		gomoku.play("NormalToken", 2, 2);
		gomoku.play("NormalToken", 3, 1);
		gomoku.play("NormalToken", 3, 3);
		assertEquals(null, gomoku.getWinner());
		gomoku.play("NormalToken", 4, 0);
		assertEquals(null, gomoku.getWinner());
	}

	@Test
	void testAGameAndTheNumberOfTokens() {
		assertEquals(100, gomoku.getPlayerTokens().get("NormalToken"));
		gomoku.play("NormalToken", 0, 0);
		assertEquals(99, gomoku.getPlayerTokens().get("NormalToken"));
		gomoku.play("NormalToken", 1, 0);
		assertEquals(99, gomoku.getPlayerTokens().get("NormalToken"));
		gomoku.play("NormalToken", 0, 1);
		assertEquals(98, gomoku.getPlayerTokens().get("NormalToken"));
		gomoku.play("NormalToken", 2, 0);
		assertEquals(98, gomoku.getPlayerTokens().get("NormalToken"));
		gomoku.play("NormalToken", 0, 2);
		assertEquals(97, gomoku.getPlayerTokens().get("NormalToken"));
		gomoku.play("NormalToken", 3, 0);
		assertEquals(97, gomoku.getPlayerTokens().get("NormalToken"));
		gomoku.play("NormalToken", 0, 3);
		assertEquals(96, gomoku.getPlayerTokens().get("NormalToken"));
		gomoku.play("NormalToken", 4, 0);
	}
	
	@Test
	void testAGameWithTemporaryTokens() {
		int numTemporary = 0;
		try {
			while (numTemporary < 4) {
				gomoku = new Gomoku("Normal",15);
				gomoku.setPlayers("Normal", "Normal");
				gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
				gomoku.setEspecialInfo(50, 0);
				numTemporary = gomoku.getPlayerTokens().get("TemporaryToken");
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
			fail("Error should not happend");
		}
		
		gomoku.play("TemporaryToken", 0, 0);//Put temporary token turn 0
		
		assertEquals(new Color(0, 0, 0),gomoku.getTokenColor(0, 0));
		
		assertEquals(numTemporary-1,gomoku.getPlayerTokens().get("TemporaryToken"));
		
		gomoku.play("TemporaryToken", 0, 1);//Put temporary token turn 1
		
		assertEquals(new Color(255, 255, 255),gomoku.getTokenColor(0, 1));
		
		assertEquals(numTemporary-1,gomoku.getPlayerTokens().get("TemporaryToken"));
		
		gomoku.play("NormalToken", 1, 0);//Turn 2 
		
		assertEquals(new Color(0, 0, 0),gomoku.getTokenColor(1, 0));
		
		gomoku.play("NormalToken", 1, 1);//Turn 3, should delete token in (0,0)
		
		assertEquals(new Color(255, 255, 255),gomoku.getTokenColor(1, 1));
		
		assertEquals(null,gomoku.getTokenColor(0, 0));
		
		gomoku.play("NormalToken", 2, 0);//Turn 4, should delete token in (0,1)
		
		assertEquals(null,gomoku.getTokenColor(0, 1));
		
		gomoku.play("NormalToken", 2, 1);
	}
	
	@Test
	void testAGameWithHeavyTokens() {
		int numHeavy = 0;
		try {
			while (numHeavy==0) {
				gomoku = new Gomoku("Normal",15);
				gomoku.setPlayers("Normal", "Normal");
				gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
				gomoku.setEspecialInfo(50, 0);
				numHeavy = gomoku.getPlayerTokens().get("TemporaryToken");
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
			fail("Error should not happend");
		}
		gomoku.play("HeavyToken", 2, 2);
		assertEquals(new Color(0, 0, 0),gomoku.getTokenColor(2, 2));
		Color c = new Color(0, 0, 0);
		assertTrue(c.equals(gomoku.getTokenColor(1, 2)) || c.equals(gomoku.getTokenColor(3, 2)) ||
				c.equals(gomoku.getTokenColor(2, 1)) || c.equals(gomoku.getTokenColor(2, 3)));
		
		gomoku.play("HeavyToken", 5, 5);
		assertEquals(new Color(255, 255, 255),gomoku.getTokenColor(5, 5));
		c = new Color(255, 255, 255);
		assertTrue(c.equals(gomoku.getTokenColor(4, 5)) || c.equals(gomoku.getTokenColor(6, 5)) ||
				c.equals(gomoku.getTokenColor(5, 4)) || c.equals(gomoku.getTokenColor(5, 6)));
		
	}
	@Test
	void testAGameWithHeavyTokensInTheBorders() {
		int numHeavy = 0;
		try {
			while (numHeavy < 2) {
				gomoku = new Gomoku("Normal",15);
				gomoku.setPlayers("Normal", "Normal");
				gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
				gomoku.setEspecialInfo(50, 0);
				numHeavy = gomoku.getPlayerTokens().get("TemporaryToken");
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
			fail("Error should not happend");
		}
		gomoku.play("HeavyToken", 0, 0);
		assertEquals(new Color(0, 0, 0),gomoku.getTokenColor(0, 0));
		Color c = new Color(0, 0, 0);
		assertTrue(c.equals(gomoku.getTokenColor(0, 1)) || c.equals(gomoku.getTokenColor(1, 0)));
		
		gomoku.play("HeavyToken", 14, 14);
		assertEquals(new Color(255, 255, 255),gomoku.getTokenColor(14, 14));
		c = new Color(255, 255, 255);
		assertTrue(c.equals(gomoku.getTokenColor(14, 13)) || c.equals(gomoku.getTokenColor(13, 14)));
		
	}
	
	@Test
	void testAGameHavingZeroEspecialPercentage() {
		int numHeavy = gomoku.getPlayerTokens().get("HeavyToken");
		int numTemporary = gomoku.getPlayerTokens().get("TemporaryToken");
		assertEquals(0,numHeavy);
		assertEquals(0,numTemporary);
	}
	

}

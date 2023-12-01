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
			gomoku = new Gomoku(gameType, size, especialPercentage);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		}
	}
	
	@Test
	void testGomokuConstructorShouldNotWork() {
		gameType = "tipoInvalido";
		try {
			gomoku = new Gomoku(gameType, size, especialPercentage);
			fail("The test should fail");
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		}
	}
	
	@Test
	void testGomokuConstructorShouldWork() {
		gameType = "Normal";
		try {
			gomoku = new Gomoku(gameType, size, especialPercentage);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("The test should pass");
		}
		gameType = "QuickTime";
		try {
			gomoku = new Gomoku(gameType, size, especialPercentage);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("The test should pass");
		}
		gameType = "Limited";
		try {
			gomoku = new Gomoku(gameType, size, especialPercentage);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
		gomoku.play("Normal", 0, 0);
		gomoku.play("Normal", 0, 1);
		assertEquals(gomoku.getTokenColor(0, 0), new Color(0, 0, 0));
		assertEquals(gomoku.getTokenColor(0, 1), new Color(255, 255, 255));
	}

	@Test
	void testPutSomeTokensInSomePositionsAndNotChangeColor() {
		gomoku.play("Normal", 0, 0);
		gomoku.play("Normal", 0, 0);
		assertEquals(gomoku.getTokenColor(0, 0), new Color(0, 0, 0));
	}

	@Test
	void testNotPutSomeTokens() {
		gomoku.play("Normal", -1, 0);
		gomoku.play("Normal", 20, 20);
	}

	@Test
	void testAGameWithAWinner1() {
		gomoku.play("Normal", 0, 0);
		gomoku.play("Normal", 1, 0);
		gomoku.play("Normal", 0, 1);
		gomoku.play("Normal", 2, 0);
		gomoku.play("Normal", 0, 2);
		gomoku.play("Normal", 3, 0);
		gomoku.play("Normal", 0, 3);
		gomoku.play("Normal", 4, 0);
		assertEquals(null, gomoku.getWinner());
		gomoku.play("Normal", 0, 4);
		assertEquals("Mateo", gomoku.getWinner());
	}

	@Test
	void testAGameWithAWinner2() {
		gomoku.play("Normal", 0, 0);
		gomoku.play("Normal", 1, 0);
		gomoku.play("Normal", 0, 1);
		gomoku.play("Normal", 2, 0);
		gomoku.play("Normal", 0, 2);
		gomoku.play("Normal", 3, 0);
		gomoku.play("Normal", 0, 3);
		gomoku.play("Normal", 4, 0);
		gomoku.play("Normal", 0, 5);
		assertEquals(null, gomoku.getWinner());
		gomoku.play("Normal", 5, 0);
		assertEquals("Murcia", gomoku.getWinner());
	}

	@Test
	void testAGameWithAWinner3() {
		gomoku.play("Normal", 0, 0);
		gomoku.play("Normal", 1, 0);
		gomoku.play("Normal", 1, 1);
		gomoku.play("Normal", 2, 0);
		gomoku.play("Normal", 2, 2);
		gomoku.play("Normal", 3, 0);
		gomoku.play("Normal", 3, 3);
		gomoku.play("Normal", 4, 0);
		assertEquals(null, gomoku.getWinner());
		gomoku.play("Normal", 4, 4);
		assertEquals("Mateo", gomoku.getWinner());
	}

	@Test
	void testAGameWithAWinner4() {
		gomoku.play("Normal", 0, 4);
		gomoku.play("Normal", 0, 0);
		gomoku.play("Normal", 1, 3);
		gomoku.play("Normal", 1, 1);
		gomoku.play("Normal", 2, 2);
		gomoku.play("Normal", 2, 9);
		gomoku.play("Normal", 3, 1);
		gomoku.play("Normal", 3, 3);
		assertEquals(null, gomoku.getWinner());
		gomoku.play("Normal", 4, 0);
		assertEquals("Mateo", gomoku.getWinner());
	}

	@Test
	void testAGameWithAWinner5() {
		gomoku.play("Normal", 0, 4);
		gomoku.play("Normal", 0, 0);
		gomoku.play("Normal", 1, 3);
		gomoku.play("Normal", 1, 1);
		gomoku.play("Normal", 2, 2);
		gomoku.play("Normal", 2, 2);
		gomoku.play("Normal", 3, 1);
		gomoku.play("Normal", 3, 3);
		assertEquals(null, gomoku.getWinner());
		gomoku.play("Normal", 4, 0);
		assertEquals(null, gomoku.getWinner());
	}

	@Test
	void testAGameAndTheNumberOfTokens() {
		gomoku.play("Normal", 0, 0);
		assertEquals(99, gomoku.getPlayerTokens().get("Normal"));
		gomoku.play("Normal", 1, 0);
		assertEquals(99, gomoku.getPlayerTokens().get("Normal"));
		gomoku.play("Normal", 0, 1);
		assertEquals(98, gomoku.getPlayerTokens().get("Normal"));
		gomoku.play("Normal", 2, 0);
		assertEquals(98, gomoku.getPlayerTokens().get("Normal"));
		gomoku.play("Normal", 0, 2);
		assertEquals(97, gomoku.getPlayerTokens().get("Normal"));
		gomoku.play("Normal", 3, 0);
		assertEquals(97, gomoku.getPlayerTokens().get("Normal"));
		gomoku.play("Normal", 0, 3);
		assertEquals(96, gomoku.getPlayerTokens().get("Normal"));
		gomoku.play("Normal", 4, 0);
	}
}

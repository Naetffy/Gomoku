package test;

import domain.*;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GomokuTest {
	
	String gameType;
	int size;
	int especialPercentage;
	Gomoku gomoku;
	int numHeavy;int numNormal;int numTemporary;int numOverlap;
	@BeforeEach
	void setUp() throws Exception {
		size = 15;
		especialPercentage = 75;
		int numHeavy = 0;int numNormal = 0;int numTemporary = 0;int numOverlap = 0;
		try {
			while (numHeavy<10 || numNormal<10 || numTemporary<10 || numOverlap<10) {
				gomoku = new Gomoku("Normal",15);
				gomoku.setPlayers("Normal", "Normal");
				gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
				gomoku.setEspecialInfo(75, 0);
				numNormal = gomoku.getPlayerTokens().get("NormalToken");
				numTemporary = gomoku.getPlayerTokens().get("TemporaryToken");
				numOverlap = gomoku.getPlayerTokens().get("OverlapToken");
				numHeavy = gomoku.getPlayerTokens().get("HeavyToken");
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
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
	void testPutSomeNormalsTokensInSomePositions() {
		try {
			gomoku = new Gomoku("Normal",15);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		try {
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 1);
			
		} catch (GomokuException e) {
			fail("A exception is not expected");
		}
		assertEquals(gomoku.getTokenColor(0, 0), new Color(0, 0, 0));
		assertEquals(gomoku.getTokenColor(0, 1), new Color(255, 255, 255));
	}

	@Test
	void testPutSomeNormalsTokensInSomePositionsAndNotChangeColor() {
		try {
			gomoku = new Gomoku("Normal",15);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		try {
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 0);
		} catch (GomokuException e) {
			assertEquals(GomokuException.INVALID_MOVE_OVERLAP,e.getMessage());
		}
		assertEquals(gomoku.getTokenColor(0, 0), new Color(0, 0, 0));
	}

	@Test
	void testNotPutSomeNormalsTokens() {
		try {
			gomoku = new Gomoku("Normal",15);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		try {
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(-1, 0);
		} catch (GomokuException e) {
			assertEquals(GomokuException.INVALID_MOVE_POSITION,e.getMessage());
		}
		try {
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(20, 20);
		} catch (GomokuException e) {
			assertEquals(GomokuException.INVALID_MOVE_POSITION,e.getMessage());
		}
		
	}

	@Test
	void testAGameWithAWinnerOnlyNormalTokens1() {
		try {
			gomoku = new Gomoku("Normal",15);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		try {
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(1, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 1);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(2, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 2);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(3, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 3);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(4, 0);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals("NormalToken",gomoku.getToken());
			assertEquals(null, gomoku.getWinner());
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 4);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals("Mateo", gomoku.getWinner());
		}
		catch (GomokuException e) {
			fail("A exception is not expected");
		}
		
	}

	@Test
	void testAGameWithAWinnerOnlyNormalTokens2() {
		try {
			gomoku = new Gomoku("Normal",15);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		try {
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 0);

			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 0);

			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 1);

			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 0);

			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 2);

			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 0);

			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 3);

			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 4, 0);

			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 5);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals("NormalToken",gomoku.getToken());
			assertEquals(null, gomoku.getWinner());
			gomoku.play( 5, 0);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals("Murcia", gomoku.getWinner());
		} catch(GomokuException e) {
			fail("A exception is not expected");
		}
		
	}

	@Test
	void testAGameWithAWinnerOnlyNormalTokens3() {
		try {
			gomoku = new Gomoku("Normal",15);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		try {
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 1);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 2);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 3);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 4, 0);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals("NormalToken",gomoku.getToken());
			assertEquals(null, gomoku.getWinner());
			gomoku.play( 4, 4);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals("Mateo", gomoku.getWinner());
		}catch(GomokuException e) {
			fail("A exception is not expected");
		}
		
	}

	@Test
	void testAGameWithAWinnerOnlyNormalTokens4() {
		try {
			gomoku = new Gomoku("Normal",15);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		try {
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 4);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 3);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 1);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 2);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 9);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 1);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 3);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals("NormalToken",gomoku.getToken());
			assertEquals(null, gomoku.getWinner());
			gomoku.play( 4, 0);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals("Mateo", gomoku.getWinner());
		}catch(GomokuException e) {
			fail("A exception is not expected");
		}
		
	}

	@Test
	void testAGameWithAWinnerOnlyNormalTokens5() {
		try {
			gomoku = new Gomoku("Normal",15);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		try {
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 4);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 3);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 1);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 2);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 2);
			
		} catch(GomokuException e) {
			try {
				assertEquals("NormalToken",gomoku.getToken());
				gomoku.play( 3, 1);
				assertEquals("NormalToken",gomoku.getToken());
				gomoku.play( 3, 3);
				assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
				assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
				assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
				assertEquals("NormalToken",gomoku.getToken());
				assertEquals("NormalToken",gomoku.getToken());
				assertEquals(null, gomoku.getWinner());
				gomoku.play( 4, 0);
				assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
				assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
				assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
				assertEquals(null, gomoku.getWinner());
			} catch(GomokuException e1){
				fail("A fail is not expected");
			}
		}
	}
	@Test
	void testAGameWithNotAWinnerOnlyNormalTokens() {
		try {
			gomoku = new Gomoku("Normal",15);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		try {
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 5);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 4);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 1);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 3);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 9);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 5, 0);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 3);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals("NormalToken",gomoku.getToken());
			assertEquals(null, gomoku.getWinner());
			gomoku.play( 4, 1);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 6, 6);
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 2);
			assertEquals(null, gomoku.getWinner());
		}catch(GomokuException e) {
			fail("A exception is not expected");
		}
		
	}

	@Test
	void testAGameAndTheNumberOfTokensOnlyNormalTokens() {
		try {
			gomoku = new Gomoku("Normal",10);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		try {
			assertEquals(100, gomoku.getPlayerTokens().get("NormalToken"));
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 0);
			assertEquals(99, gomoku.getPlayerTokens().get("NormalToken"));
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 0);
			assertEquals(99, gomoku.getPlayerTokens().get("NormalToken"));
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 1);
			assertEquals(98, gomoku.getPlayerTokens().get("NormalToken"));
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 0);
			assertEquals(98, gomoku.getPlayerTokens().get("NormalToken"));
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 2);
			assertEquals(97, gomoku.getPlayerTokens().get("NormalToken"));
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 0);
			assertEquals(97, gomoku.getPlayerTokens().get("NormalToken"));
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 3);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
			assertEquals(96, gomoku.getPlayerTokens().get("NormalToken"));
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 4, 0);
			assertEquals(0, gomoku.getPlayerTokens().get("HeavyToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("TemporaryToken"));
			assertEquals(0, gomoku.getPlayerTokens().get("OverlapToken"));
		} catch(GomokuException e) {
			fail("A error is dont expected");
		}
	}
	//Desde aca
	@Test
	void testPutSomeHeavyTokensInSomePositions() {
		try {
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play(0, 0);
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play(0, 1);
			
		} catch (GomokuException e) {
			fail("A exception is not expected");
		}
		assertEquals(gomoku.getTokenColor(0, 0), new Color(0, 0, 0));
		assertEquals(gomoku.getTokenColor(0, 1), new Color(255, 255, 255));
	}
	
	@Test
	void testPutSomeHeavysTokensInSomePositionsAndNotChangeColor() {
		try {
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 0);
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play(0, 0);
		} catch (GomokuException e) {
			assertEquals(GomokuException.INVALID_MOVE_OVERLAP,e.getMessage());
		}
		assertEquals(gomoku.getTokenColor(0, 0), new Color(0, 0, 0));
		try {
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play(0, 1);
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play(0, 1);
		} catch (GomokuException e) {
			assertEquals(GomokuException.INVALID_MOVE_OVERLAP,e.getMessage());
		}
		assertEquals(gomoku.getTokenColor(0, 1), new Color(255, 255, 255));
		try {
			gomoku.setPlayerToken("TemporaryToken");
			assertEquals("TemporaryToken",gomoku.getToken());
			gomoku.play(0, 2);
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play(0, 2);
		} catch (GomokuException e) {
			assertEquals(GomokuException.INVALID_MOVE_OVERLAP,e.getMessage());
		}
		assertEquals(gomoku.getTokenColor(0, 2), new Color(0, 0, 0));
		try {
			gomoku.setPlayerToken("OverlapToken");
			assertEquals("OverlapToken",gomoku.getToken());
			gomoku.play(0, 3);
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play(0, 3);
		} catch (GomokuException e) {
			assertEquals(GomokuException.INVALID_MOVE_OVERLAP,e.getMessage());
		}
		assertEquals(gomoku.getTokenColor(0, 3), new Color(255, 255, 255));
	}
	
	@Test
	void testNotPutSomeHeavysTokens() {
		try {
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play(-1, 0);
		} catch (GomokuException e) {
			assertEquals(GomokuException.INVALID_MOVE_POSITION,e.getMessage());
		}
		try {
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play(20, 20);
		} catch (GomokuException e) {
			assertEquals(GomokuException.INVALID_MOVE_POSITION,e.getMessage());
		}
		
	}
	

	@Test
	void testAGameWithAWinnerNormalAndHeavyTokens1() {
		try {
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play(0, 0);//2
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.setPlayerToken("NormalToken");
			gomoku.play(1, 0);//1
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 1);//3
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(2, 0);//2
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 2);//4
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(3, 0);//3
			assertEquals(null, gomoku.getWinner());
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play(0, 3);//5
			assertEquals("Mateo", gomoku.getWinner());
		}
		catch (GomokuException e) {
			fail("A exception is not expected");
		}
		
	}
	@Test
	void testAGameWithAWinnerOnlyNormalAndHeavysTokens2() {
		try {
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 0);//2

			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 1, 0);//2

			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 1);//4
			
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 2, 0);//4
			
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 2);//6
			assertEquals(null, gomoku.getWinner());
			
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 0);//5
			assertEquals("Murcia", gomoku.getWinner());
		} catch(GomokuException e) {
			fail("A exception is not expected "+e.getMessage());
		}
		
	}
	@Test
	void testAGameWithAWinnerOnlyNormalAndHeavysTokens3() {
		try {
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 0);//2
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 0);//1
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 1);//3
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 0);//2
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 2);//4
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 0);//3
			assertEquals(null, gomoku.getWinner());
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 3);//5
			assertEquals("Mateo", gomoku.getWinner());
		}catch(GomokuException e) {
			fail("A exception is not expected");
		}
		
	}

	@Test
	void testAGameWithAWinnerOnlyNormalAndHeavyTokens4() {
		try {
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 4);//2
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 0);//1
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 3);//3
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 1);//2
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 2);//4
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 9);//1
			assertEquals(null, gomoku.getWinner());
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 3, 1);//5
			assertEquals("Mateo", gomoku.getWinner());
		}catch(GomokuException e) {
			fail("A exception is not expected");
		}
		
	}
	
	@Test
	void testAGameWithAWinnerOnlyNormalAndHeavyTokens5() {
		try {
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 4);//2
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 0, 0);//1
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 3);//3
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 1, 1);//2
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 2);//4
			gomoku.setPlayerToken("NormalToken");
			assertEquals("NormalToken",gomoku.getToken());
			gomoku.play( 2, 2);//3
			
		} catch(GomokuException e) {
			try {
				gomoku.setPlayerToken("NormalToken");
				assertEquals("NormalToken",gomoku.getToken());
				gomoku.play( 3, 3);//3
				assertEquals(null, gomoku.getWinner());
				gomoku.setPlayerToken("NormalToken");
				assertEquals("NormalToken",gomoku.getToken());
				gomoku.play( 3, 1);//5
				assertEquals("Mateo", gomoku.getWinner());
			} catch(GomokuException e1){
				fail("A fail is not expected");
			}
		}
		
	}

	@Test
	void testAGameWithNotAWinnerOnlyNormalHeavyTokens() {
		try {
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 4);//2
			assertEquals(null, gomoku.getWinner());
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 0);//2
			assertEquals(null, gomoku.getWinner());
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 1, 3);//4
			assertEquals(null, gomoku.getWinner());
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 1, 1);//4
			assertEquals(null, gomoku.getWinner());
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 2, 2);//6
			assertEquals(null, gomoku.getWinner());
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 3, 3);//2
			assertEquals(null, gomoku.getWinner());
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 3, 1);//8
			assertEquals(null, gomoku.getWinner());
		}catch(GomokuException e) {
			fail("A exception is not expected");
		}
		
	}
	
	@Test
	void testAGameAndTheNumberOfTokensOnlyHeavyTokens() {
		try {
			numHeavy = gomoku.getPlayerTokens().get("HeavyToken");
			assertEquals(numHeavy, gomoku.getPlayerTokens().get("HeavyToken"));
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 0);
			assertEquals(numHeavy-1, gomoku.getPlayerTokens().get("HeavyToken"));
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 1, 0);
			assertEquals(numHeavy-1, gomoku.getPlayerTokens().get("HeavyToken"));
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 1);
			assertEquals(numHeavy-2, gomoku.getPlayerTokens().get("HeavyToken"));
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 2, 0);
			assertEquals(numHeavy-2, gomoku.getPlayerTokens().get("HeavyToken"));
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 2);
			assertEquals(numHeavy-3, gomoku.getPlayerTokens().get("HeavyToken"));
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 3, 0);
			assertEquals(numHeavy-3, gomoku.getPlayerTokens().get("HeavyToken"));
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 0, 3);
			assertEquals(numHeavy-4, gomoku.getPlayerTokens().get("HeavyToken"));
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
			gomoku.play( 4, 0);
			assertEquals(numHeavy-4, gomoku.getPlayerTokens().get("HeavyToken"));
			gomoku.setPlayerToken("HeavyToken");
			assertEquals("HeavyToken",gomoku.getToken());
		} catch(GomokuException e) {
			fail("A error is dont expected");
		}
	}
	//Hasta aca
	@Test
	void testPutSomeTemporaryTokensInSomePositions() {
		try {
			gomoku.setPlayerToken("TemporaryToken");
			assertEquals("TemporaryToken",gomoku.getToken());
			gomoku.play(0, 0);
			gomoku.setPlayerToken("TemporaryToken");
			assertEquals("TemporaryToken",gomoku.getToken());
			gomoku.play(0, 1);
			
		} catch (GomokuException e) {
			fail("A exception is not expected");
		}
		assertEquals(gomoku.getTokenColor(0, 0), new Color(0, 0, 0));
		assertEquals(gomoku.getTokenColor(0, 1), new Color(255, 255, 255));
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
		try {
			gomoku.setPlayerToken("TemporaryToken");

			assertEquals("TemporaryToken", gomoku.getToken());
			gomoku.play(0, 0);//Put temporary token turn 0
			
			assertEquals(new Color(0, 0, 0),gomoku.getTokenColor(0, 0));
			
			assertEquals(numTemporary-1,gomoku.getPlayerTokens().get("TemporaryToken"));
			
			gomoku.setPlayerToken("TemporaryToken");
			assertEquals("TemporaryToken", gomoku.getToken());
			gomoku.play(0, 1);//Put temporary token turn 1
			
			assertEquals(new Color(255, 255, 255),gomoku.getTokenColor(0, 1));
			
			assertEquals(numTemporary-1,gomoku.getPlayerTokens().get("TemporaryToken"));
			
			gomoku.setPlayerToken("NormalToken");
			gomoku.play( 1, 0);//Turn 2 
			
			assertEquals(new Color(0, 0, 0),gomoku.getTokenColor(1, 0));
			
			gomoku.setPlayerToken("NormalToken");
			gomoku.play( 1, 1);//Turn 3, should delete token in (0,0)
			
			assertEquals(new Color(255, 255, 255),gomoku.getTokenColor(1, 1));
			
			assertEquals(null,gomoku.getTokenColor(0, 0));
			
			gomoku.setPlayerToken("NormalToken");
			gomoku.play( 2, 0);//Turn 4, should delete token in (0,1)
			
			assertEquals(null,gomoku.getTokenColor(0, 1));
			
			gomoku.setPlayerToken("NormalToken");
			gomoku.play( 2, 1);
		} catch(GomokuException e) {
			fail("A fail is dont expected");
		}
	}
	
	
	
	@Test
	void testAGameHavingZeroEspecialPercentage() {
		try {
			gomoku = new Gomoku("Normal",15);
			gomoku.setPlayers("Normal", "Normal");
			gomoku.setPlayersInfo("Mateo", new Color(0,0,0), "Murcia", new Color(255,255,255));
			gomoku.setEspecialInfo(0, 0);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | GomokuException e) {
		}
		int numHeavy = gomoku.getPlayerTokens().get("HeavyToken");
		int numTemporary = gomoku.getPlayerTokens().get("TemporaryToken");
		int numOverlap= gomoku.getPlayerTokens().get("OverlapToken");
		assertEquals(0,numHeavy);
		assertEquals(0,numTemporary);
		assertEquals(0,numOverlap);
	}
	
}

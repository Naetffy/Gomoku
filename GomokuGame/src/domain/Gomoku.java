package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Gomoku {

	private Game game;

	public Gomoku(String gameType, int size, int especialPercentage)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String type = "domain." + gameType + "Game";
		Class<?> clazz = Class.forName(type);
		Constructor<?> constructor = clazz.getConstructor(int.class, int.class);
		Object gameInstance = constructor.newInstance(size, especialPercentage);
		game = (Game) gameInstance;
	}

	public void setPlayers(String typePlayer1, String typePlayer2)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		game.setPlayers(typePlayer1, typePlayer2);
	}

	public void setPlayersInfo(String player1, Color color1, String player2, Color color2) {
		game.setPlayersInfo(player1, color1, player2, color2);
	}

	public int getSize() {
		return game.getSize();
	}

	public Game getGame() {
		return game;
	}

	public void play(int row, int column) {
		game.play(row, column);
	}

	public Color getTokenColor(int row, int column) {
		return game.getTokenColor(row, column);
	}

	public String getWinner() {
		return game.getWinner();
	}

	public HashMap<String, Integer> getPlayerTokens() {
		return game.getPlayerTokens();
	}

	public Player getPlayerOne() {
		return game.getPlayerOne();
	}

	public Player getPlayerTwo() {
		return game.getPlayerTwo();
	}

}
package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Gomoku {
	
	private Game game;
	
	
	public Gomoku(String gameType,int size, int especialPercentage) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String type = "domain."+gameType+"Game";
		Class<?> clazz = Class.forName(type);
		Constructor<?> constructor = clazz.getConstructor(int.class,int.class);
		Object gameInstance = constructor.newInstance(size,especialPercentage);
		game = (Game) gameInstance;
	}
	
	public void setPlayers(String player1, String player2) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Player playerOne, playerTwo;
		if (player1 == "Normal") {
			playerOne = new NormalPlayer(); 
		}
		else{
			String type = "domain."+player1+"Machine";
			Class<?> clazz = Class.forName(type);
			Constructor<?> constructor = clazz.getConstructor();
			Object machineInstance = constructor.newInstance();
			playerOne =  (Player) machineInstance;
		}
		if (player2 == "Normal") {
			playerTwo = new NormalPlayer(); 
		}
		else{
			String type = "domain."+player2+"Machine";
			Class<?> clazz = Class.forName(type);
			Constructor<?> constructor = clazz.getConstructor();
			Object machineInstance = constructor.newInstance();
			playerTwo =  (Player) machineInstance;
		}
		game.setPlayers(playerOne, playerTwo);
	}
	
	public void setPlayersInfo(String player1, String player2) {
		game.setPlayersInfo(player1, new Color(0,0,0), player2, new Color(255,255,255));
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
	
	public HashMap<String,Integer> getPlayerTokens(){
		return game.getPlayerTokens();
	}
	public Player getPlayerOne() {
		return game.getPlayerOne();
	}
	public Player getPlayerTwo() {
		return game.getPlayerTwo();
	}

}
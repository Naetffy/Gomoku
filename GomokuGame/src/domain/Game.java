package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public abstract class Game {
	
	private Board board;
	protected int size;
	protected int especialPercentage;
	protected Player playerOne;
	protected Player playerTwo;
	private String winner;
	private int turn;
	
	public Game (int size, int especialPercentage) {
		this.size = size;
		this.board = new Board(size,especialPercentage);
		this.especialPercentage = especialPercentage;
		turn = 0;
		winner = null;
	}

	public void play(int row, int column) {
		if(board.getTokenColor(row, column) == null && board.verify(row, column)) {
			String player;
			if ((turn % 2) == 0) {
				((NormalPlayer)playerOne).play("Normal",row,column);
				player = playerOne.getName();
			}
			else {
				((NormalPlayer)playerTwo).play("Normal",row,column);
				player = playerTwo.getName();
			}
			turn+=1;
			if(board.validate(row,column))winner = player;
		}
	}
	
	public void setPlayers(String player1, String player2) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
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
		start(especialPercentage);
	}
	
	public void setPlayersInfo(String nameOne, Color color1, String nameTwo, Color color2) {
		playerOne.setInfo(nameOne,color1);
		playerTwo.setInfo(nameTwo,color2);
	}
	
	public int getSize() {
		return size;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public Player getPlayerOne() {
		return playerOne;
	}
	
	public Player getPlayerTwo() {
		return playerTwo;
	}
	
	public Color getTokenColor(int row, int column) {
		return board.getTokenColor(row, column);
	}
	
	public HashMap<String,Integer> getPlayerTokens(){
		HashMap<String,Integer> res;
		if ((turn % 2) != 0) {
			res = playerOne.getMap();
		}
		else {
			res = playerTwo.getMap();
		}
		return res;
	}
	
	public abstract void start(int especialPercentage);

}

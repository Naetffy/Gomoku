package domain;

import java.awt.Color;
import java.util.HashMap;

public abstract class Game {
	
	private Board board;
	protected int size;
	protected int especialPercentage;
	protected Player playerOne;
	protected Player playerTwo;
	private boolean turn;
	private String winner;
	
	public Game (int size, int especialPercentage) {
		this.size = size;
		this.board = new Board(size,especialPercentage);
		this.especialPercentage = especialPercentage;
		turn = true;
		winner = null;
	}
	
	public int getSize() {
		return size;
	}
	
	
	public void setPlayers(Player playerOne, Player playerTwo) {
		this.playerOne = playerOne;
		this.playerOne.setBoard(board);
		this.playerTwo = playerTwo;
		this.playerTwo.setBoard(board);
		start(especialPercentage);
	}

	public void setPlayersInfo(String nameOne, Color color1, String nameTwo, Color color2) {
		playerOne.setInfo(nameOne,color1);
		playerTwo.setInfo(nameTwo,color2);
	}
	public Color getTokenColor(int row, int column) {
		return board.getTokenColor(row, column);
	}

	public void play(int row, int column) {
		if(board.getTokenColor(row, column)==null) {
			String player;
			if (turn) {
				((NormalPlayer)playerOne).play("Normal",row,column);
				player = playerOne.getName();
			}
			else {
				((NormalPlayer)playerTwo).play("Normal",row,column);
				player = playerTwo.getName();
			}
			turn = !turn;
			if(board.validate(row,column))winner = player;
		}
	}
	
	public String getWinner() {
		return winner;
	}
	
	public HashMap<String,Integer> getPlayerTokens(){
		HashMap<String,Integer> res;
		if (!turn) {
			res = playerOne.getMap();
		}
		else {
			res = playerTwo.getMap();
		}
		return res;
	}
	public abstract void start(int especialPercentage);

}

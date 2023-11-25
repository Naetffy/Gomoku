package domain;

public abstract class Game {
	private Board board;
	protected Player playerOne;
	protected Player playerTwo;
	
	public Game (int size, int especialPercentage) {
		this.board = new Board(size,especialPercentage);
	}
	
	
	public abstract void start();
}

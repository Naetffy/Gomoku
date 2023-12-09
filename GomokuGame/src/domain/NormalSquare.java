package domain;

public class NormalSquare extends Square {

	public NormalSquare(Board board,int row, int column) {
		super(board,row,column);
	}

	public void act() {
		
		AlertPlay.dettach(this);
		setToken(token);
		board.increaseTurn();
	}
}

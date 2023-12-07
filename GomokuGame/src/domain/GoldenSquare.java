package domain;

public class GoldenSquare extends Square {

	public GoldenSquare(Board board,int row, int column) {
		super(board,row,column);
	}

	public void playToken(Token token) {
		setToken(token);
	}
}

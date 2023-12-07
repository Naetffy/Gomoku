package domain;

import java.util.Random;

public class TeleportSquare extends Square {

	public TeleportSquare(Board board,int row, int column) {
		super(board,row,column);
	}

	public void playToken(Token token) {
		Random random = new Random();
		int i = random.nextInt(0,board.getSize());
		int j = random.nextInt(0,board.getSize());
		while (board.getTokenColor(i, j) != null) {
			i = random.nextInt(0,board.getSize());
			j = random.nextInt(0,board.getSize());
		}
		token.setPosition(i, j);
		board.playToken(token, i, j);
	}
}

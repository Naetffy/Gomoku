package domain;

import java.util.Random;

public class TeleportSquare extends Square {

	public TeleportSquare(Board board,int row, int column) {
		super(board,row,column);
	}
	
	public void act() {
		AlertPlay.dettach(this);
		Random random = new Random();
		int i = random.nextInt(0,board.getSize());
		int j = random.nextInt(0,board.getSize());
		while (board.getTokenColor(i, j) != null) {
			i = random.nextInt(0,board.getSize());
			j = random.nextInt(0,board.getSize());
		}
		Token actualToken = token;
		token = null;
		board.playToken(actualToken, i, j);
		
	}
}

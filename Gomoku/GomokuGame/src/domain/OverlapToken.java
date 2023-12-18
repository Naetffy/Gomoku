package domain;

import java.awt.Color;
import java.util.Random;

public class OverlapToken extends Token {
	private Token otherToken;
	
	public OverlapToken(Color color, int row, int column) {
		super(color, row, column);
		
	}

	public void act() throws GomokuException {
		AlertPlay.dettach(this);
		player.increaseScore(100);
		if (otherToken == null) {
			
		}
		else if(otherToken instanceof HeavyToken) {
			int r = row; int c = column;
			Square squareOther = otherToken.getSquare();
			player.deleteToken(r, c);
			Token other = new NormalToken(otherToken.getColor(),r,c);
			other.setPlayer(otherToken.getPlayer());
			squareOther.setToken(other);
			otherToken.getPlayer().setWinner(r,c);
		}
		else if(otherToken instanceof TemporaryToken) {
			Random random = new Random();
			Board board = square.getBoard();
	        int i = random.nextInt(0, board.getSize());
	        int j = random.nextInt(0, board.getSize());

	        // Find a random empty square on the board
	        while (board.getTokenColor(i, j) != null) {
	            i = random.nextInt(0, board.getSize());
	            j = random.nextInt(0, board.getSize());
	        }
	        board.playToken(otherToken, i, j);
	        board.decreaseTurn();
		}
		else {
			player.increaseScore(100);
		}
		
	}
	
	public boolean valid(Token token) throws GomokuException {
		if(token == null || !token.getColor().equals(this.color)) {
			if (token instanceof OverlapToken)
				throw new GomokuException(GomokuException.INVALID_OVERLAP);
			otherToken = token;
			return true;
		}
		else {
			throw new GomokuException(GomokuException.INVALID_OVERLAP_SAME);
		}
	}

}

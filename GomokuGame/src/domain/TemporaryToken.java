package domain;

import java.awt.Color;

public class TemporaryToken extends Token {

	Integer creationTurn;
	
	public TemporaryToken(Color color, int row, int column) {
		super(color, row, column);
	}
	
	public void act() {
		if (creationTurn == null) {
			creationTurn = Gomoku.getGomoku().getTurn();
		}
		else {
			if(creationTurn + 3 == Gomoku.getGomoku().getTurn()) {
				player.deleteToken(row,column);
				AlertPlay alert = new AlertPlay();
				alert.dettach(this);
			}
		}
	}
	

}

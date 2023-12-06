package domain;

import java.awt.Color;

public class NormalToken extends Token {

	public NormalToken(Color color, int row, int column) {
		super(color, row, column);
	}

	
	public void act() {
		AlertPlay alert = new AlertPlay();
		alert.dettach(this);
	}

}

package domain;

import java.awt.Color;

public abstract class Token {
	private Color color;
	int row;
	int column;
	
	public Token(Color color, int row, int column) {
		this.color = color;
		this.row = row;
		this.column = column;
	}
	
	public Color getColor() {
		return color;
	}
}

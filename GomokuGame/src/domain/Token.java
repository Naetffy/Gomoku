package domain;

import java.awt.Color;

public abstract class Token {
	private Color color;
	
	public Token(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}

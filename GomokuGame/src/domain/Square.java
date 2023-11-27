package domain;

import java.awt.Color;

public class Square {
	Token token;
	public Square() {
		this.token = null;
	}
	public static NormalSquare createSquareInstance(boolean especial) {
		NormalSquare square = new NormalSquare();
		return square;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public Color getTokenColor() {
		Color color = null;
		if (token != null) {
			color = token.getColor();
		}
		return color;
	}
}

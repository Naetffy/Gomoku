package domain;

import java.awt.Color;
import java.util.Set;

import org.reflections.Reflections;

public class Square {
	Token token;
	public static Set<Class> subTypes = null;
	
	public static Set<Class> getSquareSubtypes() {
		if (subTypes == null) {
			Reflections reflections = new Reflections("domain");
	        Set subTypesGet = reflections.getSubTypesOf(Square.class);
	        subTypes = (Set<Class>)subTypesGet;
		}
        return subTypes;
    }

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

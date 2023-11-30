package domain;

import java.awt.Color;
import java.util.Set;
import org.reflections.Reflections;

public abstract class Token {
	private Color color;
	int row;
	int column;
	public static Set<Class> subTypes = null;
	
	public static Set<Class> getTokenSubtypes() {
		if (subTypes == null) {
			Reflections reflections = new Reflections("domain");
	        Set subTypesGet = reflections.getSubTypesOf(Token.class);
	        subTypes = (Set<Class>)subTypesGet;
		}
        return subTypes;
    }
	
	public Token(Color color, int row, int column) {
		this.color = color;
		this.row = row;
		this.column = column;
	}

	public Color getColor() {
		return color;
	}
    
}

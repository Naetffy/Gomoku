package domain;

import java.awt.Color;
import java.util.Set;
import org.reflections.Reflections;

public abstract class Token implements PlayToken{
	private Color color;
	protected int row;
	protected int column;
	protected Player player;
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
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setPosition(int row, int column) {
		this.row = row;
		this.column =  column;
	}

	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
}

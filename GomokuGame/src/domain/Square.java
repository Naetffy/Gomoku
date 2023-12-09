package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.Set;

import org.reflections.Reflections;

public abstract class Square implements PlayToken{
	protected Token token;
	public static Set<Class> subTypes = null;
	protected Board board;
	protected int row;
	protected int column;

	public static Set<Class> getSquareSubtypes() {
		if (subTypes == null) {
			Reflections reflections = new Reflections("domain");
			Set subTypesGet = reflections.getSubTypesOf(Square.class);
			subTypes = (Set<Class>) subTypesGet;
		}
		return subTypes;
	}

	public Square(Board board,int row, int column) {
		this.board = board;
		this.row = row;
		this.column = column;
		this.token = null;
	}

	public static Square createSquareInstance(Board board,int row,int column, boolean especial) {
		Square square = null;
		if (!especial) {
			square = new NormalSquare(board,row,column);
		} else {
			Set<Class> subtypes = Square.getSquareSubtypes();
			Random random = new Random();
			int range = random.nextInt(0, subtypes.size()-1);
			int i = 0;
			for (Class sq : subtypes) {
				if (!sq.getSimpleName().equals("NormalSquare")) {
					if (i == range) {
						try {
							Constructor<?> cons = sq.getConstructor(Board.class,int.class,int.class);
							Object obj = cons.newInstance(board,row,column);
							square = (Square) obj;
						} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
								| IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					i++;
				}
			}
		}
		return square;
	}

	public void setToken(Token token) {
		if (token != null) {
			this.token = token;
			token.setPosition(row, column);
			AlertPlay.attach(token);
			AlertPlay.notifyObservers();
		}
		else {
			this.token.setPosition(-1,-1);
			this.token = token;
		}
		
	}

	public void playToken(Token token) {
		this.token = token;
		AlertPlay.attach(this);
		AlertPlay.notifyObservers();
	}
	
	public Color getTokenColor() {
		Color color = null;
		if (token != null) {
			color = token.getColor();
		}
		return color;
	}
}

package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NormalPlayer extends Player{

	public void play() {
		
	}
	
	public void play(String typeToken, int row, int column) {
		String type = "domain."+typeToken+"Token";
		Class<?> clazz;
		try {
			clazz = Class.forName(type);
			Constructor<?> constructor = clazz.getConstructor(Color.class);
			Object tokenInstance = constructor.newInstance(color);
			Token actualToken =  (Token) tokenInstance;
			int quantity = quantitys.get(typeToken);
			quantitys.put(typeToken, quantity-1);
			board.setToken(actualToken,row,column);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
	}
}

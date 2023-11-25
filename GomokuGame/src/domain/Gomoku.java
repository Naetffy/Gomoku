package domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Gomoku {
	
	private Game game;
	
	public Gomoku(String gameType,int size, int especialPercentage) {
		try {
			String type = "domain."+gameType+"Game";
			System.out.println(type);
			Class<?> clazz = Class.forName(type);
			System.out.println(type);
			Constructor<?> constructor = clazz.getConstructor(int.class,int.class);
			Object gameInstance = constructor.newInstance(size,especialPercentage);
			game = (Game) gameInstance;
		} 
		catch (ClassNotFoundException e) {e.printStackTrace();} 
		catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();} 
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {e.printStackTrace();}
	}

}
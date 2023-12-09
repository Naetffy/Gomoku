package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.reflections.Reflections;

public abstract class Player {
	private String name;
	protected Color color;
	protected HashMap<String, Integer> quantitys;
	protected Game game;

	public static Set<Class> subTypes = null;
	
	public static Set<Class> getPlayerSubtypes() {
		if (subTypes == null) {
			Reflections reflections = new Reflections("domain");
	        Set subTypesGet = reflections.getSubTypesOf(Player.class);
	        subTypes = (Set<Class>)subTypesGet;
		}
        return subTypes;
    }
	
	public Player() {
		quantitys = new HashMap<>();
	}

	public void setInfo(String namePlayer, Color color) {
		name = namePlayer;
		this.color = color;
	}

	public void setQuantityTypeOfToken(String tokenName, int quantity) {
		quantitys.put(tokenName, quantity);
	}
	
	public void increaseQuantityToken(String tokenName, int increase) {
		quantitys.put(tokenName, quantitys.get(tokenName)+increase);
	}


	public String getName() {
		return name;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}

	public HashMap<String, Integer> getMap() {
		return quantitys;
	}
	
	public void play(String typeToken, int row, int column) {
		String type = "domain." + typeToken;
		Class<?> clazz;
		try {
			int quantity = quantitys.get(typeToken);
			if (quantity > 0) {
				clazz = Class.forName(type);
				Constructor<?> constructor = clazz.getConstructor(Color.class, int.class, int.class);
				Object tokenInstance = constructor.newInstance(color, row, column);
				Token actualToken = (Token) tokenInstance;
				actualToken.setPlayer(this);
				quantitys.put(typeToken, quantity - 1);
				game.playToken(actualToken, row, column);
				game.setWinner(actualToken.getRow(), actualToken.getColumn());
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteToken(int row, int column) {
		game.setToken(null, row, column);
	}
	public Set<Class> getTokenSubtypes() {
		return Token.getTokenSubtypes();
	}
	
	public abstract ArrayList<Object> play();

	public void increaseTurn() {
		game.increaseTurn();
	}
	public void decreaseTurn() {
		game.decreaseTurn();
	}
}

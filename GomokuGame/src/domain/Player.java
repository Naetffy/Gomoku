package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.reflections.Reflections;

public abstract class Player {
	private String name;
	protected Color color;
	protected HashMap<String, Integer> quantitys;
	protected LinkedList<Token> tokens;
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
		LinkedList<Token> tokens = new LinkedList();
	}

	public void setInfo(String namePlayer, Color color) {
		name = namePlayer;
		this.color = color;
	}

	public void setQuantityTypeOfToken(String tokenName, int quantity) {
		quantitys.put(tokenName, quantity);
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
	

	public abstract int[] play();
}

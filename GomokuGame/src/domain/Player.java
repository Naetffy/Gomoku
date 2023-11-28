package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class Player {
	private String name;
	protected Color color;
	protected Board board;
	protected HashMap<String,Integer> quantitys;
	protected LinkedList<Token> tokens;
	
	public Player() {
		quantitys = new HashMap<>();
		LinkedList<Token> tokens = new LinkedList();
	}

	public void setInfo(String namePlayer,Color color) {
		name = namePlayer;
		this.color = color;
	}
	
	public void setTypeOfToken(String tokenName, int quantity){
		quantitys.put(tokenName, quantity);
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public String getName() {
		return name;
	}
	
	public HashMap<String,Integer> getMap(){
		return quantitys;
	}
	
	public abstract int[] play();
}

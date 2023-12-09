package domain;

import java.util.ArrayList;

public class AlertPlay {

	private static boolean iterating = false;
	private static ArrayList<PlayToken> observers =  new ArrayList<>();
	private static ArrayList<PlayToken> toRemove =  new ArrayList<>();
	
	public static void  attach(PlayToken observer) {
		observers.add(observer);
	}
	
	public static void  dettach(PlayToken observer) {
		if(!iterating) {
			observers.remove(observer);
		}
		else {
			toRemove.add(observer);
		}
	}
	
	public static void notifyObservers() {
		iterating = true;
		for (int i = 0; i < observers.size(); i++) {
			if (!toRemove.contains(observers.get(i))) {
				observers.get(i).act();
			}
		}
		iterating = false;
		toRemove();
	}
	
	public static void toRemove() {
		for(PlayToken c : toRemove) {
			dettach(c);
		}
		toRemove = new ArrayList<>();
	}

	public static void dettachAll() {
		observers = new ArrayList<>();
	}
}

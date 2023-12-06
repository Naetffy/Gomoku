package domain;

import java.util.ArrayList;

public class AlertPlay {

	private static ArrayList<PlayToken> observers =  new ArrayList<>();
	
	public void  attach(PlayToken observer) {
		observers.add(observer);
	}
	
	public void  dettach(PlayToken observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).act();
		}
	}
}

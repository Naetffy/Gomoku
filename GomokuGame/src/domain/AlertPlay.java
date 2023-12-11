package domain;

import java.util.ArrayList;

/**
 * The AlertPlay class represents a mechanism for managing and notifying observers (PlayToken instances)
 * when a specific alert condition is met.
 */
public class AlertPlay {

    // Flag to indicate if the observer list is currently being iterated
	private static boolean iterating = false;
	
    // List to store the registered observers
	private static ArrayList<PlayToken> observers =  new ArrayList<>();
	
    // List to store observers that need to be removed after the iteration
	private static ArrayList<PlayToken> toRemove =  new ArrayList<>();
	
	/**
     * Attaches a PlayToken observer to the list.
     *
     * @param observer The PlayToken observer to be attached.
     */
	public static void  attach(PlayToken observer) {
		observers.add(observer);
	}
	
	
	/**
     * Detaches a PlayToken observer from the list. If the list is currently being iterated,
     * the observer is marked for removal and will be removed after the iteration is complete.
     *
     * @param observer The PlayToken observer to be detached.
     */
	public static void  dettach(PlayToken observer) {
		if(!iterating) {
			observers.remove(observer);
		}
		else {
			toRemove.add(observer);
		}
	}
	
	
	/**
     * Notifies all registered observers by calling their 'act' method. During the iteration,
     * observers marked for removal are skipped. After the iteration, the marked observers are removed.
     */
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
	
	
	/**
     * Removes observers marked for removal from the list.
     */
	public static void toRemove() {
		for(PlayToken c : toRemove) {
			dettach(c);
		}
		toRemove = new ArrayList<>();
	}

	
	 /**
     * Detaches all observers from the list.
     */
	public static void dettachAll() {
		observers = new ArrayList<>();
	}
}

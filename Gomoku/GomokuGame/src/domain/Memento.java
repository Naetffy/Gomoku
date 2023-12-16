package domain;

public class Memento {
	private Game state;
	
	public Memento(Game state) {
		this.state = state;  
	}
	
	public Game getSavedState() {
		return state;	
	}
	
}

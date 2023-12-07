package domain;

public class GomokuException extends Exception {
	
	public static final String INVALID_GAME_SIZE = "The game should have a size upper than 10.";
	
	
	public GomokuException(String message) {
		super(message);
	}
}

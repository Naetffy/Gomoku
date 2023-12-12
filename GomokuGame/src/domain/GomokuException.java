package domain;


/*
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public class GomokuException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String INVALID_GAME_SIZE = "The game should have a size upper than 10.";
	
	
	public GomokuException(String message) {
		super(message);
	}
}

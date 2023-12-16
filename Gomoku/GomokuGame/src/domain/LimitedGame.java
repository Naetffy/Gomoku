package domain;


/**
 * The LimitedGame class represents a specialized type of Game with limited token quantities.
 * It extends the Game class and overrides the start() method to initialize the game with a specific
 * distribution of normal and special tokens between two players.
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public class LimitedGame extends Game {

	
	/**
     * Constructs a LimitedGame with the specified size.
     *
     * @param size The size of the game board.
     */
	public LimitedGame(int size) {
		super(size);
	}

	
	/**
     * Overrides the start() method of the Game class. Initializes the game with a specific distribution
     * of normal and special tokens between two players based on the predefined percentage of special tokens.
     */
	public void start() {
		super.start();
	}
}

package domain;


/**
 * The NormalGame class represents a specific implementation of the Game interface for a normal Gomoku game.
 * In a normal game, players have a set quantity of normal tokens, with a specified percentage of special tokens.
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public class NormalGame extends Game {

	/**
     * Constructs a NormalGame object with the specified size and percentage of special elements.
     *
     * @param size               The size of the game board, indicating the dimensions.
     * @param especialPercentage The percentage of special elements in the game.
     */
	public NormalGame(int size) {
		super(size);
	}

	
	/**
     * Initializes and starts a normal Gomoku game by distributing normal and special tokens to players.
     * The number of normal and special tokens is determined based on the size of the game board
     * and the specified percentage of special elements.
     */
	public void start() {
    	numTokens = size * size;

		super.start();
	
	}

}

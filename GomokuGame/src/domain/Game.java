package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

/**
 * The abstract Game class defines the common structure and behavior for Gomoku games.
 * Subclasses must extend this class and implement the abstract methods to create specific games.
 * @author mateo forero fuentes and juan daniel murcia
 * @version 1.5.2
 */
public abstract class Game {

	private Board board;
	protected int size;
	protected int numTokens;
	protected int timeLimit;
	protected int especialPercentageTokens;
	protected Player playerOne;
	protected Player playerTwo;
	private String winner;
	private int turn;
	public static Set<Class> subTypes = null;
	
	/**
     * Retrieves a set of all classes that extends the Game class within the "domain" package.
     *
     * @return A set of Class objects representing the game subtypes.
     */
	public static Set<Class> getGameSubtypes() {
		if (subTypes == null) {
			Reflections reflections = new Reflections("domain");
	        Set subTypesGet = reflections.getSubTypesOf(Game.class);
	        subTypes = (Set<Class>)subTypesGet;
		}
        return subTypes;
    }

	/**
	 * Constructs a Game object with the specified size and percentage of special elements.
	 *
	 * @param size               The size of the game board, indicating the dimensions.
	 * @param especialPercentage The percentage of special elements in the game.
	 */
	public Game(int size) {
		this.size = size;
		this.board = new Board(size);
		this.board.setGame(this);
		turn = 0;
		winner = null;
	}
	
	/**
	 * Sets the players for the game based on the specified player types.
	 *
	 * @param typePlayer1 The type of player 1, represented as a String.
	 * @param typePlayer2 The type of player 2, represented as a String.
	 * @throws ClassNotFoundException    If the specified player type classes are not found.
	 * @throws InstantiationException    If an instance of the player type cannot be created (abstract class or interface).
	 * @throws IllegalAccessException    If the player type's constructor is not accessible due to access modifiers.
	 * @throws IllegalArgumentException  If the provided arguments are not valid for the player type's constructor.
	 * @throws InvocationTargetException If an exception occurs while invoking the player type's constructor.
	 * @throws NoSuchMethodException     If a matching method is not found in the specified player type class.
	 * @throws SecurityException         If a security violation occurs during reflection.
	 */
	public void setPlayers(String typePlayer1, String typePlayer2)
	        throws ClassNotFoundException, InstantiationException, IllegalAccessException,
	        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
	    String type = "domain." + typePlayer1 + "Player";
	    Class<?> clazz = Class.forName(type);
	    Constructor<?> constructor = clazz.getConstructor();
	    Object playerInstance = constructor.newInstance();
	    playerOne = (Player) playerInstance;

	    type = "domain." + typePlayer2 + "Player";
	    clazz = Class.forName(type);
	    constructor = clazz.getConstructor();
	    playerInstance = constructor.newInstance();
	    playerTwo = (Player) playerInstance;
	    
	    playerOne.setGame(this);
	    playerTwo.setGame(this);
	    
	}
	
	/**
	 * Sets the information for players in the game, including names and colors.
	 *
	 * @param nameOne  The name of player one.
	 * @param color1   The color associated with player one.
	 * @param nameTwo  The name of player two.
	 * @param color2   The color associated with player two.
	 */
	public void setPlayersInfo(String nameOne, Color color1, String nameTwo, Color color2) {
		playerOne.setInfo(nameOne, color1);
		playerTwo.setInfo(nameTwo, color2);
	}
	
	/**
	 * Sets the number of tokens that will have the players
	 * 
	 * @param numTokens The number of tokens for the game
	 */
	public void setNumTokens(int numTokens) {
		this.numTokens = numTokens;
	}
	
	/**
	 * Sets the limit time that will have the players
	 * 
	 * @param timeLimit the limit in seconds of the players -1 for inifinite time
	 * 
	 */
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public void setEspecialInfo(int especialPercentageTokens, int especialPercentageSquares) {
		board.setEspecialPercentageSquares(especialPercentageSquares);
		this.especialPercentageTokens = especialPercentageTokens;
		start();
	}
	
	public ArrayList<Object> play() {
		ArrayList<Object> info;
		if ((turn % 2) == 0) {	
			info = playerOne.play();
		} else {
			info = playerTwo.play();
		}
		return info;
	}
	/**
	 * Plays a move in the game by placing a token at the specified row and column for the current player.
	 * The player's turn alternates between Player One and Player Two.
	 *
	 * @param token  The player's token to be placed on the game board.
	 * @param row    The row where the player wants to place the token.
	 * @param column The column where the player wants to place the token.
	 */
	public void play(String token, int row, int column) {
		if (board.verify(row, column) && board.getTokenColor(row, column) == null) {
			if ((turn % 2) == 0) {	
				playerOne.play(token, row, column);
			} else {
				playerTwo.play(token, row, column);
			}
		}
	}

	/**
	 * Plays the specified token at the given row and column on the game board.
	 *
	 * @param token  The token to be placed on the game board.
	 * @param row    The row where the token will be placed.
	 * @param column The column where the token will be placed.
	 */
	public void playToken(Token token,int row, int column) {
		board.playToken(token,row,column);
	}
	
	/**
	 * Sets the specified token at the given row and column on the game board.
	 *
	 * @param token  The token to be placed on the game board.
	 * @param row    The row where the token will be placed.
	 * @param column The column where the token will be placed.
	 */
	public void setToken(Token token,int row, int column) {
		board.setToken(token,row,column);
	}
	
	/**
	 * Retrieves the game board associated with the current game instance.
	 *
	 * @return The Board object representing the game board.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Retrieves the current turn number in the game.
	 *
	 * @return The current turn number.
	 */
	public int getTurn() {
		return turn;
	}
	
	/**
	 * Sets the size of the game board to the specified value.
	 *
	 * @param size The new size of the game board.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	
	/**
	 * Retrieves the size of the game board.
	 *
	 * @return The size of the game board.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Retrieves the name of the player who won the game.
	 *
	 * @return The name of the winner or null if there is no winner yet.
	 */ 
	public String getWinner() {
		return winner;
	}
	
	
	/**
	 * Sets the winner of the game based on the last move's coordinates.
	 *
	 * @param row    The row of the last move.
	 * @param column The column of the last move.
	 */
	public void setWinner(int row,int column) {
		String player = null;
		if ((turn % 2) != 0) 
			player = playerOne.getName();
		else 
			player = playerTwo.getName();
		if(board.validate(row, column))
			winner = player;
	}

	/**
	 * Retrieves information about player one in the game.
	 *
	 * @return The Player object representing player one.
	 */
	public Player getPlayerOne() {
		return playerOne;
	}

	/**
	 * Retrieves information about player two in the game.
	 *
	 * @return The Player object representing player two.
	 */
	public Player getPlayerTwo() {
		return playerTwo;
	}

	/**
	 * Retrieves the color of the token at the specified row and column on the game board.
	 *
	 * @param row    The row coordinate of the token.
	 * @param column The column coordinate of the token.
	 * @return The color of the token.
	 */
	public Color getTokenColor(int row, int column) {
		return board.getTokenColor(row, column);
	}

	/**
	 * Retrieves the map of player tokens for the current player's turn.
	 *
	 * @return A HashMap containing player names and their corresponding token counts.
	 */
	public HashMap<String, Integer> getPlayerTokens() {
		HashMap<String, Integer> res;
		if ((turn % 2) != 0) {
			res = playerOne.getMap();
		} else {
			res = playerTwo.getMap();
		}
		return res;
	}

	/**
	 * Retrieves the set of token subtypes used by player one in the game.
	 *
	 * @return A set containing the classes of token subtypes used by player one.
	 */
	public Set<Class> getTokenSubtypes() {
		return playerOne.getTokenSubtypes();
	}
	
	/**
	 * Abstract method to be implemented by subclasses of Game.
	 * Initiates the game setup based on the specified special percentage.
	 *
	 * @param especialPercentage The percentage of special elements in the game.
	 */	
	protected abstract void start();

	public Square getSquare(int i, int j) {
		return board.getSquare(i,j);
	}

	public void increaseTurn() {
		turn+=1;
	}
	public void decreaseTurn() {
		turn-=1;
	}

	public void incresePlayerQuantity(String name, int i) {
		if (turn%2 == 0) {
			playerOne.increaseQuantityToken(name, i);
		}
		else {
			playerTwo.increaseQuantityToken(name, i);
		}
	}
}

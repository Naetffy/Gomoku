package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * The Gomoku class represents a game of Gomoku, a two-player strategy board game.
 * It manages the game state, player information, and provides access to various
 * aspects of the ongoing Gomoku game.
 * 
 * @author mateo forero fuentes and juan daniel murcia
 * @version 1.5.2
 */
public class Gomoku {

	private Game game;

	private static Gomoku gomokuSingleton = null;
	
	/**
	 * Retrieves the singleton instance of the Gomoku class.
	 *
	 * @return The singleton instance of the Gomoku class.
	 */
	public static Gomoku getGomoku() {
		return gomokuSingleton;
	}

	/**
	 * Constructs a Gomoku object based on the specified game type, and size.
	 *
	 * @param gameType           The type of the game, represented as a String.
	 * @param size               The size of the game, indicating the dimensions.
	 * @throws ClassNotFoundException    If the specified game type class is not found.
	 * @throws NoSuchMethodException     If a matching constructor is not found in the specified class.
	 * @throws SecurityException         If a security violation occurs during reflection.
	 * @throws InstantiationException    If an instance of the class cannot be created (abstract class or interface).
	 * @throws IllegalAccessException    If the constructor is not accessible due to access modifiers.
	 * @throws IllegalArgumentException  If the provided arguments are not valid for the constructor.
	 * @throws InvocationTargetException If an exception occurs while invoking the constructor.
	 * @throws GomokuException           If the given size is less than 10
	 */
	public Gomoku(String gameType, int size)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, GomokuException {
		if (size < 10) throw new GomokuException(GomokuException.INVALID_GAME_SIZE);
		String type = "domain." + gameType + "Game";
		Class<?> clazz = Class.forName(type);
		Constructor<?> constructor = clazz.getConstructor(int.class);
		Object gameInstance = constructor.newInstance(size);
		game = (Game) gameInstance;
		AlertPlay.dettachAll();	
		gomokuSingleton = this;
	}

	/**
	 * Sets the players for the Gomoku game based on the specified player types.
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
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		game.setPlayers(typePlayer1, typePlayer2);
	}

	/**
	 * Sets the information for players in the Gomoku game, including names and colors.
	 *
	 * @param player1 The name of player 1.
	 * @param color1  The color associated with player 1.
	 * @param player2 The name of player 2.
	 * @param color2  The color associated with player 2.
	 */
	public void setPlayersInfo(String player1, Color color1, String player2, Color color2) {
		game.setPlayersInfo(player1, color1, player2, color2);
	}
	
	/**
	 * Sets the especial info in the Gomoku game, including the especial percentage for tokens or squares.
	 *
	 * @param especialPercentageTokens the percentage of especial tokens for the players
	 * @param especialPercentageSquares the percentage of especial squares for the board
	 */
	public void setEspecialInfo(int especialPercentageTokens, int especialPercentageSquares) {
		game.setEspecialInfo(especialPercentageTokens, especialPercentageSquares);
	}
	
	/**
	 * Sets the limits of time an tokens that will have the players
	 * 
	 * @param numTokens The number of tokens for the game
	 */
	public void setLimits(int numTokens,int timeLimit) {
		game.setNumTokens(numTokens);
		game.setTimeLimit(timeLimit);
	}
	
	

	public void play() {
		ArrayList<Object> info = game.play();
		if (info != null) {
			game.play((String)info.get(0),(int)info.get(1), (int)info.get(2));
			play();
		}
	}
	/**
	 * Plays a move in the Gomoku game by placing a token at the specified row and column.
	 *
	 * @param token  The player's token to be placed on the game board.
	 * @param row    The row where the player wants to place the token.
	 * @param column The column where the player wants to place the token.
	 */
	public void play(String token, int row, int column) {
		game.play(token, row, column);
	}
	
	/**
	 * Retrieves the size of the Gomoku game board.
	 *
	 * @return The size of the game board.
	 */
	public int getSize() {
		return game.getSize();
	}

	/**
	 * Retrieves the underlying game instance.
	 *
	 * @return The instance of the underlying game.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Retrieves the color of the token at the specified row and column on the game board.
	 *
	 * @param row    The row coordinate of the token.
	 * @param column The column coordinate of the token.
	 * @return The color of the token.
	 */
	public Color getTokenColor(int row, int column) {
		return game.getTokenColor(row, column);
	}

	/**
	 * Retrieves the winner of the Gomoku game.
	 *
	 * @return The name of the winner, or null if there is no winner yet.
	 */
	public String getWinner() {
		return game.getWinner();
	}

	/**
	 * Retrieves a map containing player names and their corresponding token counts.
	 *
	 * @return A map of player names to token counts.
	 */
	public HashMap<String, Integer> getPlayerTokens() {
		return game.getPlayerTokens();
	}

	/**
	 * Retrieves the information of player one.
	 *
	 * @return The Player object representing player one.
	 */
	public Player getPlayerOne() {
		return game.getPlayerOne();
	}

	/**
	 * Retrieves the information of player two.
	 *
	 * @return The Player object representing player two.
	 */
	public Player getPlayerTwo() {
		return game.getPlayerTwo();
	}
	/**
	 * Retrieves the current turn number in the Gomoku game.
	 *
	 * @return The current turn number.
	 */
	public int getTurn() {
		return game.getTurn();
	}
	

	/**
	 * Retrieves the set of token subtypes used in the Gomoku game.
	 *
	 * @return A set containing the classes of token subtypes.
	 */
	public Set<Class> getTokenSubtypes() {
		return game.getTokenSubtypes();
	}

	public Square getSquare(int i, int j) {
		return game.getSquare(i,j);
	}

}
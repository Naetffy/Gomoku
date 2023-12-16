package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

/**
 * Abstract class representing a player in the game.
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public abstract class Player {
    private String name;
    protected Color color;
    protected HashMap<String, Integer> quantities;
    protected Game game;

    // Static variable to store player subtypes
    public static Set<Class<? extends Player>> subTypes = null;

    /**
     * Retrieves the set of player subtypes using reflection.
     *
     * @return The set of player subtypes.
     */
    public static Set<Class<? extends Player>> getPlayerSubtypes() {
        if (subTypes == null) {
            Reflections reflections = new Reflections("domain");
            Set<Class<? extends Player>> subTypesGet = reflections.getSubTypesOf(Player.class);
            subTypes = subTypesGet;
        }
        return subTypes;
    }

    /**
     * Constructor for the Player class.
     */
    public Player() {
        quantities = new HashMap<>();
    }

    /**
     * Sets the name and color information for the player.
     *
     * @param playerName The name of the player.
     * @param color      The color associated with the player.
     */
    public void setInfo(String playerName, Color color) {
        name = playerName;
        this.color = color;
    }

    /**
     * Sets the quantity of a specific type of token for the player.
     *
     * @param tokenName The name of the token type.
     * @param quantity  The quantity to set.
     */
    public void setQuantityTypeOfToken(String tokenName, int quantity) {
        quantities.put(tokenName, quantity);
    }

    /**
     * Increases the quantity of a specific type of token for the player.
     *
     * @param tokenName The name of the token type.
     * @param increase  The quantity to increase.
     */
    public void increaseQuantityToken(String tokenName, int increase) {
        quantities.put(tokenName, quantities.get(tokenName) + increase);
    }
	
	    /**
	     * Retrieves the name of the player.
	     *
	     * @return The name of the player.
	     */
	    public String getName() {
	        return name;
	    }

    /**
     * Sets the associated game for the player.
     *
     * @param game The game to associate with the player.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Retrieves the map of token quantities associated with the player.
     *
     * @return The map of token quantities.
     */
    public HashMap<String, Integer> getMap() {
        return quantities;
    }

    /**
     * Initiates the player's move with the specified type of token at the given row and column.
     *
     * @param typeToken The type of token to play.
     * @param row       The row to place the token.
     * @param column    The column to place the token.
     */
    public void play(String typeToken, int row, int column) {
        String type = "domain." + typeToken;
        Class<?> clazz;
        try {
            int quantity = quantities.get(typeToken);
            if (quantity > 0) {
                clazz = Class.forName(type);
                Constructor<?> constructor = clazz.getConstructor(Color.class, int.class, int.class);
                Object tokenInstance = constructor.newInstance(color, row, column);
                Token actualToken = (Token) tokenInstance;
                actualToken.setPlayer(this);
                quantities.put(typeToken, quantity - 1);
                game.playToken(actualToken, row, column);
                game.setWinner(actualToken.getRow(), actualToken.getColumn());
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the token at the specified row and column.
     *
     * @param row    The row coordinate of the token.
     * @param column The column coordinate of the token.
     */
    public void deleteToken(int row, int column) {
        game.setToken(null, row, column);
    }

    /**
     * Retrieves the set of token subtypes associated with the player.
     *
     * @return The set of token subtypes.
     */
    public Set<Class<? extends Token>> getTokenSubtypes() {
        return Token.getTokenSubtypes();
    }

    /**
     * Abstract method to be implemented by player subclasses for playing the move.
     *
     * @return An ArrayList containing player move information.
     */
    public abstract ArrayList<Object> play();

    /**
     * Increases the turn count in the game.
     */
    public void increaseTurn() {
        game.increaseTurn();
    }

    /**
     * Decreases the turn count in the game.
     */
   
	public void decreaseTurn() {
		game.decreaseTurn();
	}
	
	public void setColor(Color color) {
	    this.color = color;
	}
}

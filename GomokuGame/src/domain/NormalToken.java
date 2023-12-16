package domain;

import java.awt.Color;

/**
 * Represents a normal token used in the game. Extends the Token class.
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public class NormalToken extends Token {

    /**
     * Constructs a NormalToken object with the specified color, row, and column coordinates.
     *
     * @param color  The color of the token.
     * @param row    The row coordinate of the token.
     * @param column The column coordinate of the token.
     */
    public NormalToken(Color color, int row, int column) {
        super(color, row, column);
    }

    /**
     * Performs the action associated with a normal token. Detaches the token from alert observers.
     */
    public void act() {
        // Detach from alert observers
        AlertPlay.dettach(this);
    }
}

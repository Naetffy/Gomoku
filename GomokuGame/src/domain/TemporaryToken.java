package domain;

import java.awt.Color;

/**
 * The TemporaryToken class represents a special type of token in a Gomoku game
 * that disappears from the board after a certain number of turns.
 * It extends the Token class and overrides the 'act()' method to implement its unique behavior.
 * The token is attached to a player, and its existence is limited to a specified number of turns.
 *
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public class TemporaryToken extends Token {

    private Integer creationTurn;

    /**
     * Constructs a TemporaryToken with the specified color, row, and column.
     *
     * @param color  The color of the token.
     * @param row    The row index of the token on the game board.
     * @param column The column index of the token on the game board.
     */
    public TemporaryToken(Color color, int row, int column) {
        super(color, row, column);
    }

    /**
     * Performs the action associated with the temporary token.
     * If the creation turn is not set, it sets it to the current turn.
     * Otherwise, it checks whether the token should disappear after a certain number of turns and detaches it.
     */
    @Override
    public void act() {
        if (creationTurn == null) {
            creationTurn = Gomoku.getGomoku().getTurn();
        } else {
            if (creationTurn + 3 == Gomoku.getGomoku().getTurn()) {
                AlertPlay.dettach(this);
                player.deleteToken(row, column);
            }
        }
    }
}

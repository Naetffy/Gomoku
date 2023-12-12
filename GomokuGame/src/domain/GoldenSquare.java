package domain;

import java.util.Random;
import java.util.Set;

/**
 * The GoldenSquare class represents a square on the game board that, when activated,
 * has a special effect on the game. It implements the PlayToken interface.
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public class GoldenSquare extends Square implements PlayToken {

    private Integer creationTurn;

    /**
     * Constructs a GoldenSquare with the specified board, row, and column.
     *
     * @param board  The game board to which the GoldenSquare belongs.
     * @param row    The row index of the GoldenSquare on the board.
     * @param column The column index of the GoldenSquare on the board.
     */
    public GoldenSquare(Board board, int row, int column) {
        super(board, row, column);
    }

    /**
     * Performs the special action associated with the GoldenSquare when activated.
     * If it's the first activation, it sets the token and increases the quantity of a randomly selected
     * special token for the current player. If it's the second activation (two turns later), it detaches
     * the square from the alert play and decreases the turn count.
     */
    public void act() {
        if (creationTurn == null) {
            creationTurn = board.getTurn();
            setToken(token);
            Set<Class<? extends Token>> tokens = Token.getTokenSubtypes();
            Random r = new Random();
            int rn = r.nextInt(tokens.size());
            int i = 0;
            for (Class<? extends Token> token : tokens) {
                String name = token.getSimpleName();
                if (i == rn) {
                    if (!name.equals("NormalToken")) {
                        AlertPlay.dettach(this);
                        creationTurn = null;
                    }
                    board.increasePlayerQuantity(name, 1);
                    break;
                }
                i++;
            }
            board.increaseTurn();
        } else {
            if (creationTurn + 2 == board.getTurn()) {
                AlertPlay.dettach(this);
                board.decreaseTurn();
            }
        }
    }
}

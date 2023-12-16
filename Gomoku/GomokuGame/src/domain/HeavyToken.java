package domain;



import java.awt.Color;


/**
 * The HeavyToken class represents a specialized type of Token with additional behavior.
 * It extends the Token class and overrides the act() method to perform a specific action
 * when triggered by an observer.
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public class HeavyToken extends Token {

	
	/**
     * Constructs a HeavyToken with the specified color, row, and column.
     *
     * @param color  The color of the HeavyToken.
     * @param row    The row position of the HeavyToken on the game board.
     * @param column The column position of the HeavyToken on the game board.
     */
	public HeavyToken(Color color, int row, int column) {
		super(color, row, column);
		this.value=2;
		
	}

	
	/**
     * Overrides the act() method of the Token class. Detaches itself from the observer list,
     * shuffles the order of possible moves, and attempts to expand by playing a NormalToken in
     * one of the shuffled directions. If successful, updates the player's token count and decreases the turn count.
     */

	public void act() {
		AlertPlay.dettach(this);
		/*
		ArrayList<Integer> order = new ArrayList<>();
		boolean expanded = false;
		int[][] moves = {{0,1},{1,0},{-1,0},{0,-1}};
		order.add(0);order.add(1);order.add(2);order.add(3);
		Random r = new Random();
		int rn = r.nextInt(10); 
		while (rn > 0) {
			Collections.shuffle(order);
			rn--;
		}
		for (int i = 0;i < 4;i++) {
			try {
				player.play("NormalToken", row + moves[order.get(i)][0], column + moves[order.get(i)][1]);
				expanded = true;
				break;
			} catch (Exception e) {
			}
		}
		if (expanded) {
			player.setQuantityTypeOfToken("NormalToken", 1+player.getMap().get("NormalToken"));
			player.decreaseTurn();
		}
		 */
	}

	public boolean valid(Token token) throws GomokuException {
		if (token == null) {
			return true;
		}
		throw new GomokuException(GomokuException.INVALID_MOVE_OVERLAP);
	}
}

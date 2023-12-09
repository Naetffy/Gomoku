package domain;

import java.util.Random;
import java.util.Set;

public class GoldenSquare extends Square implements PlayToken {
	private Integer creationTurn;
	
	public GoldenSquare(Board board,int row, int column) {
		super(board,row,column);
	}

	public void act() {
		
		if (creationTurn == null) {
			creationTurn = board.getTurn();
			setToken(token);
			Set<Class>tokens = Token.getTokenSubtypes();
			Random r = new Random();
			int rn = r.nextInt(tokens.size());
			int i = 0;
			for (Class token : tokens) {
				String name = token.getSimpleName();
				if(i == rn) {
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
		}
		else {
			if (creationTurn+2 == board.getTurn()) {
				AlertPlay.dettach(this);
				board.decreaseTurn();
			}
		}
	}
}

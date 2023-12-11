package domain;

import java.awt.Color;
import java.util.Random;


/**
 * The LimitedGame class represents a specialized type of Game with limited token quantities.
 * It extends the Game class and overrides the start() method to initialize the game with a specific
 * distribution of normal and special tokens between two players.
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
		int numSpecials = (numTokens) * especialPercentageTokens / 100;
		int num = numTokens - numSpecials;
		
		playerOne.setQuantityTypeOfToken("NormalToken", num);
		playerTwo.setQuantityTypeOfToken("NormalToken", num);
		Random random = new Random();
		String lastName = null;
		int lastSum=0;
		num = 0;
		for (Class typeOfToken : Token.getTokenSubtypes()) {
			String tokenName = typeOfToken.getSimpleName();
			if (!tokenName.equals("NormalToken") && numSpecials != 0) 
				num = random.nextInt(numSpecials);
			if(!tokenName.equals("NormalToken")) {
				playerOne.setQuantityTypeOfToken(tokenName, num);
				playerTwo.setQuantityTypeOfToken(tokenName, num);
				numSpecials -= num;
				lastName = tokenName;
				lastSum = num;
			}
		}
		if (numSpecials != 0) {
			playerOne.setQuantityTypeOfToken(lastName, numSpecials+lastSum);
			playerTwo.setQuantityTypeOfToken(lastName, numSpecials+lastSum);
		}
	}
}

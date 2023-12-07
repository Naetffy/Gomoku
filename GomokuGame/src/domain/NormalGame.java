package domain;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;


/**
 * The NormalGame class represents a specific implementation of the Game interface for a normal Gomoku game.
 * In a normal game, players have a set quantity of normal tokens, with a specified percentage of special tokens.
 * @author mateo forero fuentes and juan daniel murcia
 * @version 1.5.2
 */
public class NormalGame extends Game {

	/**
     * Constructs a NormalGame object with the specified size and percentage of special elements.
     *
     * @param size               The size of the game board, indicating the dimensions.
     * @param especialPercentage The percentage of special elements in the game.
     */
	public NormalGame(int size, int especialPercentage) {
		super(size, especialPercentage);
	}

	public void start(int especialPercentage) {
		numTokens = size*size;
		int numSpecials = (numTokens) * especialPercentage / 100;
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

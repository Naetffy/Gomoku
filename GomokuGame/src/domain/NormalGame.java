package domain;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;

public class NormalGame extends Game {

	public NormalGame(int size, int especialPercentage) {
		super(size, especialPercentage);
	}

	public void start(int especialPercentage) {
		int numSpecials = (size * size) * especialPercentage / 100;
		int num = size*size - numSpecials;
		playerOne.setQuantityTypeOfToken("Normal", num);
		playerTwo.setQuantityTypeOfToken("Normal", num);
		Random random = new Random();
		String lastName = null;
		for (Class typeOfToken : Token.getTokenSubtypes()) {
			String tokenName = typeOfToken.getSimpleName();
			if (numSpecials != 0) {
				num = random.nextInt(numSpecials);
				numSpecials -= num;
			}
			if(!tokenName.equals("Normal")) {
				playerOne.setQuantityTypeOfToken(tokenName, num);
				playerTwo.setQuantityTypeOfToken(tokenName, num);
				lastName = tokenName;
			}
		}
		if (numSpecials != 0) {
			playerOne.setQuantityTypeOfToken(lastName, numSpecials);
			playerTwo.setQuantityTypeOfToken(lastName, numSpecials);
		}
		
	}

}

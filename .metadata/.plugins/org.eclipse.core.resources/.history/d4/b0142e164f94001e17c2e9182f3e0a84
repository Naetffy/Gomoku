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

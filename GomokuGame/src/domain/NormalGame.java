package domain;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;

public class NormalGame extends Game{
	
	public NormalGame(int size,int especialPercentage) {
		super(size,especialPercentage);
	}
	
	public void start(int especialPercentage) {
		int numSpecials = (size*size)*especialPercentage/100;
		Random random = new Random();
		playerOne.setTypeOfToken("Normal", size*size-numSpecials);
		playerTwo.setTypeOfToken("Normal", size*size-numSpecials);
		int num = 0;
		if (numSpecials!=0) num = random.nextInt(numSpecials);
		playerOne.setTypeOfToken("Heavy", num);
		playerTwo.setTypeOfToken("Heavy", num);
		playerOne.setTypeOfToken("Temporary", numSpecials-num);
		playerTwo.setTypeOfToken("Temporary", numSpecials-num);
		
	}

	
}

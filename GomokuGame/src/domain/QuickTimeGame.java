package domain;
 
import java.awt.Color;
import java.util.Random;
 
 
public class QuickTimeGame extends Game {
	
	private Thread timerThread1;
	private Thread timerThread2;
	private int timePlayerOne;
	private int timePlayerTwo;
 
	
	public QuickTimeGame(int size) {
		
		super(size);
	}
	
	public void start() {
		numTokens = size*size;
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
		if (timeLimit >= 0) {
			timePlayerOne = timeLimit;
			timePlayerTwo = timeLimit;
			timerThread1 = createTimerThread(timePlayerOne);
			timerThread1.start();
			timerThread1 = createTimerThread(timePlayerOne);
		}
	}
		
 
	private Thread createTimerThread(int seconds) {
		return new Thread(() -> {
			int seconds1 = seconds;
			int creationTurn = turn;
			while (seconds1 > 0 && turn == creationTurn ) {
 
				try {
					Thread.sleep(1000);
					System.out.println(seconds1);
					seconds1--;
				} catch (Exception e) {
					System.out.println(e);
				} 		
			}
			if (seconds <= 0 && getWinner() == null) {
				if (timePlayerOne <= 0) {
		            	winner = playerTwo.getName();
					} else if (timePlayerTwo <= 0){
						winner = playerOne.getName();
					}
				} 
			
			if (turn % 2 != 0) {
				timePlayerOne = seconds1;
			} else {
				timePlayerTwo = seconds1;
			}
		});
	}
 
	@Override
	public void play(String token, int row, int column) {
		super.play(token, row, column);
		if (turn %2 != 0) {
			timerThread2 = createTimerThread(timePlayerTwo);
			timerThread2.start();
 
		} else {
			timerThread1 = createTimerThread(timePlayerOne);
			timerThread1.start();
			
		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Cambio de jugador");
	}
	
}
	
package domain;

import java.util.Random;

/**
 * The QuickTimeGame class represents a specific implementation of the Game interface for a Gomoku game
 * with a quick time limit for each player's turn.
 *
 * @author [Author Names]
 * @version [Version Number]
 */
public class QuickTimeGame extends Game {

    private Thread timerThread1;
    private Thread timerThread2;
    private int timePlayerOne;
    private int timePlayerTwo;

    /**
     * Constructs a QuickTimeGame object with the specified size and initializes the game with a quick time limit.
     *
     * @param size The size of the game board, indicating the dimensions.
     */
    public QuickTimeGame(int size) {
        super(size);
    }

    /**
     * Initializes and starts a QuickTime Gomoku game by distributing normal and special tokens to players
     * and setting up the quick time limit for each player's turn.
     */
    public void start() {
        numTokens = size * size;
        int numSpecials = (numTokens) * especialPercentageTokens / 100;
        int num = numTokens - numSpecials;
        playerOne.setQuantityTypeOfToken("NormalToken", num);
        playerTwo.setQuantityTypeOfToken("NormalToken", num);
        Random random = new Random();
        String lastName = null;
        int lastSum = 0;
        num = 0;

        // Distribute special tokens to players
        for (Class typeOfToken : Token.getTokenSubtypes()) {
            String tokenName = typeOfToken.getSimpleName();
            if (!tokenName.equals("NormalToken") && numSpecials != 0)
                num = random.nextInt(numSpecials);

            // Set the quantity of each token type for both players
            if (!tokenName.equals("NormalToken")) {
                playerOne.setQuantityTypeOfToken(tokenName, num);
                playerTwo.setQuantityTypeOfToken(tokenName, num);
                numSpecials -= num;
                lastName = tokenName;
                lastSum = num;
            }
        }

        // If there are remaining special tokens, distribute them to the last token type
        if (numSpecials != 0) {
            playerOne.setQuantityTypeOfToken(lastName, numSpecials + lastSum);
            playerTwo.setQuantityTypeOfToken(lastName, numSpecials + lastSum);
        }

        // Set up quick time limit for each player's turn
        if (timeLimit >= 0) {
            timePlayerOne = timeLimit;
            timePlayerTwo = timeLimit;
            timerThread1 = createTimerThread(timePlayerOne);
            timerThread1.start();
            timerThread2 = createTimerThread(timePlayerTwo);
        }
    }

    private Thread createTimerThread(int seconds) {
        return new Thread(() -> {
            int seconds1 = seconds;
            int creationTurn = turn;
            while (seconds1 > 0 && turn == creationTurn) {
                try {
                    Thread.sleep(1000);
                    System.out.println(seconds1);
                    seconds1--;
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }

            if (seconds <= 0 && getWinner() == null) {
                if (timePlayerOne <= 0) {
                    winner = playerTwo.getName();
                } else if (timePlayerTwo <= 0) {
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

    /**
     * Overrides the play method to incorporate the timing mechanism for each player's turn.
     *
     * @param token  The type of token to be played.
     * @param row    The row index where the token is played.
     * @param column The column index where the token is played.
     */
    @Override
    public void play(String token, int row, int column) {
        super.play(token, row, column);
        if (turn % 2 != 0) {
            timerThread2 = createTimerThread(timePlayerTwo);
            timerThread2.start();
        } else {
            timerThread1 = createTimerThread(timePlayerOne);
            timerThread1.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("Change of player");
    }
}

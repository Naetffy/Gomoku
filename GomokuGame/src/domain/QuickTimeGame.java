package domain;

/**
 * The QuickTimeGame class represents a specific implementation of the Game interface for a Gomoku game
 * with a quick time limit for each player's turn.
 *
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
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
        super.start();
        // Set up quick time limit for each player's turn
        if (timeLimit >= 0) {
            timePlayerOne = timeLimit;
            timePlayerTwo = timeLimit;
            timerThread1 = createTimerThread(timePlayerOne);
            timerThread1.start();
        }
    }

    /**
     * Creates a timer thread with the specified time limit for a player's turn.
     *
     * @param seconds The time limit in seconds.
     * @return A timer thread.
     */
    private Thread createTimerThread(int seconds) {
        return new Thread(() -> {
            int remainingSeconds = seconds;
            int creationTurn = turn;

            while (remainingSeconds > 0 && turn == creationTurn) {
                try {
                    Thread.sleep(1000);
                    System.out.println(remainingSeconds);
                    remainingSeconds--;
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }

            if (remainingSeconds <= 0 && getWinner() == null) {
                if (timePlayerOne <= 0) {
                    winner = playerTwo.getName();
                } else if (timePlayerTwo <= 0) {
                    winner = playerOne.getName();
                }
            }

            if (turn % 2 != 0) {
                timePlayerOne = remainingSeconds;
            } else {
                timePlayerTwo = remainingSeconds;
            }

        });
    }

    /**
     * Overrides the play method from the Game class to handle the player's turn and update the timer threads.
     *
     * @param token  The type of token to play.
     * @param row    The row to place the token.
     * @param column The column to place the token.
     */
    @Override
    public void play(String token, int row, int column) {
        super.play(token, row, column);
        Thread currentTimerThread;
        if (turn % 2 != 0) currentTimerThread = timerThread1;
        else currentTimerThread = timerThread2;
        if (currentTimerThread != null && currentTimerThread.isAlive()) {
            try {
                currentTimerThread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread join interrupted: " + e);
            }
        }

        if (turn % 2 != 0) {
            timerThread2 = createTimerThread(timePlayerTwo);
            timerThread2.start();
        } else {
            timerThread1 = createTimerThread(timePlayerOne);
            timerThread1.start();
        }

        System.out.println("time playerOne: " + timePlayerOne);
        System.out.println("time playerTwo: " + timePlayerTwo);
    }
}

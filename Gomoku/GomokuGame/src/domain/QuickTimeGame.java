package domain;

import java.util.concurrent.Flow.Subscription;

/**
 * The QuickTimeGame class represents a specific implementation of the Game interface for a Gomoku game
 * with a quick time limit for each player's turn.
 *
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public class QuickTimeGame extends Game {
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
    }

    @Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(Integer item) {
		if (item < 0) {
			if (turn % 2 == 0) {
				winner = playerTwo.getName();
			}
			else {
				winner = playerOne.getName();
			}
		}
        subscription.request(1); // Solicitar más elementos después de recibir uno
	}

	@Override
    public void onError(Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("La publicación ha finalizado.");
    }
}

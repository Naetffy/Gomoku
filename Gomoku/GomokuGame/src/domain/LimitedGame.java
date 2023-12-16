package domain;

import java.util.concurrent.Flow.Subscription;

/**
 * The LimitedGame class represents a specialized type of Game with limited token quantities.
 * It extends the Game class and overrides the start() method to initialize the game with a specific
 * distribution of normal and special tokens between two players.
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public class LimitedGame extends Game {
	/*
     * Constructs a LimitedGame with the specified size.
     *
     * @param size The size of the game board.
     */
	public LimitedGame(int size) {
		super(size);
	}
	public void setTimeLimit(int timeLimit) {
		this.time = new Time(-1,this);
	}
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(Integer item) {
		System.out.println("Tiempo actual: " + item + " milisegundos");
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

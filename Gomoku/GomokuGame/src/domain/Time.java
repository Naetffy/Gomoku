package domain;

import java.io.Serializable;
import java.util.concurrent.SubmissionPublisher;

public class Time extends SubmissionPublisher<Integer> implements Runnable,Serializable{

	private int limitMili; 
	private Game game;
	private int deltaMili;
	private int timePlayerOneMili;
	private int timePlayerTwoMili;
	
	public Time(int limit,Game game) {
		this.limitMili = limit*1000;
		this.game = game;
		this.subscribe(game);
		if(this.limitMili == -1000) {
			this.limitMili = 0;
			deltaMili = 100;
		}
		else{
			deltaMili = -100;
		}
		timePlayerOneMili = this.limitMili;
		timePlayerTwoMili = this.limitMili;
	}
	@Override
	public void run() {
		while(game.getWinner() == null) {
			if(game.getTurn()%2 == 0) {
				while (game.getWinner() == null && game.getTurn()%2 == 0 && timePlayerOneMili >= 0) {
					try {
						Thread.sleep(Math.abs(deltaMili));
						timePlayerOneMili += deltaMili;
						if (timePlayerOneMili%1000 == 0) {
							this.submit(timePlayerOneMili);
						}
					} catch (InterruptedException e) {
					}
				}
			}
			else {
				while(game.getWinner() == null && game.getTurn()%2 != 0 && timePlayerTwoMili >= 0) {
					try {
						Thread.sleep(Math.abs(deltaMili));
						timePlayerTwoMili += deltaMili;
						if (timePlayerTwoMili%1000 == 0) {
							this.submit(timePlayerTwoMili);
						}
					} catch (InterruptedException e) {
					}
				}
			}
			if(timePlayerOneMili < 0) {
				this.submit(timePlayerOneMili);
			}
			if(timePlayerTwoMili < 0) {
				this.submit(timePlayerTwoMili);
			}
		}
	}

}

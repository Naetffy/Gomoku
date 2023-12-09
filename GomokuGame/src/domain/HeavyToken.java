package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class HeavyToken extends Token {

	public HeavyToken(Color color, int row, int column) {
		super(color, row, column);
	}

	public void act() {
		AlertPlay.dettach(this);
		ArrayList<Integer> order = new ArrayList<>();
		boolean expanded = false;
		int[][] moves = {{0,1},{1,0},{-1,0},{0,-1}};
		order.add(0);order.add(1);order.add(2);order.add(3);
		Random r = new Random();
		int rn = r.nextInt(10); 
		while (rn > 0) {
			Collections.shuffle(order);
			rn--;
		}
		for (int i = 0;i < 4;i++) {
			try {
				player.play("NormalToken", row + moves[order.get(i)][0], column + moves[order.get(i)][1]);
				expanded = true;
				break;
			} catch (Exception e) {
			}
		}
		if (expanded) {
			player.setQuantityTypeOfToken("NormalToken", 1+player.getMap().get("NormalToken"));
			player.decreaseTurn();
		}

	}

}

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
		AlertPlay alert = new AlertPlay();
		alert.dettach(this);
		ArrayList<Integer> order = new ArrayList<>();
		order.add(1);
		order.add(2);
		order.add(3);
		order.add(4);
		Collections.shuffle(order);
		boolean expanded = false;
		try {
			if (order.get(0) == 0) {
				player.play("NormalToken", row - 1, column);
				expanded = true;
			} else if (order.get(0) == 1) {
				player.play("NormalToken", row + 1, column);
				expanded = true;
			} else if (order.get(0) == 2) {
				player.play("NormalToken", row, column - 1);
				expanded = true;
			} else {
				player.play("NormalToken", row, column + 1);
				expanded = true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		if (!expanded) {
			try {
				if (order.get(1) == 0) {
					player.play("NormalToken", row - 1, column);
					expanded = true;
				} else if (order.get(1) == 1) {
					player.play("NormalToken", row + 1, column);
					expanded = true;
				} else if (order.get(1) == 2) {
					player.play("NormalToken", row, column - 1);
					expanded = true;
				} else {
					player.play("NormalToken", row, column + 1);
					expanded = true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {

			}
		}
		if (!expanded) {
			try {
				if (order.get(2) == 0) {
					player.play("NormalToken", row - 1, column);
					expanded = true;
				} else if (order.get(2) == 1) {
					player.play("NormalToken", row + 1, column);
					expanded = true;
				} else if (order.get(2) == 2) {
					player.play("NormalToken", row, column - 1);
					expanded = true;
				} else {
					player.play("NormalToken", row, column + 1);
					expanded = true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {

			}
		}
		if (!expanded) {
			try {
				if (order.get(3) == 0) {
					player.play("NormalToken", row - 1, column);
					expanded = true;
				} else if (order.get(3) == 1) {
					player.play("NormalToken", row + 1, column);
					expanded = true;
				} else if (order.get(3) == 2) {
					player.play("NormalToken", row, column - 1);
					expanded = true;
				} else {
					player.play("NormalToken", row, column + 1);
					expanded = true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {

			}
		}
		if (expanded) {
			player.setQuantityTypeOfToken("NormalToken", 1+player.getMap().get("NormalToken"));
		}

	}

}

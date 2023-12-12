package domain;

import java.awt.Color;
import java.util.Random;


/*
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public class Board {

	private int size;
	private Square[][] boardSquares;
	private Game game;

	/**
	 * Constructs a Board object with the specified size and percentage of special
	 * squares.
	 *
	 * @param size                      The size of the board, indicating the
	 *                                  dimensions.
	 * @param percentageEspecialSquares The percentage of special squares on the
	 *                                  board.
	 */
	public Board(int size) {
		this.size = size;
		boardSquares = new Square[size][size];
		boolean[][] visited = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				visited[i][j] = false;
				boardSquares[i][j] = Square.createSquareInstance(this, i, j, false);
			}
		}
	}

	/**
	 * Sets the percentage of special squares on the board based on the specified
	 * special percentage. Special squares are randomly placed on the board while
	 * considering the provided percentage.
	 *
	 * @param especialPercentageSquares The percentage of special squares to be
	 *                                  placed on the board.
	 */
	public void setEspecialPercentageSquares(int especialPercentageSquares) {
		// Calculate the number of special squares based on the size and percentage.
		int numEspecialSquares = ((size * size) * especialPercentageSquares) / 100;

		// Initialize a boolean array to keep track of visited squares during special
		// square placement.
		boolean[][] visited = new boolean[size][size];

		// Randomly place special squares on the board.
		Random random = new Random();
		while (numEspecialSquares > 0) {
			int i = random.nextInt(0, size);
			int j = random.nextInt(0, size);

			// Check if the square at (i, j) has not been visited.
			if (!visited[i][j]) {
				// Set the square at (i, j) as a special square.
				boardSquares[i][j] = Square.createSquareInstance(this, i, j, true);

				// Mark the square as visited.
				visited[i][j] = true;

				// Decrement the count of remaining special squares.
				numEspecialSquares--;
			}
		}
	}

	/**
	 * Sets the associated Game instance for this Gomoku object.
	 *
	 * @param game The Game instance to be associated with this Gomoku object.
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Plays the specified token at the given row and column on the board.
	 *
	 * @param token  The token to be played on the board.
	 * @param row    The row where the token will be played.
	 * @param column The column where the token will be played.
	 */
	public void playToken(Token token, int row, int column) {
		boardSquares[row][column].playToken(token);
	}

	/**
	 * Sets the specified token at the given row and column on the board.
	 *
	 * @param token  The token to be set on the board.
	 * @param row    The row where the token will be set.
	 * @param column The column where the token will be set.
	 */
	public void setToken(Token token, int row, int column) {
		boardSquares[row][column].setToken(token);
	}

	/**
	 * Retrieves the color of the token at the specified row and column on the
	 * board.
	 *
	 * @param row    The row coordinate of the token.
	 * @param column The column coordinate of the token.
	 * @return The color of the token, or null if the coordinates are invalid.
	 */
	public Color getTokenColor(int row, int column) {
		if (verify(row, column)) {
			return boardSquares[row][column].getTokenColor();
		} else {
			return null;
		}
	}

	/**
	 * Retrieves the size of the board, indicating its dimensions.
	 *
	 * @return The size of the board.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Validates whether the move at the specified row and column results in a
	 * winning condition. Checks for diagonal, vertical, and horizontal winning
	 * moves.
	 *
	 * @param row    The row coordinate of the last move.
	 * @param column The column coordinate of the last move.
	 * @return True if the move results in a winning condition, false otherwise.
	 */
	public boolean validate(int row, int column) {
		boolean winner = false;
		if (verify(row, column)) {
			winner = diagonalMove(row, column) || diagonalIMove(row, column) || verticalMove(row, column)
					|| horizontalMove(row, column);
		}
		return winner;
	}

	/**
	 * Verifies if the specified row and column coordinates are within the valid
	 * bounds of the board.
	 *
	 * @param r The row coordinate.
	 * @param c The column coordinate.
	 * @return True if the coordinates are valid, false otherwise.
	 */
	public boolean verify(int r, int c) {
		boolean flag = false;
		if (0 <= r && r < size && 0 <= c && c < size)
			flag = true;
		return flag;
	}

	/**
	 * Checks for a winning condition along the diagonal line (from top-left to
	 * bottom-right) originating from the specified row and column.
	 *
	 * @param row    The row coordinate of the last move.
	 * @param column The column coordinate of the last move.
	 * @return True if a winning condition is met, false otherwise.
	 */
	private boolean diagonalMove(int row, int column) {
		boolean winner = false;
		Color moveColor = boardSquares[row][column].getTokenColor();
		if (moveColor != null) {
			int t = 0;
			int up = 0;
			while (verify(row - up - 1, column - up - 1)
					&& boardSquares[row - up - 1][column - up - 1].getTokenColor() != null
					&& boardSquares[row - up - 1][column - up - 1].getTokenColor().equals(moveColor)) {
				t += boardSquares[row - up - 1][column - up - 1].getTokenValue();
				up++;
			}
			int dw = 0;
			while (verify(row + dw + 1, column + dw + 1)
					&& boardSquares[row + dw + 1][column + dw + 1].getTokenColor() != null
					&& boardSquares[row + dw + 1][column + dw + 1].getTokenColor().equals(moveColor)) {
				t += boardSquares[row + dw + 1][column + dw + 1].getTokenValue();
				dw++;
			}
			if (t + boardSquares[row][column].getTokenValue() == 5) {
				System.out.println("Diagonal at: " + row + " " + column + "..." + up + " " + dw + "... color:"
						+ boardSquares[row][column].getTokenColor());
				winner = true;
			}
		}

		return winner;
	}

	/**
	 * Checks for a winning condition along the diagonal line (from top-right to
	 * bottom-left) originating from the specified row and column.
	 *
	 * @param row    The row coordinate of the last move.
	 * @param column The column coordinate of the last move.
	 * @return True if a winning condition is met, false otherwise.
	 */
	private boolean diagonalIMove(int row, int column) {
		boolean winner = false;
		Color moveColor = boardSquares[row][column].getTokenColor();
		if (moveColor != null) {
			int t = 0;
			int up = 0;
			while (verify(row - up - 1, column + up + 1)
					&& boardSquares[row - up - 1][column + up + 1].getTokenColor() != null
					&& boardSquares[row - up - 1][column + up + 1].getTokenColor().equals(moveColor)) {
				t += boardSquares[row - up - 1][column + up + 1].getTokenValue();
				up++;
			}
			int dw = 0;
			while (verify(row + dw + 1, column - dw - 1)
					&& boardSquares[row + dw + 1][column - dw - 1].getTokenColor() != null
					&& boardSquares[row + dw + 1][column - dw - 1].getTokenColor().equals(moveColor)) {
				t += boardSquares[row + dw + 1][column - dw - 1].getTokenValue();
				dw++;
			}
			if (t + boardSquares[row][column].getTokenValue() == 5) {
				System.out.println("Diagonal at: " + row + " " + column + "..." + up + " " + dw + "... color:"
						+ boardSquares[row][column].getTokenColor());
				winner = true;
			}
		}
		return winner;
	}

	/**
	 * Checks for a winning condition along the horizontal line originating from the
	 * specified row and column.
	 *
	 * @param row    The row coordinate of the last move.
	 * @param column The column coordinate of the last move.
	 * @return True if a winning condition is met, false otherwise.
	 */
	private boolean horizontalMove(int row, int column) {
		boolean winner = false;
		Color moveColor = boardSquares[row][column].getTokenColor();
		if (moveColor != null) {
			int t = 0;
			int lf = 0;
			while (verify(row, column - lf - 1) && boardSquares[row][column - lf - 1].getTokenColor() != null
					&& boardSquares[row][column - lf - 1].getTokenColor().equals(moveColor)) {
				t += boardSquares[row][column - lf - 1].getTokenValue();
				lf++;
			}

			int rg = 0;
			while (verify(row, column + rg + 1) && boardSquares[row][column + rg + 1].getTokenColor() != null
					&& boardSquares[row][column + rg + 1].getTokenColor().equals(moveColor)) {
				t += boardSquares[row][column + rg + 1].getTokenValue();
				rg++;
			}
			if (t + boardSquares[row][column].getTokenValue() == 5) {
				System.out.println("Horizontal at: " + row + " " + column + "... " + lf + " " + rg + "... color:"
						+ boardSquares[row][column].getTokenColor());
				winner = true;
			}
		}

		return winner;
	}

	/**
	 * Checks for a winning condition along the vertical line originating from the
	 * specified row and column.
	 *
	 * @param row    The row coordinate of the last move.
	 * @param column The column coordinate of the last move.
	 * @return True if a winning condition is met, false otherwise.
	 */
	private boolean verticalMove(int row, int column) {
		boolean winner = false;
		Color moveColor = boardSquares[row][column].getTokenColor();
		if (moveColor != null) {
			int up = 0;
			int t = 0;
			while (verify(row - up - 1, column) && boardSquares[row - up - 1][column].getTokenColor() != null
					&& boardSquares[row - up - 1][column].getTokenColor().equals(moveColor)) {
				t += boardSquares[row - up - 1][column].getTokenValue();
				up++;
			}
			int dw = 0;
			while (verify(row + dw + 1, column) && boardSquares[row + dw + 1][column].getTokenColor() != null
					&& boardSquares[row + dw + 1][column].getTokenColor().equals(moveColor)) {
				t += boardSquares[row + dw + 1][column].getTokenValue();
				dw++;
			}
			if (t + boardSquares[row][column].getTokenValue() == 5) {
				System.out.println("Vertical at: " + row + " " + column + "... " + up + " " + dw + "... color:"
						+ boardSquares[row][column].getTokenColor());
				winner = true;
			}
		}
		return winner;
	}

	/**
	 * Retrieves the Square object at the specified row and column coordinates on
	 * the board.
	 *
	 * @param i The row coordinate of the Square.
	 * @param j The column coordinate of the Square.
	 * @return The Square object at the specified coordinates.
	 */
	public Square getSquare(int i, int j) {
		return boardSquares[i][j];
	}

	/**
	 * Increases the turn count in the associated Game instance.
	 */
	public void increaseTurn() {
		game.increaseTurn();
	}

	/**
	 * Decreases the turn count in the associated Game instance.
	 */
	public void decreaseTurn() {
		game.decreaseTurn();
	}

	/**
	 * Increases the quantity of a specific player's tokens in the associated Game
	 * instance.
	 *
	 * @param name The name of the player.
	 * @param i    The quantity to increase.
	 */
	public void increasePlayerQuantity(String name, int i) {
		game.increasePlayerQuantity(name, i);
	}

	/**
	 * Retrieves the current turn count from the associated Game instance.
	 *
	 * @return The current turn count.
	 */
	public int getTurn() {
		return game.getTurn();
	}

}

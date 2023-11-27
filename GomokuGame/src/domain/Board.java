package domain;

import java.awt.Color;
import java.util.Random;

public class Board {

	private int size;
	private Square[][] boardSquares;
	
	public Board(int size,int percentageEspecialSquares) {
		this.size = size;
		boardSquares = new Square[size][size];
		boolean [][] visited = new boolean[size][size];
		int numEspecialSquares = ((size*size)*percentageEspecialSquares)/100;
		for (int i = 0; i < size; i++) {
			for(int j = 0; j < size;j++) {
				boardSquares[i][j] = Square.createSquareInstance(false);
			}
		}
		Random random = new Random();
		while(numEspecialSquares>0) {
			int i = random.nextInt(0,size);
			int j = random.nextInt(0,size);
			if (!visited[i][j]) {
				boardSquares[i][j] = Square.createSquareInstance(true);
				visited[i][j] = true;
				numEspecialSquares--;
			}
		}
	}
	
	public void setToken(Token token, int row, int column) {
		if (verify(row,column))
			boardSquares[row][column].setToken(token);
	}
	
	public Color getTokenColor(int row, int column) {
		if (verify(row,column))
			return boardSquares[row][column].getTokenColor();
		else 
			return null;
	}
	
	public boolean validate(int row, int column) {
		boolean winner = false;
		if (verify(row,column))
			winner = diagonalMove(row,column) || diagonalIMove(row,column) ||
				         verticalMove(row,column) || horizontalMove(row,column);
		return winner;
	}
	
	private boolean diagonalMove(int row, int column) {
		int up = 0;
		while (verify(row-up-1,column-up-1) &&
				boardSquares[row-up-1][column-up-1].getTokenColor() == boardSquares[row][column].getTokenColor()) 
			up++;
		int dw = 0;
		while (verify(row+dw+1,column+dw+1) && 
				boardSquares[row+dw+1][column+dw+1].getTokenColor() == boardSquares[row][column].getTokenColor()) 
			dw++;
		boolean winner = false; 
		if (up + dw >= 4) {
			winner = true;
		}
		return winner;
	}

	private boolean diagonalIMove(int row, int column) {
		int up = 0;
		while (verify(row-up-1,column+up+1) && 
				boardSquares[row-up-1][column+up+1].getTokenColor() == boardSquares[row][column].getTokenColor()) 
			up++;
		int dw = 0;
		while (verify(row+dw+1,column-dw-1) && 
				boardSquares[row+dw+1][column-dw-1].getTokenColor() == boardSquares[row][column].getTokenColor()) 
			dw++;
		boolean winner = false; 
		if (up + dw >= 4) {
			winner = true;
		}
		return winner;
	}


	private boolean horizontalMove(int row, int column) {
		int lf = 0;
		while (verify(row,column-lf-1) && 
				boardSquares[row][column-lf-1].getTokenColor() == boardSquares[row][column].getTokenColor()) 
			lf++;
		int rg = 0;
		while (verify(row,column+rg+1) && 
				boardSquares[row][column+rg+1].getTokenColor() == boardSquares[row][column].getTokenColor()) 
			rg++;
		boolean winner = false; 
		if (lf + rg >= 4) {
			winner = true;
		}
		return winner;
	}


	private boolean verticalMove(int row, int column) {
		int up = 0;
		while (verify(row-up-1,column) && 
				boardSquares[row-up-1][column].getTokenColor() == boardSquares[row][column].getTokenColor()) 
			up++;
		int dw = 0;
		while (verify(row+dw+1,column) && 
				boardSquares[row+dw+1][column].getTokenColor() == boardSquares[row][column].getTokenColor()) 
			dw++;
		boolean winner = false; 
		if (up + dw >= 4) {
			winner = true;
		}
		return winner;
	}

	private boolean verify(int r, int c) {
		boolean flag = false;
		if (0 <= r && r < size && 0 <= c && c < size) flag = true;
		return flag;
	}
}

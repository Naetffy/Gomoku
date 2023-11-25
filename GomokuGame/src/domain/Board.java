package domain;

import java.util.Random;

public class Board {

	private Square[][] boardSquares;
	
	public Board(int size,int percentageEspecialSquares) {
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
	
}

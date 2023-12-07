package domain;

public class MineSquare extends Square {

	public MineSquare(Board board,int row, int column) {
		super(board,row,column);
	}

	public void playToken(Token token) {
		for(int i = row - 1; i <= row + 1;i++) {
			for(int j = column - 1; j <= column + 1;j++) {
				if (board.verify(i, j)) {
					token.setPosition(-1, -1);
					board.setToken(null, i, j);
				}
				
			}
		}
		
	}
}

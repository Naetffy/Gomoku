package domain;

public class MineSquare extends Square {

	public MineSquare(Board board,int row, int column) {
		super(board,row,column);
	}

	public void act() {
		AlertPlay.dettach(this);
		setToken(token);
		for(int i = row - 1; i <= row + 1;i++) {
			for(int j = column - 1; j <= column + 1;j++) {
				System.out.println(i+" "+j);
				if (board.verify(i, j)) {
					board.setToken(null, i, j);
				}
				
			}
		}
		board.increaseTurn();
	}
}

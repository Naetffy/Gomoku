package domain;

import java.awt.Color;
import java.util.ArrayList;

public class AggressiveMachinePlayer extends MachinePlayer {

	private int[][] bestMoves;

	@Override
	public int[] play() {
		try {
			Thread.sleep(timeRetard);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return miniMax();
	}

	@Override
	public int[] miniMax() {
		Board board = game.getBoard();

		int[] bestMove = evaluateMovesDefensively(board);

		return bestMove;
	}

	private int[] evaluateMovesDefensively(Board board) {
		int[] bestMove = null;
		int bestEvaluation = Integer.MIN_VALUE;
		evaluateMoveDefensively(board);
		for (int row = 0; row < board.getSize(); row++) {
			for (int column = 0; column < board.getSize(); column++) {
				if (board.getTokenColor(row, column) == null) {
					if (bestMoves[row][column] > bestEvaluation) {
						bestEvaluation = bestMoves[row][column];
						bestMove = new int[] { row, column };
					}
				}
				System.out.print(bestMoves[row][column] + " ");
			}
			System.out.println();
		}

		return bestMove;
	}

	private void evaluateMoveDefensively(Board board) {
		bestMoves = new int[board.getSize()][board.getSize()];
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				bestMoves[i][j] = 0;
			}
		}
		
		ArrayList<int[]> opponentTokenPositions = getOpponentTokenPositions();
		for (int[] opponentPosition : opponentTokenPositions) {
			bestMoves[opponentPosition[0]][opponentPosition[1]] += game.getTokenValue(opponentPosition[0], opponentPosition[1]);
		}
		for (int[] opponentPosition : opponentTokenPositions) {
			diagonalMove(opponentPosition[0], opponentPosition[1]);
			diagonalIMove(opponentPosition[0], opponentPosition[1]);
			horizontalMove(opponentPosition[0], opponentPosition[1]);
			verticalMove(opponentPosition[0], opponentPosition[1]);
		}
	}

	private ArrayList<int[]> getOpponentTokenPositions() {
		Color opponentColor = game.getOpponentColor();
		return game.getBoard().getOpponentTokenPositions(opponentColor);
	}

	private void diagonalMove(int row, int column) {
		Color moveColor = game.getTokenColor(row, column);
		if (moveColor != null) {
			int up = 0;
			int f = 1;
			boolean flag =false;
			while (game.verify(row - up - 1, column - up - 1) && up < 4) {
				if (game.getTokenColor(row - up - 1, column - up - 1) != null
						&& game.getTokenColor(row - up - 1, column - up - 1) != color) {
					bestMoves[row - up - 1][column - up - 1] += game.getTokenValue(row - up - 1, column - up - 1)
							+ (5 - calculateDistance(row - up - 1, column - up - 1, row, column));
					f++;
				}
				else if(flag || game.getTokenColor(row - up - 1, column - up - 1) == color) {
					bestMoves[row - up - 1][column - up - 1]=0;
					flag =true;
				}
				else
					bestMoves[row - up - 1][column - up - 1] += (5
							- calculateDistance(row - up - 1, column - up - 1, row, column));
				bestMoves[row - up - 1][column - up - 1]*=f;
				up++;
			}
			int dw = 0;
			flag =false;
			while (game.verify(row + dw + 1, column + dw + 1) && dw < 4) {
				if (game.getTokenColor(row + dw + 1, column + dw + 1) != null
						&& game.getTokenColor(row + dw + 1, column + dw + 1) != color) {
					bestMoves[row + dw + 1][column + dw + 1] += game.getTokenValue(row + dw + 1, column + dw + 1)
							+ (5 - calculateDistance(row + dw + 1, column + dw + 1, row, column));
					f++;
				}
				else if (flag||game.getTokenColor(row + dw + 1, column + dw + 1) == color) {
					bestMoves[row + dw + 1][column + dw + 1] =0;
					flag =true;
				}
				else
					bestMoves[row + dw + 1][column + dw + 1] += (5
							- calculateDistance(row + dw + 1, column + dw + 1, row, column));
				bestMoves[row + dw + 1][column + dw + 1]*=f;
				dw++;
			}
		}
	}

	private void diagonalIMove(int row, int column) {
		Color moveColor = game.getTokenColor(row, column);
		if (moveColor != null) {
			int up = 0;
			int f=1;
			boolean flag =false;
			while (game.verify(row - up - 1, column + up + 1) && up < 4) {
				if (game.getTokenColor(row - up - 1, column + up + 1) != null
						&& game.getTokenColor(row - up - 1, column + up + 1) != color) {
					bestMoves[row - up - 1][column + up + 1] += game.getTokenValue(row - up - 1, column + up + 1)
							+ (5 - calculateDistance(row - up - 1, column + up + 1, row, column));
					f++;
				}
				else if (flag||game.getTokenColor(row - up - 1, column + up + 1) == color) {
					bestMoves[row - up - 1][column + up + 1] = 0;
					flag =true;
				}
				else
					bestMoves[row - up - 1][column + up + 1] += (5
							- calculateDistance(row - up - 1, column + up + 1, row, column));
				bestMoves[row - up - 1][column + up + 1]*=f;
				up++;
			}
			int dw = 0;
			flag = false;
			while (game.verify(row + dw + 1, column - dw - 1) && dw < 4) {
				if (game.getTokenColor(row + dw + 1, column - dw - 1) != null
						&& game.getTokenColor(row + dw + 1, column - dw - 1) != color) {
					bestMoves[row + dw + 1][column - dw - 1] += game.getTokenValue(row + dw + 1, column - dw - 1)
							+ (5 - calculateDistance(row + dw + 1, column - dw - 1, row, column));
					f++;
				}
				else if (flag || game.getTokenColor(row + dw + 1, column - dw - 1) == color) {
					bestMoves[row + dw + 1][column - dw - 1] =0;
					flag =true;
				}
				else
					bestMoves[row + dw + 1][column - dw - 1] += (5
							- calculateDistance(row + dw + 1, column - dw - 1, row, column));
				bestMoves[row + dw + 1][column - dw - 1]*=f;
				dw++;
			}
		}
	}

	private void horizontalMove(int row, int column) {
		Color moveColor = game.getTokenColor(row, column);
		if (moveColor != null) {
			int lf = 0;
			int f=1;
			boolean flag =false;
			while (game.verify(row, column - lf - 1) && lf < 4) {
				if (game.getTokenColor(row, column - lf - 1) != null
						&& game.getTokenColor(row, column - lf - 1) != color) {
					bestMoves[row][column - lf - 1] += game.getTokenValue(row, column - lf - 1)
							+ (5 - calculateDistance(row, column - lf - 1, row, column));
					f++;
				}
				else if (flag || game.getTokenColor(row, column - lf - 1) == color) {
					bestMoves[row][column - lf - 1] =0;
					flag =true;
				}
				else
					bestMoves[row][column - lf - 1] += (5 - calculateDistance(row, column - lf - 1, row, column));
				bestMoves[row][column - lf - 1]*=f;
				lf++;
			}
			flag =false;
			int rg = 0;
			while (game.verify(row, column + rg + 1) && rg < 4) {
				if (game.getTokenColor(row, column + rg + 1) != null
						&& game.getTokenColor(row, column + rg + 1) != color) {
					bestMoves[row][column + rg + 1] += game.getTokenValue(row, column + rg + 1)
							+ (5 - calculateDistance(row, column + rg + 1, row, column));
					f++;
				}
				else if (flag || game.getTokenColor(row, column + rg + 1) == color) {
					bestMoves[row][column + rg + 1] =0;
					flag =true;
				}
				else
					bestMoves[row][column + rg + 1] += (5 - calculateDistance(row, column + rg + 1, row, column));
				bestMoves[row][column + rg + 1] *= f;
				rg++;
			}
		}
	}

	private void verticalMove(int row, int column) {
		Color moveColor = game.getTokenColor(row, column);
		if (moveColor != null) {
			int up = 0;
			int f=1;
			boolean flag=false;
			while (game.verify(row - up - 1, column) && up < 4) {
				if (game.getTokenColor(row - up - 1, column) != null
						&& game.getTokenColor(row - up - 1, column) != color) {
					bestMoves[row - up - 1][column] += game.getTokenValue(row - up - 1, column)
							+ (5 - calculateDistance(row - up - 1, column, row, column));
					f ++;
				}
				else if (flag||game.getTokenColor(row - up - 1, column) == color) {
					bestMoves[row - up - 1][column] =0;
					flag = true;
				}
				else
					bestMoves[row - up - 1][column] += (5 - calculateDistance(row - up - 1, column, row, column));
				bestMoves[row - up - 1][column]*=f;
				up++;
				
			}
			flag = false;
			int dw = 0;
			while (game.verify(row + dw + 1, column) && dw < 4) {
				if (game.getTokenColor(row + dw + 1, column) != null
						&& game.getTokenColor(row + dw + 1, column) != color) {
					bestMoves[row + dw + 1][column] += game.getTokenValue(row + dw + 1, column)
							+ (5 - calculateDistance(row + dw + 1, column, row, column));
					f++;
				}
				else if (game.getTokenColor(row + dw + 1, column) == color || flag) {
					bestMoves[row + dw + 1][column] =0;
					flag = true;
				}
				else
					bestMoves[row + dw + 1][column] += (5 - calculateDistance(row + dw + 1, column, row, column));
				bestMoves[row + dw + 1][column]*=f;
				dw++;
			}

		}
	}

	private double calculateDistance(int row1, int col1, int row2, int col2) {
		double dis;
		if (Math.abs(row1 - row2) == 1 && Math.abs(col1 - col2) == 1)
			dis = 1;
		else if (Math.abs(row1 - row2) == 2 && Math.abs(col1 - col2) == 2)
			dis = 2;
		else if (Math.abs(row1 - row2) == 3 && Math.abs(col1 - col2) == 3)
			dis = 3;
		else if (Math.abs(row1 - row2) == 4 && Math.abs(col1 - col2) == 4)
			dis = 4;
		else
			dis = Math.sqrt(Math.pow(row1 - row2, 2) + Math.pow(col1 - col2, 2));
		return dis;
	}
}
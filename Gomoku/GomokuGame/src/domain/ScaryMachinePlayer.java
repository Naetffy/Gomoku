package domain;

import java.awt.Color;
import java.util.ArrayList;

public class ScaryMachinePlayer extends MachinePlayer {


    @Override
    public int[] play() {
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return miniMax();
    }

    @Override
    public int[] miniMax() {
        Board board = game.getBoard();

        ArrayList<int[]> opponentTokenPositions = getOpponentTokenPositions();

        int[] bestMove = evaluateMovesWithSafety(board, opponentTokenPositions);

        return bestMove;
    }

    private ArrayList<int[]> getOpponentTokenPositions() {
        Color opponentColor = game.getOpponentColor();
        return game.getBoard().getOpponentTokenPositions(opponentColor);
    }

    private int[] evaluateMovesWithSafety(Board board, ArrayList<int[]> opponentTokenPositions) {
        int[] bestMove = null;
        int bestEvaluation = Integer.MIN_VALUE;

        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 0; column < board.getSize(); column++) {
                if (board.getTokenColor(row, column) == null) {
                    int evaluation = evaluateMove(board, row, column, opponentTokenPositions);

                    if (evaluation > bestEvaluation) {
                        bestEvaluation = evaluation;
                        bestMove = new int[]{row, column};
                    }
                }
            }
        }

        return bestMove;
    }

    private int evaluateMove(Board board, int row, int column, ArrayList<int[]> opponentTokenPositions) {
        int safetyScore = 0;

        for (int[] opponentPosition : opponentTokenPositions) {
            double distance = calculateDistance(row, column, opponentPosition[0], opponentPosition[1]);

            safetyScore += distance;
        }

        

        return safetyScore;
    }

    private double calculateDistance(int row1, int col1, int row2, int col2) {
        return Math.sqrt(Math.pow(row1 - row2,2) + Math.pow(col1 - col2,2));
    }

}
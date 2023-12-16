package domain;

import java.awt.Color;
import java.util.ArrayList;

public class AggressiveMachinePlayer extends MachinePlayer {

    @Override
    public int[] play() {
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

        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 0; column < board.getSize(); column++) {
                if (board.getTokenColor(row, column) == null) {
                    int evaluation = evaluateMoveDefensively(board, row, column);

                    if (evaluation > bestEvaluation) {
                        bestEvaluation = evaluation;
                        bestMove = new int[]{row, column};
                    }
                }
            }
        }

        return bestMove;
    }

    private int evaluateMoveDefensively(Board board, int row, int column) {
        int defensiveScore = 0;

        ArrayList<int[]> opponentTokenPositions = getOpponentTokenPositions();
        for (int[] opponentPosition : opponentTokenPositions) {
            double distance = calculateDistance(row, column, opponentPosition[0], opponentPosition[1]);

            defensiveScore += (distance == 1) ? 1 : 0;
        }

        return defensiveScore;
    }

    private ArrayList<int[]> getOpponentTokenPositions() {
        Color opponentColor = game.getOpponentColor();
        return game.getBoard().getOpponentTokenPositions(opponentColor);
    }

    private double calculateDistance(int row1, int col1, int row2, int col2) {
    	return Math.sqrt(Math.pow(row1 - row2,2) + Math.pow(col1 - col2,2));
    }
}
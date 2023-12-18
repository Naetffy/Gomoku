package domain;

import java.awt.Color;
import java.util.ArrayList;

/*
* @author Juan Daniel Murcia - Mateo Forero Fuentes
* @version 1.8.5
*/
public class ExpertMachinePlayer extends MachinePlayer {

   @Override
   public int[] play() {
	   try {
			Thread.sleep(timeRetard);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return miniMax();
   }

 
   @Override
   public int[] miniMax() {
       Board board = game.getBoard();
       int[] bestMove = evaluateMovesForFiveInARow(board);
       return bestMove;
   }


   private int[] evaluateMovesForFiveInARow(Board board) {
       int[] bestMove = null;
       int bestEvaluation = Integer.MIN_VALUE;

       for (int row = 0; row < board.getSize(); row++) {
           for (int column = 0; column < board.getSize(); column++) {
               if (board.getTokenColor(row, column) == null) {
                   int evaluation = evaluateMoveForFiveInARow(board, row, column);

                   if (evaluation > bestEvaluation) {
                       bestEvaluation = evaluation;
                       bestMove = new int[]{row, column};
                   }
               }
           }
       }

       return bestMove;
   }

  
   private int evaluateMoveForFiveInARow(Board board, int row, int column) {
       Color opponentColor = game.getOpponentColor();
       
       if (board.validate(row, column)) {
           return Integer.MAX_VALUE; 
       }

       int evaluation = countTokensInLine(board, row, column, 1, 0, opponentColor) +
                        countTokensInLine(board, row, column, 0, 1, opponentColor) +
                        countTokensInLine(board, row, column, 1, 1, opponentColor) +
                        countTokensInLine(board, row, column, 1, -1, opponentColor);

       return evaluation;
   }

   
   private int countTokensInLine(Board board, int row, int column, int rowIncrement, int colIncrement, Color opponentColor) {
       Color playerColor = color;
       int count = 1; 

       int r = row + rowIncrement;
       int c = column + colIncrement;
       while (board.verify(r, c) && board.getTokenColor(r, c) == playerColor) {
           count++;
           r += rowIncrement;
           c += colIncrement;
       }

       r = row - rowIncrement;
       c = column - colIncrement;
       while (board.verify(r, c) && board.getTokenColor(r, c) == playerColor) {
           count++;
           r -= rowIncrement;
           c -= colIncrement;
       }

       r = row + rowIncrement;
       c = column + colIncrement;
       while (board.verify(r, c) && board.getTokenColor(r, c) == opponentColor) {
           count--;
           r += rowIncrement;
           c += colIncrement;
       }

       r = row - rowIncrement;
       c = column - colIncrement;
       while (board.verify(r, c) && board.getTokenColor(r, c) == opponentColor) {
           count--;
           r -= rowIncrement;
           c -= colIncrement;
       }

       return count;
   }

}
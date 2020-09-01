package com.tieto.tictactoesolution.NoStrategies;

import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;
import com.tieto.tictactoesolution.NoStrategies.AnswerFriendlyRow;


public class MovementWithoutStrategy {

    private PlayerSymbol playerSymbol;
    private int boardSize;

    public MovementWithoutStrategy(PlayerSymbol playerSymbol, int boardSize)
    {
        this.boardSize = boardSize;
        this.playerSymbol = playerSymbol;
    }

    public Point calculateBestPosition(PlayerSymbol[][] board) {
        AnswerFriendlyRow answerFriendlyRow = new AnswerFriendlyRow();

        //checking middle line diagonal
        for (int m = 0, n = boardSize - 1; m < boardSize; m++, n--) {
            checkSpot(m, n, board, answerFriendlyRow);
        }
        checkIfBetterThanPreviousCount(answerFriendlyRow);
        answerFriendlyRow.countInRow = 0;

        //checking middle line diagonal - other side
        for (int m = boardSize - 1; m > 0; m--) {
            checkSpot(m, m, board, answerFriendlyRow);
        }
        checkIfBetterThanPreviousCount(answerFriendlyRow);
        answerFriendlyRow.countInRow = 0;

        for (int i = 0; i < boardSize; i++) {
            //checking line
            for (int j = 0; j < boardSize; j++) {
                checkSpot(i, j, board, answerFriendlyRow);
            }
            //need to check the count once again, because otherwise it doesnt check the end of the row.
            checkIfBetterThanPreviousCount(answerFriendlyRow);
            answerFriendlyRow.countInRow = 0;

            //checking column
            for (int j = 0; j < boardSize; j++) {
                checkSpot(j, i, board, answerFriendlyRow);
            }
            checkIfBetterThanPreviousCount(answerFriendlyRow);
            answerFriendlyRow.countInRow = 0;
        }
        if (answerFriendlyRow.finalEnd != null) {
            return answerFriendlyRow.finalEnd;
        } else {
            return answerFriendlyRow.finalStart;
        }
    }


    public void checkSpot(int i, int j, PlayerSymbol[][] board, AnswerFriendlyRow answerFriendlyRow) {
        if (board[i][j].equals(PlayerSymbol.E)) {
            if (answerFriendlyRow.countInRow == 0) {
                answerFriendlyRow.potentialStart = new Point(i, j);
            } else {
                answerFriendlyRow.potentialEnd = new Point(i, j);
                checkIfBetterThanPreviousCount(answerFriendlyRow);
            }
            answerFriendlyRow.countInRow = 0;

        } else if (board[i][j].equals(playerSymbol)) {
            answerFriendlyRow.countInRow++;
        } else {
            answerFriendlyRow.potentialEnd = null;
            checkIfBetterThanPreviousCount(answerFriendlyRow);
            answerFriendlyRow.countInRow = 0;
        }
    }



    public void checkIfBetterThanPreviousCount(AnswerFriendlyRow answerFriendlyRow) {
        //check if final count is smaller than currently suggested count
        //also check if at least one point is free
        if (((answerFriendlyRow.finalCountInRow < answerFriendlyRow.countInRow) && ((answerFriendlyRow.potentialStart != null) || (answerFriendlyRow.potentialEnd != null)))) {
            answerFriendlyRow.finalStart = answerFriendlyRow.potentialStart;
            answerFriendlyRow.finalCountInRow = answerFriendlyRow.countInRow;
            answerFriendlyRow.finalEnd = answerFriendlyRow.potentialEnd;
        }
    }




}

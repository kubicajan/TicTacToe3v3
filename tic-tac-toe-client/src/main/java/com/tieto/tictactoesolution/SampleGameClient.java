package com.tieto.tictactoesolution;

import com.tieto.tictactoeclient.api.GameClient;
import com.tieto.tictactoeclient.internal.GameConfiguration;
import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;

import java.util.UUID;

public class SampleGameClient implements GameClient {

    private UUID sessionId;
    private UUID clientId;
    private PlayerSymbol playerSymbol;
    private int boardSize;
    private int winnerLength;

    /**
     * Initialize game client with provided game configuration.
     *
     * @param gameConfiguration object that holds necessary configuration for playing game
     * @apiNote This method will be used by game framework to provide game configuration
     */
    @Override
    public void init(GameConfiguration gameConfiguration) {
        this.sessionId = gameConfiguration.getSessionId();
        this.clientId = gameConfiguration.getClientId();
        this.playerSymbol = gameConfiguration.getPlayerSymbol();
        this.boardSize = gameConfiguration.getBoardSize();
        this.winnerLength = gameConfiguration.getWinnerLength();
    }

    /**
     * Makes decision about next move.
     *
     * @param board two-dimensional array representing game board state, where:<br>
     * - first dimension refers to rows<br>
     * - second dimension refers to columns<br>
     * - [row=0;column=0] coordinates refers to left upper corner<br>
     * @return point which represents next move or null when game board is full
     */


    private boolean firstCheck = true;
    private boolean wentFirst;

    @Override
    public Point getNextMove(PlayerSymbol[][] board) {
        if (firstCheck) {
            wentFirst = playerSymbol.equals(PlayerSymbol.X);
        }

        if (wentFirst) {
            if (firstCheck) {
                firstCheck = false;
                return new Point(1, 1);
            } else {
                return strategyHandler(board);
            }
        } else {
            if (firstCheck) {
                firstCheck = false;
                return getMiddleOrStartStrategy(board);
            }

            return strategyHandler(board);
        }
    }


    public Point doBottomLeftStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing BottomLeftStrategy");
        if (checkIfEmpty(board, 2, 0)) {
            return new Point(2, 0);
        } else if (checkIfMine(board, 1, 1) && checkIfEmpty(board, 0, 2)) {
            return new Point(0, 2);
        } else if (checkIfEmpty(board, 2, 1)) {
            return new Point(2, 1);
        } else if (checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");

        return checkBestPositionWentSecond(board);
    }

    public Point doBottomRightStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing BottomRightStrategy");
        if (checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        } else if (checkIfMine(board, 1, 1) && checkIfEmpty(board, 0, 0)) {
            return new Point(0, 0);
        } else if (checkIfEmpty(board, 0, 2)) {
            return new Point(0, 2);
        } else if (checkIfEmpty(board, 1, 2)) {
            return new Point(1, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");

        return checkBestPositionWentSecond(board);
    }


    public Point doTopRightStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing TopRightStrategy");
        //these strategies always first check a different pair of corners and a point between them.
        //if a strategy that works is found, it will start putting points first corner - then it checks if middle is ours


        if (checkIfEmpty(board, 0, 0)) {
            return new Point(0, 0);
        } else if (checkIfMine(board, 1, 1) && checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        } else if (checkIfEmpty(board, 0, 2)) {
            return new Point(0, 2);
        } else if (checkIfEmpty(board, 0, 1)) {
            return new Point(0, 1);
        }
        System.out.println("None of the suggested points worked, panicking now.");

        return checkBestPositionWentSecond(board);
    }


    public Point doTopLeftStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing TopLeftStrategy");
        if (checkIfEmpty(board, 0, 0)) {
            return new Point(0, 0);
        } else if (checkIfMine(board, 1, 1) && checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        } else if (checkIfEmpty(board, 2, 0)) {
            return new Point(2, 0);
        } else if (checkIfEmpty(board, 1, 0)) {
            return new Point(1, 0);
        }
        System.out.println("None of the suggested points worked, panicking now.");

        return checkBestPositionWentSecond(board);
    }

    private String iDidThisStrategyLast = "";
    private final String diagonalLeftStrategy = "doDiagonalFromLeftTop";
    private final String diagonalRightStrategy = "doDiagonalFromRightUp";
    //    private final String backupTopRightForLeft = "doBackupLeftForLeft";
//    private final String backupTopLeftForLeft = "doBackupRightForLeft";
//    private final String backupTopRightForRight = "doBackupLeftForRight";
//    private final String backupTopLeftForRight = "doBackupRightForRight";
    private final String lastPieceStrategy = "doLastPieceStrategy";

    public Point doDiagonalLeftStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing DiagonalLeft");
        if (checkIfEmpty(board, 0, 0)) {
            return new Point(0, 0);
        } else if (checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return checkBestPositionWentSecond(board);
    }

    public Point doDiagonalRightStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing DiagonalRight");
        if (checkIfEmpty(board, 0, 2)) {
            return new Point(0, 2);
        } else if (checkIfEmpty(board, 2, 0)) {
            return new Point(2, 0);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return checkBestPositionWentSecond(board);
    }

    public Point doBackupTopRightForLeft(PlayerSymbol[][] board) {
        System.out.println("I am doing BackupTopRightForLeft");
        if (checkIfEmpty(board, 1, 0)) {
            return new Point(0, 2);
        } else if (checkIfEmpty(board, 2, 0)) {
            return new Point(2, 0);
        } else if (checkIfEmpty(board, 1, 2)) {
            return new Point(1, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return checkBestPositionWentSecond(board);
    }

    public Point doBackupTopLeftForLeft(PlayerSymbol[][] board) {
        System.out.println("I am doing BackupTopLeftForLeft");
        if (checkIfEmpty(board, 0, 2)) {
            return new Point(0, 2);
        } else if (checkIfEmpty(board, 0, 1)) {
            return new Point(1, 1);
        } else if (checkIfEmpty(board, 2, 1)) {
            return new Point(2, 1);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return checkBestPositionWentSecond(board);
    }


    public Point doBackupTopRightForRight(PlayerSymbol[][] board) {
        System.out.println("I am doing BackupTopRightForRight");
        if (checkIfEmpty(board, 1, 0)) {
            return new Point(1, 0);
        } else if (checkIfEmpty(board, 0, 0)) {
            return new Point(0, 0);
        } else if (checkIfEmpty(board, 1, 2)) {
            return new Point(1, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return checkBestPositionWentSecond(board);
    }


    public Point doBackupTopLeftForRight(PlayerSymbol[][] board) {
        System.out.println("I am doing BackupTopLeftForRight");
        if (checkIfEmpty(board, 2, 1)) {
            return new Point(2, 1);
        } else if (checkIfEmpty(board, 0, 1)) {
            return new Point(0, 1);
        } else if (checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return checkBestPositionWentSecond(board);
    }

    public Point doLastPieceStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing LASTPIECE");
        if (checkIfEmpty(board, 0, 0)) {
            return new Point(0, 0);
        } else if (checkIfEmpty(board, 1, 0)) {
            return new Point(1, 0);
        } else if (checkIfEmpty(board, 2, 0)) {
            return new Point(2, 0);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return checkBestPositionWentSecond(board);
    }

    public Point strategyHandler(PlayerSymbol[][] board) {
        switch (checkWhatStrategyPossible(board)) {
            case diagonalLeftStrategy:
                return doDiagonalLeftStrategy(board);
            case diagonalRightStrategy:
                return doDiagonalRightStrategy(board);
            case lastPieceStrategy:
                return doLastPieceStrategy(board);
        }
        return checkBestPositionWentSecond(board);
    }


    public String checkWhatStrategyPossible(PlayerSymbol[][] board) {

        if (
                checkIfEmptyOrMine(board, 0, 0) &&
                        checkIfEmptyOrMine(board, 2, 2)
        ) {
            iDidThisStrategyLast = diagonalLeftStrategy;
            return diagonalLeftStrategy;
        }

        if (
                checkIfEmptyOrMine(board, 0, 2) &&
                        checkIfEmptyOrMine(board, 2, 0)
        ) {
            iDidThisStrategyLast = diagonalRightStrategy;
            return diagonalRightStrategy;
        }
        if (checkIfEmptyOrMine(board, 0, 0) && checkIfEmptyOrMine(board, 1, 0) && checkIfEmptyOrMine(board, 2, 0)) {
            return lastPieceStrategy;
        }


//        if (iDidThisStrategyLast.equals(diagonalLeftStrategy)) {
//            if (checkIfEmptyOrMine(board, 1, 0) && checkIfEmptyOrMine(board, 2, 0) && checkIfEmptyOrMine(board, 1, 2)) {
//                iDidThisStrategyLast = backupTopRightForLeft;
//                return backupTopRightForLeft;
//            }
//            if (checkIfEmptyOrMine(board, 0, 2) && checkIfEmptyOrMine(board, 0, 1) && checkIfEmptyOrMine(board, 2, 1)) {
//                iDidThisStrategyLast = backupTopLeftForLeft;
//                return backupTopLeftForLeft;
//
//            }
//        }
//
//        if (iDidThisStrategyLast.equals(diagonalRightStrategy)) {
//            if (checkIfEmptyOrMine(board, 1, 0) && checkIfEmptyOrMine(board, 0, 0) && checkIfEmptyOrMine(board, 1, 2)) {
//                iDidThisStrategyLast = backupTopRightForRight;
//                return backupTopRightForRight;
//            }
//            if (checkIfEmptyOrMine(board, 2, 1) && checkIfEmptyOrMine(board, 0, 1) && checkIfEmptyOrMine(board, 2, 2)) {
//                iDidThisStrategyLast = backupTopLeftForRight;
//                return backupTopLeftForRight;
//            }
//        }
//
//
//        if (
//                checkIfEmptyOrMine(board, 0, 0) &&
//                        checkIfEmptyOrMine(board, 2, 0) &&
//                        checkIfEmptyOrMine(board, 1, 0)
//        ) {
//            return "doTopLeft";
//        } else if (
//                checkIfEmptyOrMine(board, 0, 0) &&
//                        checkIfEmptyOrMine(board, 0, 2) &&
//                        checkIfEmptyOrMine(board, 0, 1)
//        ) {
//            return "doTopRight";
//        } else if (
//                checkIfEmptyOrMine(board, 2, 2) &&
//                        checkIfEmptyOrMine(board, 2, 1) &&
//                        checkIfEmptyOrMine(board, 2, 0)
//        ) {
//            return "doBottomLeft";
//        } else if (
//                checkIfEmptyOrMine(board, 2, 2) &&
//                        checkIfEmptyOrMine(board, 1, 2) &&
//                        checkIfEmptyOrMine(board, 0, 2)
//        ) {
//            return "doBottomRight";
//        }
        return "nothing";
    }

    public boolean checkIfMine(PlayerSymbol[][] board, int i, int j) {
        return (board[i][j].equals(playerSymbol));
    }

    public boolean checkIfEmpty(PlayerSymbol[][] board, int i, int j) {
        return (board[i][j].equals(PlayerSymbol.E));
    }

    public boolean checkIfEmptyOrMine(PlayerSymbol[][] board, int i, int j) {
        return (board[i][j].equals(PlayerSymbol.E) || board[i][j].equals(playerSymbol));
    }

    public Point getMiddleOrStartStrategy(PlayerSymbol[][] board) {
        if (board[1][1].equals(PlayerSymbol.E)) {
            return new Point(1, 1);
        } else {
            return strategyHandler(board);
        }
    }

    public Point checkBestPositionWentFirst(PlayerSymbol[][] board) {
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

    public static class AnswerFriendlyRow {
        public Point potentialStart;
        public Point potentialEnd;

        public Point finalStart;
        public Point finalEnd;

        public int countInRow = 0;
        public int finalCountInRow = 0;
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

    public Point checkBestPositionWentSecond(PlayerSymbol[][] board) {
        return checkBestPositionWentFirst(board);
    }

}

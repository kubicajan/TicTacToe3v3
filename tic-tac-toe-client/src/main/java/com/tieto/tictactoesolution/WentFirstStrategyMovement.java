package com.tieto.tictactoesolution;

import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;

public class WentFirstStrategyMovement {

    private PlayerSymbol playerSymbol;
    private MovementWithoutStrategy movementWithoutStrategy;
    private boolean wentSecondButHaveMiddle = false;

    public WentFirstStrategyMovement(PlayerSymbol playerSymbol, int boardSize) {
        this.playerSymbol = playerSymbol;
        movementWithoutStrategy = new MovementWithoutStrategy(playerSymbol, boardSize);

    }

    private Point checkBestPositionWentFirst(PlayerSymbol[][] board) {
        return movementWithoutStrategy.checkBestPositionWentFirst(board);
    }


    public Point strategyHandlerWentFirst(PlayerSymbol[][] board) {
        switch (checkWhatStrategyPossibleWentFirst(board)) {
            case diagonalLeftStrategy:
                return doDiagonalLeftStrategy(board);
            case diagonalRightStrategy:
                return doDiagonalRightStrategy(board);
            case lastPieceStrategy:
                return doLastPieceStrategy(board);
        }
        return strategyHandlerWentSecond(board);
    }


    public Point strategyHandlerWentSecond(PlayerSymbol[][] board) {
        switch (checkWhatStrategyPossibleWentSecond(board)) {
            case wentSecondLastHopeLol:
                return doWentSecondLastHopeLol(board);
            case wentSecondAnotherLastHopeLol:
                return doWentSecondAnotherLastHopeLol(board);
        }
        return checkBestPositionWentFirst(board);
    }


    public Strategy checkWhatStrategyPossibleWentFirst(PlayerSymbol[][] board) {

        if (checkIfEmptyOrMine(board, 0, 0) && checkIfEmptyOrMine(board, 2, 2)) {
            return Strategy.diagonalLeftStrategy;
        }

        if (checkIfEmptyOrMine(board, 0, 2) && checkIfEmptyOrMine(board, 2, 0)) {
            return Strategy.diagonalRightStrategy;
        }
        if (checkIfEmptyOrMine(board, 0, 0) && checkIfEmptyOrMine(board, 1, 0) && checkIfEmptyOrMine(board, 2, 0)) {
            return Strategy.lastPieceStrategy;
        }
        return Strategy.nothing;
    }

    public boolean checkIfMine(PlayerSymbol[][] board, int row, int column) {
        return (board[row][column].equals(playerSymbol));
    }

    public boolean checkIfEmpty(PlayerSymbol[][] board, int row, int column) {
        return (board[row][column].equals(PlayerSymbol.E));
    }

    public boolean checkIfEmptyOrMine(PlayerSymbol[][] board, int row, int column) {
        return (board[row][column].equals(PlayerSymbol.E) || board[row][column].equals(playerSymbol));
    }


    public Boolean getMiddleOrStartStrategy(PlayerSymbol[][] board) {
        if (checkIfEmpty(board, 1, 1)) {
            wentSecondButHaveMiddle = true;
            return true;
        } else {
            return false;
        }
    }

    public Point doWentSecondLastHopeLol(PlayerSymbol[][] board) {
        System.out.println("I am doing WentSecondLastHopeLol");
        if (checkIfEmpty(board, 0, 2)) {
            return new Point(0, 2);
        } else if (checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        } else if (checkIfEmpty(board, 1, 2)) {
            return new Point(1, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return checkBestPositionWentFirst(board);
    }

    public Point doWentSecondAnotherLastHopeLol(PlayerSymbol[][] board) {
        System.out.println("I am doing wentSecondAnotherLastHopeLol");
        if (checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        } else if (checkIfEmpty(board, 2, 0)) {
            return new Point(2, 0);
        } else if (checkIfEmpty(board, 2, 1)) {
            return new Point(2, 1);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return checkBestPositionWentFirst(board);
    }


    public Strategy checkWhatStrategyPossibleWentSecond(PlayerSymbol[][] board) {

        //todo: check middle and do strategy thanks to that.
//        if(checkIfMine(board,1,1))
//        {
//           return checkWhatStrategyPossibleWentFirst(board);
//        }

        if (checkIfEmptyOrMine(board, 0, 2) && checkIfEmptyOrMine(board, 1, 2) && checkIfEmptyOrMine(board, 2, 2)) {
            return Strategy.wentSecondLastHopeLol;
        }

        if (checkIfEmptyOrMine(board, 2, 2) && checkIfEmptyOrMine(board, 2, 1) && checkIfEmptyOrMine(board, 2, 0)) {
            return Strategy.wentSecondAnotherLastHopeLol;
        }

        return Strategy.nothing;
    }

    public Point doDiagonalLeftStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing DiagonalLeft");
        if (checkIfEmpty(board, 0, 0)) {
            return new Point(0, 0);
        } else if (checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return strategyHandlerWentSecond(board);
    }

    public Point doDiagonalRightStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing DiagonalRight");
        if (checkIfEmpty(board, 0, 2)) {
            return new Point(0, 2);
        } else if (checkIfEmpty(board, 2, 0)) {
            return new Point(2, 0);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return strategyHandlerWentSecond(board);
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
        return strategyHandlerWentSecond(board);
    }
}

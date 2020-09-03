package com.tieto.tictactoesolution.Strategies;

import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;
import com.tieto.tictactoesolution.Enums.Strategy;
import com.tieto.tictactoesolution.NoStrategies.MovementWithoutStrategy;

public class WentFirstStrategyMovement extends StrategyMovement {

    public WentFirstStrategyMovement(PlayerSymbol playerSymbol, int boardSize) {
        initValues(playerSymbol, new MovementWithoutStrategy(playerSymbol, boardSize));
    }

    @Override
    public Point strategyHandler(PlayerSymbol[][] board) {
        switch (checkForStrategies(board)) {
            case diagonalTopStrategy:
                return doDiagonalTopStrategy(board);
            case diagonalBottom:
                return doDiagonalBottomStrategy(board);
            case lastPieceStrategy:
                return doLastPieceStrategy(board);
            case shitFuck:
                return doShitFuck(board);
        }
        return moveWithoutStrategy(board);
    }

    private Point doShitFuck(PlayerSymbol[][] board) {
        return new Point(1, 2);
    }


    @Override
    public Strategy checkForStrategies(PlayerSymbol[][] board) {

        if (checkIfEmptyOrMine(board, 0, 0) && checkIfEmptyOrMine(board, 2, 2)) {
            return Strategy.diagonalTopStrategy;
        }
        if (checkIfEmptyOrMine(board, 0, 2) && checkIfEmptyOrMine(board, 2, 0)) {
            return Strategy.diagonalBottom;
        }
        if (checkIfEmptyOrMine(board, 0, 0) && checkIfEmptyOrMine(board, 1, 0) && checkIfEmptyOrMine(board, 2, 0)) {
            return Strategy.lastPieceStrategy;
        }
        if (checkIfEmpty(board, 1, 2) && !checkIfEmptyOrMine(board, 0, 2) && !checkIfEmptyOrMine(board, 2, 2)) {
            return Strategy.shitFuck;
        }
        return Strategy.nothing;
    }

    public Point doDiagonalTopStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing DiagonalTop");
        if (checkIfEmpty(board, 0, 0)) {
            return new Point(0, 0);
        } else if (checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return moveWithoutStrategy(board);
    }

    public Point doDiagonalBottomStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing DiagonalBottom");
        if (checkIfEmpty(board, 2, 0)) {
            return new Point(2, 0);
        } else if (checkIfEmpty(board, 0, 2)) {
            return new Point(0, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return moveWithoutStrategy(board);
    }

    public Point doLastPieceStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing LASTPIECE");
        if (checkIfEmpty(board, 0, 0)) {
            return new Point(0, 0);
        } else if (checkIfEmpty(board, 1, 0)) {
            return new Point(1, 0);
        } else if (checkIfEmpty(board, 0, 2)) {
            return new Point(0, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return moveWithoutStrategy(board);
    }
}

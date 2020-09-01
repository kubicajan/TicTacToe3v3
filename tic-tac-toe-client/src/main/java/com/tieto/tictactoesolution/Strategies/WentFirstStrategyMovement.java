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
            case diagonalLeftStrategy:
                return doDiagonalLeftStrategy(board);
            case diagonalRightStrategy:
                return doDiagonalRightStrategy(board);
            case lastPieceStrategy:
                return doLastPieceStrategy(board);
        }
        return moveWithoutStrategy(board);
    }


    @Override
    public Strategy checkForStrategies(PlayerSymbol[][] board) {

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

    public Point doDiagonalLeftStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing DiagonalLeft");
        if (checkIfEmpty(board, 0, 0)) {
            return new Point(0, 0);
        } else if (checkIfEmpty(board, 2, 2)) {
            return new Point(2, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return moveWithoutStrategy(board);
    }

    public Point doDiagonalRightStrategy(PlayerSymbol[][] board) {
        System.out.println("I am doing DiagonalRight");
        if (checkIfEmpty(board, 0, 2)) {
            return new Point(0, 2);
        } else if (checkIfEmpty(board, 2, 0)) {
            return new Point(2, 0);
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
        } else if (checkIfEmpty(board, 2, 0)) {
            return new Point(2, 0);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return moveWithoutStrategy(board);
    }
}

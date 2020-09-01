package com.tieto.tictactoesolution.Strategies;

import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;
import com.tieto.tictactoesolution.Enums.Strategy;
import com.tieto.tictactoesolution.NoStrategies.MovementWithoutStrategy;

public class WentSecondStrategyMovement extends StrategyMovement {


    public WentSecondStrategyMovement(PlayerSymbol playerSymbol, int boardSize) {
        initValues(playerSymbol, new MovementWithoutStrategy(playerSymbol, boardSize));
    }

    @Override
    public Strategy checkForStrategies(PlayerSymbol[][] board) {

        if (checkIfMine(board, 1, 1)) {
            if (checkIfEmptyOrMine(board, 0, 1) && checkIfEmptyOrMine(board, 2, 1)) {
                return Strategy.stealTheWinVertical;
            }

            if (checkIfEmptyOrMine(board, 1, 0) && checkIfEmptyOrMine(board, 1, 2)) {
                return Strategy.stealTheWinHorizontal;
            }
        }

        if (checkIfEmptyOrMine(board, 0, 2) && checkIfEmptyOrMine(board, 1, 2) && checkIfEmptyOrMine(board, 2, 2)) {
            return Strategy.wentSecondLastHopeLol;
        }

        if (checkIfEmptyOrMine(board, 2, 2) && checkIfEmptyOrMine(board, 2, 1) && checkIfEmptyOrMine(board, 2, 0)) {
            return Strategy.wentSecondAnotherLastHopeLol;
        }
        return Strategy.nothing;
    }

    @Override
    public Point strategyHandler(PlayerSymbol[][] board) {
        switch (checkForStrategies(board)) {
            case stealTheWinHorizontal:
                return doStealTheWinHorizontal(board);
            case stealTheWinVertical:
                return doStealTheWinVertical(board);
            case wentSecondLastHopeLol:
                return doWentSecondLastHopeLol(board);
            case wentSecondAnotherLastHopeLol:
                return doWentSecondAnotherLastHopeLol(board);
        }
        return moveWithoutStrategy(board);
    }

    private Point doStealTheWinHorizontal(PlayerSymbol[][] board) {
        System.out.println("I am doing StealTheWinHorizontal");
        if (checkIfEmpty(board, 1, 0)) {
            return new Point(1, 0);
        } else if (checkIfEmpty(board, 1, 2)) {
            return new Point(1, 2);
        }
        System.out.println("None of the suggested points worked, panicking now.");
        return moveWithoutStrategy(board);
    }

        private Point doStealTheWinVertical(PlayerSymbol[][] board) {
            System.out.println("I am doing StealTheWinHorizontal");
            if (checkIfEmpty(board, 0,1)) {
                return new Point(0,1);
            } else if (checkIfEmpty(board, 2,1)) {
                return new Point(2,1);
            }
            System.out.println("None of the suggested points worked, panicking now.");
            return moveWithoutStrategy(board);
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
        return moveWithoutStrategy(board);
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
        return moveWithoutStrategy(board);
    }
}

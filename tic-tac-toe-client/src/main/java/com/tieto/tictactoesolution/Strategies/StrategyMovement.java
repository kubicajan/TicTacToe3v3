package com.tieto.tictactoesolution.Strategies;

import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;
import com.tieto.tictactoesolution.Enums.Strategy;
import com.tieto.tictactoesolution.NoStrategies.MovementWithoutStrategy;


public abstract class StrategyMovement {
    private PlayerSymbol playerSymbol;
    private MovementWithoutStrategy movementWithoutStrategy;

    public void initValues(PlayerSymbol playerSymbol,
                           MovementWithoutStrategy movementWithoutStrategy) {
        this.playerSymbol = playerSymbol;
        this.movementWithoutStrategy = movementWithoutStrategy;
    }

    public abstract Point strategyHandler(PlayerSymbol[][] board);
    public abstract Strategy checkForStrategies(PlayerSymbol[][] board);


    public Point moveWithoutStrategy(PlayerSymbol[][] board) {
        return movementWithoutStrategy.calculateBestPosition(board);
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
}

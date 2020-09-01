package com.tieto.tictactoesolution.util;

import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;
import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Value
public class GameBoard {
    private final PlayerSymbol[][] board;
    private final int winnerLength;

    public GameBoard(PlayerSymbol[][] board, int winnerLength) {
        this.board = board;
        this.winnerLength = winnerLength;
    }

    public GameBoard(int size, int winnerLength) {
        this.board = new PlayerSymbol[size][size];
        this.winnerLength = winnerLength;
        this.initGameBoard();
    }

    public void initGameBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = PlayerSymbol.E;
            }
        }
    }

    public boolean isCorrectMove(int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board.length && board[row][column] == PlayerSymbol.E;
    }

    public Point getRandomAvailablePoint() {
        final List<Point> availablePoints = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == PlayerSymbol.E) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        Collections.shuffle(availablePoints);
        return !availablePoints.isEmpty() ? availablePoints.get(0) : null;
    }

    private Optional<PlayerSymbol> getWinner(AtomicInteger xCount, AtomicInteger oCount) {
        if (xCount.get() >= winnerLength) return Optional.of(PlayerSymbol.X);
        if (oCount.get() >= winnerLength) return Optional.of(PlayerSymbol.O);

        return Optional.empty();
    }

    public Optional<PlayerSymbol> getWinner() {
        final Optional<PlayerSymbol> winnerFromRows = getWinnerFromRows();
        if (winnerFromRows.isPresent()) return winnerFromRows;

        final Optional<PlayerSymbol> winnerFromColumns = getWinnerFromColumns();
        if (winnerFromColumns.isPresent()) return winnerFromColumns;

        final Optional<PlayerSymbol> winnerFromLeftDiagonal = getWinnerFromLeftDiagonal();
        if (winnerFromLeftDiagonal.isPresent()) return winnerFromLeftDiagonal;

        return getWinnerFromRightDiagonal();
    }

    private void updateCounts(int x, int y, AtomicInteger xCount, AtomicInteger oCount) {
        if (PlayerSymbol.X.equals(board[x][y])) {
            oCount.set(0);
            xCount.incrementAndGet();
        } else if (PlayerSymbol.O.equals(board[x][y])) {
            xCount.set(0);
            oCount.incrementAndGet();
        } else {
            xCount.set(0);
            oCount.set(0);
        }
    }

    private void resetCounts(AtomicInteger xCount, AtomicInteger oCount) {
        xCount.set(0);
        oCount.set(0);
    }

    private Optional<PlayerSymbol> getWinnerFromRows() {
        Optional<PlayerSymbol> winner;
        AtomicInteger xCount = new AtomicInteger(0);
        AtomicInteger oCount = new AtomicInteger(0);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                updateCounts(i, j, xCount, oCount);
                winner = getWinner(xCount, oCount);
                if (winner.isPresent()) return winner;
            }
            resetCounts(xCount, oCount);
        }

        return Optional.empty();
    }

    private Optional<PlayerSymbol> getWinnerFromColumns() {
        Optional<PlayerSymbol> winner;
        AtomicInteger xCount = new AtomicInteger(0);
        AtomicInteger oCount = new AtomicInteger(0);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                updateCounts(j, i, xCount, oCount);
                winner = getWinner(xCount, oCount);
                if (winner.isPresent()) return winner;
            }
            resetCounts(xCount, oCount);
        }

        return Optional.empty();
    }

    private Optional<PlayerSymbol> getWinnerFromLeftDiagonal() {
        Optional<PlayerSymbol> winner;
        AtomicInteger xCount = new AtomicInteger(0);
        AtomicInteger oCount = new AtomicInteger(0);

        // left to right
        for (int x = 0; x < board.length; x++) {
            for (int i = 0, j = x; i < board.length && j >= 0; i++, j--) {
                updateCounts(i, j, xCount, oCount);
                winner = getWinner(xCount, oCount);
                if (winner.isPresent()) return winner;
            }
            resetCounts(xCount, oCount);
        }
        for (int x = board.length; x > 0; x--) {
            for (int i = board.length - x, j = board.length - 1; i < board.length && j >= board.length - x; i++, j--) {
                updateCounts(i, j, xCount, oCount);
                winner = getWinner(xCount, oCount);
                if (winner.isPresent()) return winner;
            }
            resetCounts(xCount, oCount);
        }

        return Optional.empty();
    }

    private Optional<PlayerSymbol> getWinnerFromRightDiagonal() {
        Optional<PlayerSymbol> winner;
        AtomicInteger xCount = new AtomicInteger(0);
        AtomicInteger oCount = new AtomicInteger(0);

        // right to left
        for (int x = 0; x < board.length; x++) {
            for (int i = 0, j = board.length - 1 - x; i < board.length && j < board.length; i++, j++) {
                updateCounts(i, j, xCount, oCount);
                winner = getWinner(xCount, oCount);
                if (winner.isPresent()) return winner;
            }
            resetCounts(xCount, oCount);
        }
        for (int x = board.length; x > 0; x--) {
            for (int i = board.length - x, j = 0; i < board.length && j < board.length; i++, j++) {
                updateCounts(i, j, xCount, oCount);
                winner = getWinner(xCount, oCount);
                if (winner.isPresent()) return winner;
            }
            resetCounts(xCount, oCount);
        }

        return Optional.empty();
    }

    public void setPlayerSign(int row, int column, PlayerSymbol playerSign) {
        if (!isCorrectMove(row, column)) {
            throw new IllegalArgumentException("Bad move! Coordinates [" + row + ";" + column + "] already contains symbol.");
        }

        board[row][column] = playerSign;
    }

    public PlayerSymbol getPlayerSign(int row, int column) {
        if (!(row >= 0 && row < board.length && column >= 0 && column < board.length)) {
            throw new IllegalArgumentException("Bad coordinates [" + row + ";" + column + "]!");
        }

        return board[row][column];
    }

    public int getBoardSize() {
        return board.length;
    }

    public boolean isFull() {
        for (PlayerSymbol[] row: board) {
            for (PlayerSymbol cell: row) {
                if (PlayerSymbol.E.equals(cell))
                    return false;
            }
        }
        return true;
    }

    public static PlayerSymbol[][] getGameBoardOfSymbol(int size, PlayerSymbol symbol) {
        final PlayerSymbol[][] gameBoard = new PlayerSymbol[size][size];
        for (PlayerSymbol[] row : gameBoard) {
            Arrays.fill(row, symbol);
        }
        return gameBoard;
    }
}

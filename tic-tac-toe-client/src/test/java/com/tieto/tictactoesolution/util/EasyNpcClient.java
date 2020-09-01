package com.tieto.tictactoesolution.util;

import com.tieto.tictactoeclient.api.GameClient;
import com.tieto.tictactoeclient.internal.GameConfiguration;
import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EasyNpcClient implements GameClient {

    @Override
    public void init(GameConfiguration gameConfiguration) { }

    @Override
    public Point getNextMove(PlayerSymbol[][] board) {
        final List<Point> availablePoints = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == PlayerSymbol.E) {
                    availablePoints.add(new Point(row, column));
                }
            }
        }
        Collections.shuffle(availablePoints);
        return !availablePoints.isEmpty() ? availablePoints.get(0) : null;
    }
}

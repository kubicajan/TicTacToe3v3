package com.tieto.tictactoesolution.Player;

import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;

import java.util.ArrayList;
import java.util.List;


public class Player {


    private PlayerSymbol playerSymbol;
    private int boardSize;
    private Point middlePoint;
    private PlayerSymbol[][] board;

    public Player(int boardSize, PlayerSymbol playerSymbol) {
        this.boardSize = boardSize;
        this.playerSymbol = playerSymbol;
        this.middlePoint = new Point(boardSize / 2, boardSize / 2);
    }

    public Point play(PlayerSymbol[][] board) {
        this.board = board;
        List<Point> availableMoves = getAvailableMoves(board);
        if (availableMoves.size() == boardSize * boardSize) {
            return (middlePoint);
        }

        minimax(availableMoves);
        return null;
    }

    private void playeFakeMove(Point point, PlayerSymbol playerSymbol) {
        board[point.getRow()][point.getColumn()] = playerSymbol;
    }

    //TODO: its fucked up, fix. https://github.com/kying18/tic-tac-toe/blob/master/player.py
    private Node minimax(Node node, PlayerSymbol player) {
        PlayerSymbol maxPlayer = this.playerSymbol;
        PlayerSymbol otherPlayer = (player.equals(PlayerSymbol.O) ? PlayerSymbol.X : PlayerSymbol.O);


        if (node.currentWinner == otherPlayer) {
            return ();
        } else if (node.countAvailableMoves <= 0) {
            return ();
        }

        Node bestMove;
        if (player == maxPlayer) {
            bestMove.score = -1000;
        } else {
            bestMove.score = 1000;

        }
        List<Point> availableMoves = getAvailableMoves();

        //simulate moves
        for (Point possibleMove : availableMoves) {
            //simulate moves
            playeFakeMove(possibleMove, otherPlayer);
            node = minimax(node, otherPlayer);

            //undo moves
            playeFakeMove(possibleMove, PlayerSymbol.E);
            node.currentWinner = PlayerSymbol.E;

            //this is optimal next move
            node.potentialPosition = possibleMove;

            if (player == maxPlayer) {
                if (potential.score > bestMove.score) {
                    bestMove = potential;
                }
            } else {
                if (potential.score < bestMove.score) {
                    bestMove = potential;
                }
            }
        }
    }

    private List<Point> getAvailableMoves() {
        List<Point> availableMoves = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].equals(PlayerSymbol.E)) {
                    availableMoves.add(new Point(row, col));
                }
            }
        }
        return availableMoves;
    }

    public class Node {
        PlayerSymbol currentWinner;
        int countAvailableMoves;
        Point potentialPosition;
        Point finalPosition;
        int score;
    }


}

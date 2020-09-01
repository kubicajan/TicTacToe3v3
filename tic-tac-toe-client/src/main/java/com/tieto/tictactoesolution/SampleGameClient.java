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


    @Override
    public void init(GameConfiguration gameConfiguration) {
        this.sessionId = gameConfiguration.getSessionId();
        this.clientId = gameConfiguration.getClientId();
        this.playerSymbol = gameConfiguration.getPlayerSymbol();
        this.boardSize = gameConfiguration.getBoardSize();
        this.winnerLength = gameConfiguration.getWinnerLength();
    }

    private boolean firstCheck = true;

    @Override
    public Point getNextMove(PlayerSymbol[][] board) {
        WentFirstStrategyMovement wentFirstStrategyMovement = new WentFirstStrategyMovement(playerSymbol, boardSize);

        if (playerSymbol.equals(PlayerSymbol.X)) {
            return handleGoingFirst(board, wentFirstStrategyMovement);
        } else {
            return handleGoingSecond(board, wentFirstStrategyMovement);
        }
    }

    private Point handleGoingFirst(PlayerSymbol[][] board, WentFirstStrategyMovement wentFirstStrategyMovement) {
        if (firstCheck) {
            firstCheck = false;
            return new Point(1, 1);
        } else {
            return wentFirstStrategyMovement.strategyHandlerWentFirst(board);
        }
    }

    private Point handleGoingSecond(PlayerSymbol[][] board, WentFirstStrategyMovement wentFirstStrategyMovement) {
        if (firstCheck) {
            firstCheck = false;
            if (wentFirstStrategyMovement.getMiddleOrStartStrategy(board)) {
                return new Point(1, 1);
            }
        }
        return wentFirstStrategyMovement.strategyHandlerWentSecond(board);
    }
}

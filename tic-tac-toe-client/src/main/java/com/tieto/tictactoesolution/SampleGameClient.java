package com.tieto.tictactoesolution;

import com.tieto.tictactoeclient.api.GameClient;
import com.tieto.tictactoeclient.internal.GameConfiguration;
import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;
import com.tieto.tictactoesolution.Strategies.WentFirstStrategyMovement;
import com.tieto.tictactoesolution.Strategies.WentSecondStrategyMovement;

import java.util.UUID;

public class SampleGameClient implements GameClient {

    private UUID sessionId;
    private UUID clientId;
    private PlayerSymbol playerSymbol;
    private int boardSize;
    private int winnerLength;
    private WentFirstStrategyMovement wentFirstStrategyMovement;
    private WentSecondStrategyMovement wentSecondStrategyMovement;


    @Override
    public void init(GameConfiguration gameConfiguration) {
        this.sessionId = gameConfiguration.getSessionId();
        this.clientId = gameConfiguration.getClientId();
        this.playerSymbol = gameConfiguration.getPlayerSymbol();
        this.boardSize = gameConfiguration.getBoardSize();
        this.winnerLength = gameConfiguration.getWinnerLength();
        this.wentFirstStrategyMovement  = new WentFirstStrategyMovement(playerSymbol, boardSize);
        this.wentSecondStrategyMovement = new WentSecondStrategyMovement(playerSymbol, boardSize);
    }

    private boolean firstCheck = true;

    @Override
    public Point getNextMove(PlayerSymbol[][] board) {
        if (playerSymbol.equals(PlayerSymbol.X)) {
            return handleGoingFirst(board, wentFirstStrategyMovement);
        } else {
            return handleGoingSecond(board, wentSecondStrategyMovement);
        }
    }

    private Point handleGoingFirst(PlayerSymbol[][] board, WentFirstStrategyMovement wentFirstStrategyMovement) {
        if (firstCheck) {
            firstCheck = false;
            return new Point(1, 1);
        } else {
            return wentFirstStrategyMovement.strategyHandler(board);
        }
    }

    private Point handleGoingSecond(PlayerSymbol[][] board, WentSecondStrategyMovement wentSecondStrategyMovement) {
        if (firstCheck) {
            firstCheck = false;
            if (wentSecondStrategyMovement.checkIfEmpty(board, 1, 1)) {
                return new Point(1, 1);
            }
        }
        return wentSecondStrategyMovement.strategyHandler(board);
    }
}

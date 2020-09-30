package com.tieto.tictactoesolution;

import com.tieto.tictactoeclient.api.GameClient;
import com.tieto.tictactoeclient.internal.GameConfiguration;
import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;
import com.tieto.tictactoesolution.Player.Player;

import java.util.UUID;

public class SampleGameClient implements GameClient {

    private UUID sessionId;
    private UUID clientId;
    private PlayerSymbol playerSymbol;
    private int boardSize;
    private int winnerLength;
    private Player player;


    @Override
    public void init(GameConfiguration gameConfiguration) {
        this.sessionId = gameConfiguration.getSessionId();
        this.clientId = gameConfiguration.getClientId();
        this.playerSymbol = gameConfiguration.getPlayerSymbol();
        this.boardSize = gameConfiguration.getBoardSize();
        this.winnerLength = gameConfiguration.getWinnerLength();
        this.player = new Player(boardSize, playerSymbol);

    }


    @Override
    public Point getNextMove(PlayerSymbol[][] board) {

        return player.play(board);
    }


}

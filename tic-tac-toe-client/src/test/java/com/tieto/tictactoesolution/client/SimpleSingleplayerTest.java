package com.tieto.tictactoesolution.client;

import com.tieto.tictactoeclient.api.GameClient;
import com.tieto.tictactoeclient.internal.GameConfiguration;
import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;
import com.tieto.tictactoesolution.SampleGameClient;
import com.tieto.tictactoesolution.util.ConsoleUtil;
import com.tieto.tictactoesolution.util.EasyNpcClient;
import com.tieto.tictactoesolution.util.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleSingleplayerTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSingleplayerTest.class);

    private final GameClient gameClient;
    private final GameConfiguration gameConfiguration;
    private final GameBoard gameBoard;

    public SimpleSingleplayerTest() {
        this.gameClient = new SampleGameClient();
        this.gameConfiguration = new GameConfiguration(
                UUID.fromString("97c46e68-e9c7-4915-9228-2f59731a4623"),
                UUID.fromString("b9c722ab-7359-4e2f-ba1c-67b42220d6b4"),
                PlayerSymbol.X,
                3,
                3
        );
        this.gameClient.init(gameConfiguration);
        this.gameBoard = new GameBoard(
                new PlayerSymbol[gameConfiguration.getBoardSize()][gameConfiguration.getBoardSize()],
                gameConfiguration.getWinnerLength());
    }

    @BeforeEach
    public void init() {
        gameBoard.initGameBoard();
    }

    @Test
    public void multiplayerAgainstEasyNpcTest() {
        final EasyNpcClient opponentGameClient = new EasyNpcClient();
        final Optional<PlayerSymbol> winner = playAgainstNpc(opponentGameClient);

        assertThat(winner).hasValue(gameConfiguration.getPlayerSymbol());
    }

    private Optional<PlayerSymbol> playAgainstNpc(GameClient opponentGameClient) {
        Point playerMove;

        do {
            playerMove = this.playMove(gameClient, gameBoard, PlayerSymbol.X);
            ConsoleUtil.printGameBoard(gameBoard, PlayerSymbol.X, playerMove, logger);
            if (gameBoard.getWinner().isPresent() || gameBoard.isFull()) break;

            playerMove = this.playMove(opponentGameClient, gameBoard, PlayerSymbol.O);
            ConsoleUtil.printGameBoard(gameBoard, PlayerSymbol.O, playerMove, logger);
        } while (gameBoard.getWinner().isEmpty() && !gameBoard.isFull());

        final Optional<PlayerSymbol> winner = gameBoard.getWinner();
        if (winner.isPresent()) {
            logger.info(() -> "Winner: " + winner.get());
        } else {
            logger.info(() -> "Draw");
        }
        return winner;
    }

    private Point playMove(GameClient gameClient, GameBoard board, PlayerSymbol playerSymbol) {
        Point playerMove = gameClient.getNextMove(board.getBoard());
        board.setPlayerSign(playerMove.getRow(), playerMove.getColumn(), playerSymbol);
        return playerMove;
    }
}

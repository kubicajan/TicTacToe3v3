package com.tieto.tictactoesolution.client;

import com.tieto.tictactoeclient.api.GameClient;
import com.tieto.tictactoeclient.internal.GameConfiguration;
import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;
import com.tieto.tictactoesolution.SampleGameClient;
import com.tieto.tictactoesolution.util.GameBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class SampleGameClientTest {

    private final GameClient gameClient;
    private final GameConfiguration gameConfiguration;

    public SampleGameClientTest() {
        this.gameClient = new SampleGameClient();
        this.gameConfiguration = new GameConfiguration(
                UUID.fromString("97c46e68-e9c7-4915-9228-2f59731a4623"),
                UUID.fromString("b9c722ab-7359-4e2f-ba1c-67b42220d6b4"),
                PlayerSymbol.X,
                3,
                3
        );
        this.gameClient.init(gameConfiguration);
    }

    @Test
    public void clearGameBoardTest() {
        final Point nextMove = gameClient.getNextMove(GameBoard.getGameBoardOfSymbol(3, PlayerSymbol.E));

        assertNotNull(nextMove);
        assertThat(nextMove.getRow()).isBetween(0, 3);
        assertThat(nextMove.getColumn()).isBetween(0, 3);
    }

    @ParameterizedTest
    @MethodSource("playerSymbolSource")
    public void fullGameBoardTest(PlayerSymbol playerSymbol) {
        final Point nextMove = gameClient.getNextMove(GameBoard.getGameBoardOfSymbol(3, playerSymbol));

        assertNull(nextMove);
    }

    @ParameterizedTest
    @MethodSource("playerSymbolSource")
    public void singleFreeFieldTest(PlayerSymbol playerSymbol) {
        final PlayerSymbol[][] gameBoard = GameBoard.getGameBoardOfSymbol(3, playerSymbol);
        gameBoard[1][2] = PlayerSymbol.E;

        final Point nextMove = gameClient.getNextMove(gameBoard);
        assertEquals(1, nextMove.getRow());
        assertEquals(2, nextMove.getColumn());
    }

    public static Stream<Arguments> playerSymbolSource() {
        return Stream.of(
                Arguments.of(PlayerSymbol.X),
                Arguments.arguments(PlayerSymbol.O)
        );
    }
}

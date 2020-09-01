package com.tieto.tictactoesolution.util;

import com.tieto.tictactoeclient.internal.PlayerSymbol;
import com.tieto.tictactoeclient.internal.Point;
import org.junit.platform.commons.logging.Logger;

public class ConsoleUtil {

    public static void printGameBoard(GameBoard gameBoard, PlayerSymbol playerSymbol, Point point, Logger logger) {
        final StringBuilder rowSeparatorConstructor = new StringBuilder();
        for (int i = 0; i < gameBoard.getBoardSize() * 1.5; i++) {
            rowSeparatorConstructor.append("---");
        }

        final String rowSeparator = rowSeparatorConstructor.toString();
        final StringBuilder logMessage = new StringBuilder();
        logMessage
                .append("Symbol ").append(playerSymbol)
                .append(" placed on [").append(point.getRow()).append(", ").append(point.getColumn()).append("]")
                .append(System.lineSeparator());

        // column numbers
        logMessage.append("  ");
        for (int i = 0; i < gameBoard.getBoardSize(); i++) {
            logMessage.append(" | ").append(i);
        }
        logMessage.append(System.lineSeparator());
        logMessage.append(rowSeparator)
                .append(System.lineSeparator());

        // rows
        for (int row = 0; row < gameBoard.getBoardSize(); row++) {
            // row number
            logMessage.append(" ").append(row).append(" |");

            // values
            for (int column = 0; column < gameBoard.getBoardSize(); column++) {
                logMessage.append(" ").append(gameBoard.getBoard()[row][column]).append(" |");
            }
            logMessage.append(System.lineSeparator())
                    .append(rowSeparator)
                    .append(System.lineSeparator());
        }
        logger.info(logMessage::toString);
    }
}

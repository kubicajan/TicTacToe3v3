package com.tieto.tictactoesolution;

import com.tieto.tictactoeclient.internal.GameFramework;

public class TicTacToeClientApplication {

    public static void main(String[] args) {
        GameFramework.run(new SampleGameClient(), args);
    }
}

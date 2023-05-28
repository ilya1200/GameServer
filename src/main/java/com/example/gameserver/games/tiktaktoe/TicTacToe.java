package com.example.gameserver.games.tiktaktoe;

public class TicTacToe {
    private static final int SIZE = 3;


    private final Board board;
    private char currentPlayer;

    public TicTacToe() {
        board = new Board();
        currentPlayer = X;
    }


    private void switchPlayer() {
        if (currentPlayer == X) {
            currentPlayer = O;
        } else {
            currentPlayer = X;
        }
    }

}

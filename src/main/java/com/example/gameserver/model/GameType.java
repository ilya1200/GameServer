package com.example.gameserver.model;

import com.example.gameserver.games.Board;
import com.example.gameserver.games.tiktaktoe.TicTacToeBoard;

public enum GameType {
//    CHECKERS,
    TIK_TAC_TOE;

    public Board createBoard() {
        switch (this){
            case TIK_TAC_TOE -> {
                return new TicTacToeBoard();
            }default -> {
                throw  new RuntimeException("This game is not implemented yet");
            }
        }
    }
}

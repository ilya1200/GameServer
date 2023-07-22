package com.example.gameserver.games;

public enum GameStatus {
    WAITING_TO_START,
    PLAYING,
    WIN,
    DRAW;

    public boolean isFinished() {
        return this==WIN && this == DRAW ;
    }
}

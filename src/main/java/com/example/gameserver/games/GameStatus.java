package com.example.gameserver.games;

public enum GameStatus {
    PLAYER_1_LEFT,
    PLAYER_2_LEFT,
    PLAYING,
    ;

    public boolean isFinished(){
        return this != PLAYING;
    }
}

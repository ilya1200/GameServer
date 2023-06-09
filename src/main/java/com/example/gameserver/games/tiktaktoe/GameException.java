package com.example.gameserver.games.tiktaktoe;

import com.example.gameserver.model.ErrorMessage;

public class GameException extends RuntimeException{
    private final ErrorMessage message;

    public GameException(ErrorMessage message) {
        this.message = message;
    }

    public ErrorMessage getErrorMessage() {
        return message;
    }
}

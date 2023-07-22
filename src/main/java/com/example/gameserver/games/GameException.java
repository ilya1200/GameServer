package com.example.gameserver.games;

import com.example.gameserver.utils.ErrorMessage;

public class GameException extends RuntimeException {
    private final ErrorMessage message;

    public GameException(ErrorMessage message) {
        this.message = message;
    }

    public ErrorMessage getErrorMessage() {
        return message;
    }
}

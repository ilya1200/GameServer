package com.example.gameserver.model;

public enum ErrorMessage {
    USERNAME_EXIST("The username %s is already exist."),
    USERNAME_NOT_EXIST("The username %s does not exist."),
    WRONG_PASSWORD("Wrong password"),
    NO_SESSION_ID("Did not find user by session ID %s"),
    INVALID_GAME_ID("Did not find a game by gameId %s"),
    USER_ALREADY_IN_GAME("User %s already in the game."),
    ILLEGAL_MOVE("Illegal move"),
    GAME_OVER("The game is over"),
    GAME_IS_FULL("Game is full")
    ;


    private final String messageTemplate;

    ErrorMessage(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }
}

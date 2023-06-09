package com.example.gameserver.model;

public enum ErrorMessage {
    USERNAME_EXIST("The username %s is already exist."),
    USERNAME_NOT_EXIST("The username %s does not exist."),
    WRONG_PASSWORD("Wrong password"),
    NO_SESSION_ID("Did not find user by session ID %s"),
    INVALID_GAME_ID("Did not find a game by gameId %s"),
    USER_ALREADY_IN_GAME("User with this session already in the game %s")
    ;


    private final String messageTemplate;

    ErrorMessage(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }
}

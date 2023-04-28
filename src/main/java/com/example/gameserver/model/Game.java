package com.example.gameserver.model;

import com.example.gameserver.model.db.User;

public class Game {
    private final GameType type;
    private User userFirst;
    private User userSecond;
    private long creationDate;

    public Game(GameType type, User userFirst) {
        this.type = type;
        this.userFirst = userFirst;
        this.creationDate = System.currentTimeMillis();
    }

    public GameType getType() {
        return type;
    }

    public User getUserFirst() {
        return userFirst;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public User getUserSecond() {
        return userSecond;
    }

    public void setUserSecond(User userSecond) {
        this.userSecond = userSecond;
    }
}

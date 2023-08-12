package com.example.gameserver.model.rest.user;

import com.example.gameserver.model.db.User;

public class UserScoreResponse {
    private final String username;
    private final int wins;
    private final int losses;

    public UserScoreResponse(User user) {
        this.username = user.getUsername();
        this.wins = user.getWins();
        this.losses = user.getLosses();
    }

    public String getUsername() {
        return username;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}

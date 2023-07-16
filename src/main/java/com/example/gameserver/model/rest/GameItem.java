package com.example.gameserver.model.rest;

import com.example.gameserver.model.Game;
import com.example.gameserver.model.GameType;

import java.util.UUID;

public class GameItem {
    private final UUID id;
    private final GameType type;
    private final String userFirstName;

    public UUID getId() {
        return id;
    }

    public GameType getType() {
        return type;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public GameItem(Game game){
        this.id = game.getId();
        this.type = game.getType();
        this.userFirstName = game.getUserFirst().getUsername();
    }
}
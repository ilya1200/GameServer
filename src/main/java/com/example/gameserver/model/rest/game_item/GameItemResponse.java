package com.example.gameserver.model.rest.game_item;

import com.example.gameserver.games.Game;
import com.example.gameserver.games.GameType;

import java.util.UUID;

public class GameItemResponse {
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

    public GameItemResponse(Game game) {
        this.id = game.getId();
        this.type = game.getType();
        this.userFirstName = game.getUserFirst().getUsername();
    }
}
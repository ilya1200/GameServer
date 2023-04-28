package com.example.gameserver.model.rest;

import com.example.gameserver.model.GameType;
import jakarta.validation.constraints.NotNull;

public class GameRequest {
    @NotNull(message = "The gameType is required.")
    private GameType gameType;

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}

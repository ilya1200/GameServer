package com.example.gameserver.model.rest;

import com.example.gameserver.model.Game;

import java.util.UUID;

public class GameCreationResponse {
    private UUID id;

    public GameCreationResponse(Game game){
        this.id = game.getId();
    }

    public UUID getId() {
        return id;
    }
}

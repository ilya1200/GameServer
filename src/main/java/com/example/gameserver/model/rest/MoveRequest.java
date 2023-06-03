package com.example.gameserver.model.rest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MoveRequest {

    @NotNull(message = "The move is required.")
    @NotEmpty(message = "The move is required.")
    private String move;

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}

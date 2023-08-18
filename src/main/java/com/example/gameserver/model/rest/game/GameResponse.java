package com.example.gameserver.model.rest.game;

import com.example.gameserver.games.*;

import java.util.UUID;

public class GameResponse {
    private final UUID id;
    private final GameType type;
    private final GameStatus gameStatus;
    private final String userFirstName;
    private final String userSecondName;
    private final Player[][] board;
    private final Player currentPlayer;

    public GameResponse(Game game) {
        this.id = game.getId();
        this.type = game.getType();
        this.gameStatus = game.getGameStatus();
        this.userFirstName = game.hasUserFirst()?game.getUserFirst().getUsername():null;
        this.userSecondName = game.hasUserSecond() ? game.getUserSecond().getUsername() : null;
        this.board = game.getBoard();
        this.currentPlayer = game.getCurrentPlayer();
    }

    public UUID getId() {
        return id;
    }

    public GameType getType() { return type; }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserSecondName() {
        return userSecondName;
    }

    public Player[][] getBoard() { return board; }

    public Player getCurrentPlayer() {return this.currentPlayer;}
}
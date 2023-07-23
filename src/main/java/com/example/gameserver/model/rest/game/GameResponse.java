package com.example.gameserver.model.rest.game;

import com.example.gameserver.games.Board;
import com.example.gameserver.games.Game;
import com.example.gameserver.games.GameStatus;
import com.example.gameserver.games.GameType;

import java.util.UUID;

public class GameResponse {
    private final UUID id;
    private final GameType type;
    private final GameStatus gameStatus;
    private final String userFirstName;
    private final String userSecondName;
    private final BoardResponse board;

    public GameResponse(Game game) {
        this.id = game.getId();
        this.type = game.getType();
        this.gameStatus = game.getGameStatus();
        this.userFirstName = game.getUserFirst().getUsername();
        this.userSecondName = game.hasUserSecond() ? game.getUserSecond().getUsername() : null;
        this.board = new BoardResponse(game.getBoard(),game.getBoardSize());
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

    public BoardResponse getBoard() { return board; }
}
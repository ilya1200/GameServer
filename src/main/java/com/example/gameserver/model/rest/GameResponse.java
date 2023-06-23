package com.example.gameserver.model.rest;

import com.example.gameserver.games.GameStatus;
import com.example.gameserver.games.Move;
import com.example.gameserver.model.Game;
import com.example.gameserver.model.GameType;
import com.example.gameserver.model.db.User;

import java.util.UUID;

public class GameResponse {
    private final UUID id;
    private final GameType type;

    private final GameStatus gameStatus;
    private final String userFirstName;

    private final String userSecondName;

    private final long creationDate;
    private final Move lastMove;

    public GameResponse(Game game){
        this.id = game.getId();
        this.type = game.getType();
        this.gameStatus = game.getGameStatus();
        this.userFirstName = game.getUserFirst().getUsername();
        this.userSecondName = game.hasUserSecond()? game.getUserSecond().getUsername(): null;
        this.creationDate = game.getCreationDate();
        this.lastMove = game.getBoard().getLastMove();
    }

    public UUID getId() {
        return id;
    }

    public GameType getType() {
        return type;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserSecondName() {
        return userSecondName;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public Move getLastMove() {
        return lastMove;
    }
}

package com.example.gameserver.model;

import com.example.gameserver.games.Board;
import com.example.gameserver.model.db.User;

import java.util.UUID;

public class Game {
    private final UUID id;
    private final GameType type;
    private final User userFirst;
    private User userSecond;
    private final long creationDate;

    private final Board board;

    public Game(GameType type, User userFirst) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.userFirst = userFirst;
        this.creationDate = System.currentTimeMillis();
        this.board = type.createBoard();
    }

    public UUID getId() {
        return id;
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

    public boolean isActiveGame(){
        return this.getUserSecond()!=null;
    }

    public boolean isFirstUser(User user){
        return this.getUserFirst().getId().equals(user.getId());
    }

    public Board getBoard() {
        return board;
    }
}

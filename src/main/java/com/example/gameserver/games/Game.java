package com.example.gameserver.games;

import com.example.gameserver.model.db.User;

import java.util.UUID;

public interface Game {
    UUID getId();

    GameType getType();

    GameStatus getGameStatus();

    void setGameStatus(GameStatus gameStatus);

    User getUserFirst();

    User getUserSecond();

    void setUserSecond(User userSecond);

    boolean hasUserSecond();

    boolean isFirstUser(User user);

    boolean isSecondUser(User user);

    Board getBoard();

    void makeMove(String move, Player player);

    void setCurrentTurn(User user);

}
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

    boolean hasUserFirst();

    boolean hasUserSecond();

    public void clearFirstUser();

    public void clearSecondUser();

    boolean isFirstUser(User user);

    boolean isSecondUser(User user);

    Player[][] getBoard();

    int getBoardSize();

    void makeMove(String move, Player player);

    Player getCurrentPlayer();
}
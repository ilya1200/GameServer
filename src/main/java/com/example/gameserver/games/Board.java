package com.example.gameserver.games;

public interface Board {
    void makeMove(String move, Player player);

    Move getLastMove();
}

package com.example.gameserver.games;

public class Move {
    private final String move;
    private final Player player;
    private final GameStatus status;

    public Move(String move, Player player, GameStatus status) {
        this.move = move;
        this.player = player;
        this.status = status;
    }

    public String getMove() {
        return move;
    }

    public Player getPlayer() {
        return player;
    }

    public GameStatus getStatus() {
        return status;
    }
}

package com.example.gameserver.games.checkers;

import java.awt.*;

public class Piece {
    private final Color color;
    private final Board board;
    private Position position;

    public Piece(Color color, Board board, Position position) {
        this.color = color;
        this.board = board;
        this.position = position;
    }

    public boolean makeMove(Position target){
        return true;
    }
}

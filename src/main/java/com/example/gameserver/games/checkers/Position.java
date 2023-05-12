package com.example.gameserver.games.checkers;

public class Position implements  Comparable<Position>{
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public int compareTo(Position o) {
        return o.row == this.row && o.col == this.col?0:-1;
    }
}

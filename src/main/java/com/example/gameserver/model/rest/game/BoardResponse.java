package com.example.gameserver.model.rest.game;

import com.example.gameserver.games.Player;

public class BoardResponse {
    private int boardSize;
    private Player[][] cell;

    public BoardResponse(Player[][] cell, int boardSize){
        this.boardSize = boardSize;
        this.cell = new Player[boardSize][boardSize];
        for(int i=0;i<boardSize;i++){
            System.arraycopy(cell[i], 0, this.cell[i], 0, boardSize);
        }
    }
    public Player[][] getBoard(){
        return cell;
    }
}

package com.example.gameserver.games.tiktaktoe;

import java.util.Scanner;

public class TicTacToe {
    private final Board board;
    private static final char X = 'X';
    private static final char O = 'O';

    private char currentPlayer;

    public TicTacToe() {
        board = new Board();
        currentPlayer = X;
    }

    public void playGame(){
        Scanner scanner = new Scanner(System.in);

        boolean gameFinished = (board.isDraw() || board.isWin());
         while (!gameFinished){
             // Display the board
             board.displayBoard();

             // Get the player's move
             System.out.println("Player " + (currentPlayer == X ? "X" : "O") +" make move:");
             int row = scanner.nextInt();
             int col = scanner.nextInt();

             // Make the move
             try{
                 board.makeMove(currentPlayer, row, col);
             }catch (IllegalArgumentException e){
                 // Handle invalid move
                 System.out.println(e.getMessage());
                 continue;
             }

             if(board.isWin()){
                 gameFinished = true;
                 System.out.println("Player " + (currentPlayer == X ? "X" : "O") + " won!");
             } else if (board.isDraw()) {
                 gameFinished = true;
                 System.out.println("Draw!");
             }else{
                 switchPlayer();
             }
         }
    }


    private void switchPlayer() {
        if (currentPlayer == X) {
            currentPlayer = O;
        } else {
            currentPlayer = X;
        }
    }

    public static void main(String[] args){
        TicTacToe game = new TicTacToe();
        game.playGame();
    }

}

package com.example.gameserver.games.tiktaktoe;

import com.example.gameserver.games.Board;
import com.example.gameserver.games.GameStatus;
import com.example.gameserver.games.Move;
import com.example.gameserver.games.Player;
import com.example.gameserver.model.ErrorMessage;
import org.springframework.data.util.Pair;

import java.util.Vector;

public class TicTacToeBoard implements Board{
    private static final int BOARD_SIZE = 3;

    private final Player[][] cell;
    private Player currentPlayer;

    private boolean isGameFinished;

    private final Vector<Move> moves;

    public TicTacToeBoard(){
        cell = new Player[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                cell[row][col] = null;
            }
        }
        currentPlayer = Player.FIRST;
        isGameFinished = false;
        moves = new Vector<>();
    }


    private void switchPlayer() {
        if (currentPlayer == Player.FIRST) {
            currentPlayer = Player.SECOND;
        } else {
            currentPlayer = Player.FIRST;
        }
    }

    private Pair<Integer, Integer> parseMove(String move){
        final String PATTERN = "[a-c][1-3]";

        if (move == null || !move.matches(PATTERN)){
            throw new IllegalStateException(String.format("Expected move to match the pattern: %s, but actual: %s",PATTERN, move));
        }

        final int COL = move.charAt(0)-'a';
        final int ROW = move.charAt(1)-'1';

        return Pair.of(ROW, COL);
    }

    @Override
    public void makeMove(String move, Player player) {
        Pair<Integer, Integer> position = parseMove(move);
        int row = position.getFirst() ;
        int col = position.getSecond();

        if (isGameFinished){
            throw new GameException(ErrorMessage.GAME_OVER);
        }
        if (player!=currentPlayer){
            throw new GameException(ErrorMessage.ILLEGAL_MOVE);
        }

        this.makeMove(player, row, col);

        GameStatus gameStatus;
        if(isWin()){
            isGameFinished = true;
            System.out.println("Player " + (currentPlayer == Player.FIRST ? Player.FIRST : Player.SECOND) + " won!");
            gameStatus = GameStatus.FINISHED_WIN;
        } else if (isDraw()) {
            isGameFinished = true;
            System.out.println("Draw!");
            gameStatus = GameStatus.FINISHED_DRAW;
        }else{
            switchPlayer();
            gameStatus = GameStatus.PLAYING;
        }
        moves.add(new Move(move, player, gameStatus));
    }

    @Override
    public Move getLastMove() {
        if (moves.isEmpty()){
            return null;
        }
        return moves.lastElement();
    }

    private void makeMove(Player player, int row, int col) throws IllegalArgumentException {
        if(!isValidMove(row,col)){
            throw new GameException(ErrorMessage.ILLEGAL_MOVE);
        }
        cell[row][col] = player;
    }

    public boolean isValidMove(int row, int column) {
        boolean is_move_in_bound = (row >= 0 && row < BOARD_SIZE && column >= 0 && column < BOARD_SIZE);
        return is_move_in_bound && cell[row][column] == null;
    }

    public boolean isWin() {
        // Check rows and columns for a win
        for (int i = 0; i < BOARD_SIZE; i++) {
            boolean rowWin = true;
            boolean colWin = true;

            for (int j = 0; j < BOARD_SIZE; j++) {
                // Check if any element in the row is different from the first element or is empty
                if (cell[i][j] != cell[i][0] || cell[i][0] == null) {
                    rowWin = false;
                }

                // Check if any element in the column is different from the first element or is empty
                if (cell[j][i] != cell[0][i] || cell[0][i] == null) {
                    colWin = false;
                }
            }

            // If a row or column has all elements equal and not empty, return true (win condition met)
            if (rowWin || colWin) {
                return true;
            }
        }

        boolean diagonalWin = true;
        boolean antiDiagonalWin = true;

        // Check diagonals for a win
        for (int i = 0; i < BOARD_SIZE; i++) {
            // Check if any element in the main diagonal is different from the first element or is empty
            if (cell[i][i] != cell[0][0] || cell[0][0] == null) {
                diagonalWin = false;
            }

            // Check if any element in the anti-diagonal is different from the first element or is empty
            if (cell[i][BOARD_SIZE - 1 - i] != cell[0][BOARD_SIZE - 1] || cell[0][BOARD_SIZE - 1] == null) {
                antiDiagonalWin = false;
            }
        }

        // If either the diagonal or anti-diagonal has all elements equal and not empty, return true (win condition met)
        return diagonalWin || antiDiagonalWin;
    }

    public boolean isDraw() {
        if (isWin()) {
            return false; // If there is a win, it's not a draw
        }

        // Check if any empty cell exists
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (cell[i][j] == null) {
                    return false; // If empty cell found, it's not a draw
                }
            }
        }

        // All cells are filled, and there is no win, it's a draw
        return true;
    }

    public void displayBoard() {
        for (Player[] players : cell) {
            for (Player aPlayer : players) {
                char aChar = aPlayer == null? ' ':aPlayer==Player.FIRST?'X':'O';
                System.out.print(" | " + aChar + " | ");
            }
            System.out.println();
        }
    }

}

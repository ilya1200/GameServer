package com.example.gameserver.games.tiktaktoe;

public class Board {
    private static final int BOARD_SIZE = 3;
    private static final char X = 'X';
    private static final char O = 'O';
    private static final char BLANK = ' ';
    private final char[][] cell;

    public Board(){
        cell = new char[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                cell[row][col] = BLANK;
            }
        }
    }

    private boolean isValidMove(int row, int column) {
        boolean is_move_in_bound = (row >= 0 && row < BOARD_SIZE && column >= 0 && column < BOARD_SIZE);
        return is_move_in_bound && cell[row][column] == BLANK;
    }

    private boolean isWin() {
        // Check rows and columns for a win
        for (int i = 0; i < BOARD_SIZE; i++) {
            boolean rowWin = true;
            boolean colWin = true;

            for (int j = 0; j < BOARD_SIZE; j++) {
                // Check if any element in the row is different from the first element or is empty
                if (cell[i][j] != cell[i][0] || cell[i][0] == BLANK) {
                    rowWin = false;
                }

                // Check if any element in the column is different from the first element or is empty
                if (cell[j][i] != cell[0][i] || cell[0][i] == BLANK) {
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
            if (cell[i][i] != cell[0][0] || cell[0][0] == BLANK) {
                diagonalWin = false;
            }

            // Check if any element in the anti-diagonal is different from the first element or is empty
            if (cell[i][BOARD_SIZE - 1 - i] != cell[0][BOARD_SIZE - 1] || cell[0][BOARD_SIZE - 1] == BLANK) {
                antiDiagonalWin = false;
            }
        }

        // If either the diagonal or anti-diagonal has all elements equal and not empty, return true (win condition met)
        return diagonalWin || antiDiagonalWin;
    }


    private boolean isDraw() {
        if (isWin()) {
            return false; // If there is a win, it's not a draw
        }

        // Check if any empty cell exists
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (cell[i][j] == BLANK) {
                    return false; // If empty cell found, it's not a draw
                }
            }
        }

        // All cells are filled, and there is no win, it's a draw
        return true;
    }
}

package com.example.gameserver.games.tic_tac_toe;

import com.example.gameserver.games.Board;
import com.example.gameserver.games.GameException;
import com.example.gameserver.games.Player;
import com.example.gameserver.utils.ErrorMessage;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class TicTacToeBoard implements Board {
    private static final int BOARD_SIZE = 3;
    private final Player[][] cell;

    private final Map<String, Pair<Integer, Integer>> moveMap = new HashMap<>();


    public TicTacToeBoard() {
        cell = new Player[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                cell[row][col] = null;
            }
        }

        moveMap.put("1", Pair.of(0, 0));
        moveMap.put("2", Pair.of(0, 1));
        moveMap.put("3", Pair.of(0, 2));
        moveMap.put("4", Pair.of(1, 0));
        moveMap.put("5", Pair.of(1, 1));
        moveMap.put("6", Pair.of(1, 2));
        moveMap.put("7", Pair.of(2, 0));
        moveMap.put("8", Pair.of(2, 1));
        moveMap.put("9", Pair.of(2, 2));
    }

    private Pair<Integer, Integer> parseMove(String move) {
        final String PATTERN = "[1-9]";

        if (move == null || !move.matches(PATTERN)) {
            throw new IllegalStateException(String.format("Expected move to match the pattern: %s, but actual: %s", PATTERN, move));
        }

        return moveMap.get(move);
    }

    @Override
    public void makeMove(String move, Player player) {
        Pair<Integer, Integer> position = parseMove(move);
        int row = position.getFirst();
        int col = position.getSecond();

        if (!isValidMove(row, col)) {
            throw new GameException(ErrorMessage.ILLEGAL_MOVE);
        }
        cell[row][col] = player;
    }

    private boolean isValidMove(int row, int column) {
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

    @Override
    public Player[][] getBoard() {
        return cell;
    }

    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }
}
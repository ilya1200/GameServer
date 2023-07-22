package com.example.gameserver.games.checkers;


import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public final static int BOARD_SIZE = 8;
    private final Piece[][] pieces = new Piece[8][8];
    private final boolean isWhiteTurn = true;

    public Board() {
        // WHITE
        pieces[0][0] = new Piece(Color.WHITE, this, new Position(0, 0));
        pieces[0][2] = new Piece(Color.WHITE, this, new Position(0, 2));
        pieces[0][4] = new Piece(Color.WHITE, this, new Position(0, 4));
        pieces[0][6] = new Piece(Color.WHITE, this, new Position(0, 6));
        pieces[1][1] = new Piece(Color.WHITE, this, new Position(1, 1));
        pieces[1][3] = new Piece(Color.WHITE, this, new Position(1, 3));
        pieces[1][5] = new Piece(Color.WHITE, this, new Position(1, 5));
        pieces[1][7] = new Piece(Color.WHITE, this, new Position(1, 7));
        pieces[2][0] = new Piece(Color.WHITE, this, new Position(2, 0));
        pieces[2][2] = new Piece(Color.WHITE, this, new Position(2, 2));
        pieces[2][4] = new Piece(Color.WHITE, this, new Position(2, 4));
        pieces[2][6] = new Piece(Color.WHITE, this, new Position(2, 6));

        // BLACK
        pieces[5][1] = new Piece(Color.BLACK, this, new Position(5, 1));
        pieces[5][3] = new Piece(Color.BLACK, this, new Position(5, 3));
        pieces[5][5] = new Piece(Color.BLACK, this, new Position(5, 5));
        pieces[5][7] = new Piece(Color.BLACK, this, new Position(5, 7));
        pieces[6][0] = new Piece(Color.BLACK, this, new Position(6, 0));
        pieces[6][2] = new Piece(Color.BLACK, this, new Position(6, 2));
        pieces[6][4] = new Piece(Color.BLACK, this, new Position(6, 4));
        pieces[6][6] = new Piece(Color.BLACK, this, new Position(6, 6));
        pieces[7][1] = new Piece(Color.BLACK, this, new Position(7, 1));
        pieces[7][3] = new Piece(Color.BLACK, this, new Position(7, 3));
        pieces[7][5] = new Piece(Color.BLACK, this, new Position(7, 5));
        pieces[7][7] = new Piece(Color.BLACK, this, new Position(7, 7));
    }

    public int move(String nextMove, Color color) {
        Pair<Position, Position> positionPair = this.parseMove(nextMove);
        Position source = positionPair.getFirst();
        Position target = positionPair.getSecond();
        Piece piece = pieces[source.getRow()][source.getCol()];
        if (piece == null) {
            throw new IllegalArgumentException(String.format("Cell [%d][%d] is empty.", source.getRow(), source.getCol()));
        }
        if (piece.makeMove(target)) {
//            pieces[source.getRow()][source.getCol()] =
        }
        return -1; // Placeholder
    }

    private Pair<Position, Position> parseMove(String move) {
        final String PATTERN = "^[a-h][1-8][a-h][1-8]$";

        if (move == null || !move.matches(PATTERN)) {
            throw new IllegalStateException(String.format("Expected move to match the pattern: %s, but actual: %s", PATTERN, move));
        }

        final int SOURCE_COL = move.charAt(0) - 'a';
        final int SOURCE_ROW = move.charAt(1) - '1';
        final int TARGET_COL = move.charAt(2) - 'a';
        final int TARGET_ROW = move.charAt(3) - '1';

        Position sourcePosition = new Position(SOURCE_ROW, SOURCE_COL);
        Position targetPosition = new Position(TARGET_ROW, TARGET_COL);
        return Pair.of(sourcePosition, targetPosition);
    }

    private boolean isPieceOfCurrentPlayer(Piece piece) {
        if (isWhiteTurn) {
            return piece.getColor() == Color.WHITE;
        } else {
            return piece.getColor() == Color.BLACK;
        }
    }

    private List<List<Position>> suggestValidMoves() {

        List<List<Position>> validMoves = new ArrayList<>();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (pieces[row][col] == null || !isPieceOfCurrentPlayer(pieces[row][col])) {
                    continue;
                }


                // Check diagonal moves to the right
                if (isValidMove(row, col, row + 1, col + 1)) {
//                    validMoves.add("(" + row + ", " + col + ") to (" + (row + 1) + ", " + (col + 1) + ")");
                }

                // Check diagonal moves to the left
                if (isValidMove(row, col, row + 1, col - 1)) {
//                    validMoves.add("(" + row + ", " + col + ") to (" + (row + 1) + ", " + (col - 1) + ")");
                }

                // Check jumps to the right
                if (isValidJump(row, col, row + 1, col + 1, row + 2, col + 2)) {
//                    validMoves.add("(" + row + ", " + col + ") over (" + (row + 1) + ", " + (col + 1) + ") to (" + (row + 2) + ", " + (col + 2) + ")");
                }

                // Check jumps to the left
                if (isValidJump(row, col, row + 1, col - 1, row + 2, col - 2)) {
//                    validMoves.add("(" + row + ", " + col + ") over (" + (row + 1) + ", " + (col - 1) + ") to (" + (row + 2) + ", " + (col - 2) + ")");
                }

            }
        }

        return validMoves;
    }

    public Piece getPiece(Position position) {
        if (this.pieces[position.getRow()][position.getCol()] == null) {
            return null;
        }
        return this.pieces[position.getRow()][position.getCol()];
    }

    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
//        if (fromRow < 0 || fromRow >= BOARD_SIZE || fromCol < 0 || fromCol >= BOARD_SIZE ||
//                toRow < 0 || toRow >= BOARD_SIZE || toCol < 0 || toCol >= BOARD_SIZE) {
//            return false;
//        }
//
//        char piece = board[fromRow][fromCol];
//        char destination = board[toRow][toCol];
//
//        if (piece != 'r' && piece != 'b' && piece != 'R' && piece != 'B') {
//            return false;
//        }
//
//        if (destination != ' ') {
//            return false;
//        }
//
//        int rowDiff = Math.abs(fromRow - toRow);
//        int colDiff = Math.abs(fromCol - toCol);
//
//        if (rowDiff != 1 || colDiff != 1) {
//            return false;
//        }
//
//        if (piece == 'r' && toRow <= fromRow) {
//            return false;
//        }
//
//        if (piece == 'b' && toRow >= fromRow) {
//            return false;
//        }

        return true;
    }

    private boolean isValidJump(int fromRow, int fromCol, int jumpRow, int jumpCol, int toRow, int toCol) {
//        if (fromRow < 0 || fromRow >= BOARD_SIZE || fromCol < 0 || fromCol >= BOARD_SIZE ||
//                jumpRow < 0 || jumpRow >= BOARD_SIZE || jumpCol < 0 || jumpCol >= BOARD_SIZE ||
//                toRow < 0 || toRow >= BOARD_SIZE || toCol < 0 || toCol >= BOARD_SIZE) {
//            return false;
//        }
//
//        char piece = board[fromRow][fromCol];
//        char destination = board[toRow][toCol];
//
//        if (piece != 'r' && piece != 'b' && piece != 'R' && piece != 'B') {
//            return false;
//        }
//
//        if (destination != ' ') {
//            return false;
//        }
//
//        int rowDiff = Math.abs(fromRow - toRow);
//        int colDiff = Math.abs(fromCol - toCol);
//
//        if (rowDiff != 2 || colDiff != 2) {
//            return false;
//        }
//
//        int jumpedRow = (fromRow + toRow) / 2;
//        int jumpedCol = (fromCol + toCol) / 2;
//        char jumpedPiece = board[jumpedRow][jumpedCol];
//
//        if (jumpedPiece == ' ' || (piece == 'r' && jumpedPiece != 'b' && jumpedPiece != 'B') ||
//                (piece == 'b' && jumpedPiece != 'r' && jumpedPiece != 'R')) {
//            return false;
//        }

        return true;
    }
}

package com.example.gameserver.games.checkers;

public class Piece {
    private final Color color;
    private final Board board;
    private final Position position;

    public Piece(Color color, Board board, Position position) {
        this.color = color;
        this.board = board;
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public boolean makeMove(Position target) {
        return true;
    }

    public boolean checkColor(Color color) {
        return color != null && color == this.color;
    }

    private boolean isValidMove(Position target) {

        if (!target.isValid()) {
            return false;
        }

        Piece piece = board.getPiece(target);
        Piece destination = board.getPiece(target);


        // Check dest
        if (destination != null) {
            return false;
        }

        int fromRow = this.position.getRow();
        int toRow = target.getRow();
        int fromCol = this.position.getCol();
        int toCol = target.getCol();

        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        if (rowDiff != 1 || colDiff != 1) {
            return false;
        }

        if (piece.checkColor(Color.WHITE) && toRow <= fromRow) {
            return false;
        }

        return !piece.checkColor(Color.BLACK) || toRow < fromRow;
    }

    private boolean isValidJump(Position jump, Position target) {
        int fromRow = this.position.getRow();
        int toRow = target.getRow();
        int fromCol = this.position.getCol();
        int toCol = target.getCol();
        int jumpRow = jump.getRow();
        int jumpCol = jump.getCol();


        if (fromRow < 0 || fromRow >= Board.BOARD_SIZE || fromCol < 0 || fromCol >= Board.BOARD_SIZE ||
                jumpRow < 0 || jumpRow >= Board.BOARD_SIZE || jumpCol < 0 || jumpCol >= Board.BOARD_SIZE ||
                toRow < 0 || toRow >= Board.BOARD_SIZE || toCol < 0 || toCol >= Board.BOARD_SIZE) {
            return false;
        }

        Piece piece = board.getPiece(target);
        Piece destination = board.getPiece(target);

//        if (piece != 'r' && piece != 'b' && piece != 'R' && piece != 'B') {
//            return false;
//        }
//
//        if (destination != ' ') {
//            return false;
//        }

        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        if (rowDiff != 2 || colDiff != 2) {
            return false;
        }

        int jumpedRow = (fromRow + toRow) / 2;
        int jumpedCol = (fromCol + toCol) / 2;
//        char jumpedPiece = board[jumpedRow][jumpedCol];
//
//        if (jumpedPiece == ' ' || (piece == 'r' && jumpedPiece != 'b' && jumpedPiece != 'B') ||
//                (piece == 'b' && jumpedPiece != 'r' && jumpedPiece != 'R')) {
//            return false;
//        }

        return true;
    }
}

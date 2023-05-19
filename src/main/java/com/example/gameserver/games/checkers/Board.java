package com.example.gameserver.games.checkers;


import org.springframework.data.util.Pair;

public class Board {
    private final Piece[][] pieces = new Piece[8][8];

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

    public int move(String nextMove, Color color){
        Pair<Position, Position> positionPair = this.parseMove(nextMove);
        Position source = positionPair.getFirst();
        Position target = positionPair.getSecond();
        Piece piece = pieces[source.getRow()][source.getCol()];
        if (piece == null){
            throw new IllegalArgumentException(String.format("Cell [%d][%d] is empty.", source.getRow(),source.getCol()));
        }
        if(piece.makeMove(target)){
//            pieces[source.getRow()][source.getCol()] =
        }
        return -1; // Placeholder
    }

    private Pair<Position, Position> parseMove(String move){
        final String PATTERN = "^[a-h][1-8][a-h][1-8]$";

        if (move == null || !move.matches(PATTERN)){
            throw new IllegalStateException(String.format("Expected move to match the pattern: %s, but actual: %s",PATTERN, move));
        }

        final int SOURCE_COL = move.charAt(0)-'a';
        final int SOURCE_ROW = move.charAt(1)-'1';
        final int TARGET_COL = move.charAt(2)-'a';
        final int TARGET_ROW = move.charAt(3)-'1';

        Position sourcePosition = new Position(SOURCE_ROW, SOURCE_COL);
        Position targetPosition = new Position(TARGET_ROW, TARGET_COL);
        return Pair.of(sourcePosition, targetPosition);
    }
}

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
        Pair<Position, Position> positionPositionPair = this.parseMove(nextMove);
        Position source = positionPositionPair.getFirst();
        Position target = positionPositionPair.getSecond();
        Piece piece = pieces[source.getRow()][source.getCol()];
        if (piece == null){
            throw new IllegalArgumentException("");
        }
        if(piece.makeMove(target)){
//            pieces[source.getRow()][source.getCol()] =
        }
    }

    private Pair<Position, Position> parseMove(String move){
        if(move.length()!=4){
            throw new IllegalStateException("Expected move to be 4 chars long, but actual " + move);
        }
        if (!('a'<= move.charAt(0) && move.charAt(0) <= 'h')){
            throw new IllegalStateException();
        }
        if (!('1'<= move.charAt(1) && move.charAt(1) <= '8')){
            throw new IllegalStateException();
        }
        if (!('a'<= move.charAt(2) && move.charAt(2) <= 'h')){
            throw new IllegalStateException();
        }
        if (!('1'<= move.charAt(3) && move.charAt(3) <= '8')){
            throw new IllegalStateException();
        }

        Position sourcePosition = new Position(move.charAt(0)-'1', move.charAt(1)-'a');
        Position targetPosition = new Position(move.charAt(2)-'1', move.charAt(3)-'a');
        return Pair.of(sourcePosition, targetPosition);
    }
}

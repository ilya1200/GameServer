package com.example.gameserver.games.tic_tac_toe;

import com.example.gameserver.games.*;
import com.example.gameserver.model.db.User;
import com.example.gameserver.utils.ErrorMessage;

import java.util.UUID;

public class TicTacToe implements Game {
    private final UUID id;
    private final GameType type;
    private User userFirst;
    private final Board board;
    private GameStatus gameStatus;
    private User userSecond;
    private Player currentPlayer;

    public TicTacToe(GameType type, User userFirst) {
        this.userFirst = userFirst;
        this.board = new TicTacToeBoard();
        this.id = UUID.randomUUID();
        this.type = type;
        this.gameStatus = GameStatus.WAITING_TO_START;
        this.currentPlayer = Player.FIRST;
    }

    public UUID getId() {
        return id;
    }

    public GameType getType() {
        return type;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public User getUserFirst() {
        return userFirst;
    }

    public User getUserSecond() {
        return userSecond;
    }

    public void setUserSecond(User userSecond) {
        this.userSecond = userSecond;
    }

    public boolean hasUserFirst() {
        return this.getUserFirst() != null;
    }

    public boolean hasUserSecond() {
        return this.getUserSecond() != null;
    }

    public boolean isFirstUser(User user) {
        return this.hasUserFirst() && this.getUserFirst().getId().equals(user.getId());
    }

    public boolean isSecondUser(User user) {
        return this.hasUserSecond() && this.getUserSecond().getId().equals(user.getId());
    }

    public void clearFirstUser(){ this.userFirst = null; }

    public void clearSecondUser(){ this.userSecond = null; }

    public Player[][] getBoard() {
        return board.getBoard();
    }

    public int getBoardSize(){
        return board.getBoardSize();
    }

    private void switchPlayer() {
        if (currentPlayer == Player.FIRST) {
            currentPlayer = Player.SECOND;
        } else {
            currentPlayer = Player.FIRST;
        }
    }

    @Override
    public void makeMove(String move, Player player) {
        if (this.gameStatus != GameStatus.PLAYING || player != currentPlayer) {
            throw new GameException(ErrorMessage.ILLEGAL_MOVE);
        }

        board.makeMove(move,player);
        if (board.isWin()) {
            gameStatus = player==Player.FIRST?GameStatus.PLAYER_1_WIN:GameStatus.PLAYER_2_WIN;
            if(player==Player.FIRST){
                userFirst.incrementWins();
                userSecond.incrementLosses();
            }else{
                userFirst.incrementLosses();
                userSecond.incrementWins();
            }
        } else if (board.isDraw()) {
            System.out.println("Draw!");
            gameStatus = GameStatus.DRAW;
        } else {
            switchPlayer();
        }
    }

    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
}
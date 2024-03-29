package com.example.gameserver.controller;

import com.example.gameserver.games.Game;
import com.example.gameserver.games.GameStatus;
import com.example.gameserver.games.Player;
import com.example.gameserver.games.GameException;
import com.example.gameserver.jpa.UserRepository;
import com.example.gameserver.manager.GameItemManger;
import com.example.gameserver.model.db.User;
import com.example.gameserver.model.rest.game.GameResponse;
import com.example.gameserver.model.rest.game.MoveRequest;
import com.example.gameserver.utils.Constants;
import com.example.gameserver.utils.ErrorMessage;
import com.example.gameserver.utils.ServerUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/games")
public class GameController {
    private final UserRepository userRepository;

    public GameController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static ResponseEntity<?> leaveGame(User user, Game game) {
        GameStatus gameStatus = game.getGameStatus();
        if (game.isFirstUser(user)) {
            if(gameStatus == GameStatus.WAITING_TO_START || gameStatus == GameStatus.PLAYING || gameStatus == GameStatus.PLAYER_2_LEFT){
                game.setGameStatus(GameStatus.PLAYER_1_LEFT);
            }
            game.clearFirstUser();
        } else if (game.isSecondUser(user)) {
            if(gameStatus == GameStatus.WAITING_TO_START || gameStatus == GameStatus.PLAYING || gameStatus == GameStatus.PLAYER_1_LEFT){
                game.setGameStatus(GameStatus.PLAYER_2_LEFT);
            }
            game.clearSecondUser();
        }else{
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.UNAUTHORIZED);
        }

        if(!game.hasUserFirst() && !game.hasUserSecond()){
            GameItemManger.deleteGame(game.getId());
        }

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    public static ResponseEntity<?> joinGame(User user, Game game) {
        if (game.hasUserSecond()) {
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.GAME_IS_FULL, game.getId());
        }
        if (game.getGameStatus().isFinished()) {
            return ServerUtils.createErrorResponse(Constants.GAME_ID, ErrorMessage.GAME_OVER);
        }
        game.setUserSecond(user);
        game.setGameStatus(GameStatus.PLAYING);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{gameId}/board")
    public ResponseEntity<?> makeMove(@RequestParam(Constants.USERNAME) String username, @PathVariable UUID gameId, @RequestBody MoveRequest moveRequest) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST);
        }
        Game game = GameItemManger.getGames().get(gameId);
        if (game == null) {
            return ServerUtils.createErrorResponse(Constants.GAME_ID, ErrorMessage.INVALID_GAME_ID, gameId);
        }

        Player player;

        if (user.equals(game.getUserFirst())) {
            player = Player.FIRST;
        } else if (user.equals(game.getUserSecond())) {
            player = Player.SECOND;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            game.makeMove(moveRequest.getMove(), player);
            GameStatus gameStatus = game.getGameStatus();
            if (gameStatus == GameStatus.PLAYER_1_WIN || gameStatus == GameStatus.PLAYER_2_WIN){
                this.userRepository.save(game.getUserFirst());
                this.userRepository.save(game.getUserSecond());
            }
        } catch (GameException gameException) {
            return ServerUtils.createErrorResponse(Constants.MOVE, gameException.getErrorMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(new GameResponse(game));
    }


    @GetMapping("/{gameId}")
    public ResponseEntity<?> getGameUpdates(@RequestParam(Constants.USERNAME) String username, @PathVariable UUID gameId) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST);
        }
        Game game = GameItemManger.getGames().get(gameId);
        if (game == null) {
            return ServerUtils.createErrorResponse(Constants.GAME_ID, ErrorMessage.INVALID_GAME_ID, gameId);
        }

        if (user.equals(game.getUserFirst()) || user.equals(game.getUserSecond())) {
            return ResponseEntity.status(HttpStatus.OK).body(new GameResponse(game));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
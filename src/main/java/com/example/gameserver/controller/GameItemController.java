package com.example.gameserver.controller;

import com.example.gameserver.games.Game;
import com.example.gameserver.games.GameStatus;
import com.example.gameserver.games.GameType;
import com.example.gameserver.games.tic_tac_toe.TicTacToe;
import com.example.gameserver.jpa.UserRepository;
import com.example.gameserver.manager.GameItemManger;
import com.example.gameserver.model.db.User;
import com.example.gameserver.model.rest.game.GameRequest;
import com.example.gameserver.model.rest.game.GameResponse;
import com.example.gameserver.model.rest.game_item.GameItemPatchActionRequest;
import com.example.gameserver.model.rest.game_item.GameItemResponse;
import com.example.gameserver.utils.Constants;
import com.example.gameserver.utils.ErrorMessage;
import com.example.gameserver.utils.ServerUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.gameserver.utils.ErrorMessage.UNSUPPORTED_GAME_TYPE;

@RestController()
@RequestMapping("/game_item")
public class GameItemController {
    private final UserRepository userRepository;

    public GameItemController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<?> createGame(@RequestBody @Valid GameRequest request, @RequestParam(Constants.USERNAME) String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST);
        }
        if (Objects.requireNonNull(request.getGameType()) != GameType.TIK_TAC_TOE) {
            return ServerUtils.createErrorResponse(Constants.USERNAME, UNSUPPORTED_GAME_TYPE, request.getGameType());
        }
        Game game = new TicTacToe(GameType.TIK_TAC_TOE, user);
        GameItemManger.addGame(game);
        return ResponseEntity.status(HttpStatus.OK).body(new GameItemResponse(game));
    }

    @GetMapping()
    public ResponseEntity<?> getJoinableGames(@RequestParam(Constants.USERNAME) String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST);
        }
        List<GameItemResponse> joinableGames = GameItemManger.getGames().entrySet().stream()
                .filter(g -> !g.getValue().isFirstUser(user) &&
                        !g.getValue().hasUserSecond() &&
                        g.getValue().getGameStatus() == GameStatus.WAITING_TO_START)
                .map(g -> new GameItemResponse(g.getValue()))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(joinableGames);
    }


    @PatchMapping("/{gameId}")
    public ResponseEntity<?> changeGame(@RequestParam(Constants.USERNAME) String username,
                                        @RequestParam(Constants.PASSWORD) String password,
                                        @RequestParam(Constants.PATCH_ACTION) GameItemPatchActionRequest action,
                                        @PathVariable UUID gameId) {
        User user = this.userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST);
        }
        Game game = GameItemManger.getGames().get(gameId);
        if (game == null) {
            return ServerUtils.createErrorResponse(Constants.GAME_ID, ErrorMessage.INVALID_GAME_ID, gameId);
        }



        return switch (action) {
            case JOIN -> GameController.joinGame(user, game);
            case LEAVE -> GameController.leaveGame(user, game);
            default -> throw new RuntimeException("Action unrecognized " + action);
        };
    }
}
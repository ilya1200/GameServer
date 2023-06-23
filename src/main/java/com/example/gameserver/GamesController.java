package com.example.gameserver;

import com.example.gameserver.games.Player;
import com.example.gameserver.games.tiktaktoe.GameException;
import com.example.gameserver.jpa.UserRepository;
import com.example.gameserver.model.ErrorMessage;
import com.example.gameserver.model.Game;
import com.example.gameserver.model.db.User;
import com.example.gameserver.model.rest.GameRequest;
import com.example.gameserver.model.rest.GameResponse;
import com.example.gameserver.model.rest.MoveRequest;
import com.example.gameserver.utils.Constants;
import com.example.gameserver.utils.ServerUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/games")
public class GamesController {
    private Map<UUID,Game> games = new HashMap<UUID, Game>();
    private final UserRepository userRepository;

    public GamesController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<?> createGame(@RequestBody @Valid GameRequest request, @RequestParam(Constants.USERNAME) String username){
        User user = this.userRepository.findByUsername(username);
        if(user==null){
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST);
        }
        Game game = new Game(request.getGameType(), user);
        games.put(game.getId(), game);

        return ResponseEntity.status(HttpStatus.OK).body(new GameResponse(game));
    }

    @GetMapping()
    public ResponseEntity<?> getJoinableGames(@RequestParam(Constants.USERNAME) String username){
        User user = this.userRepository.findByUsername(username);
        if(user==null){
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST);
        }
        List<GameResponse> joinableGames = games.entrySet().stream()
                .filter(g -> !g.getValue().hasUserSecond() && !g.getValue().isFirstUser(user))
                .map(g -> new GameResponse(g.getValue()))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(joinableGames);
    }

    @PatchMapping("/{gameId}")
    public ResponseEntity<?> joinGame(@RequestParam(Constants.USERNAME) String username, @PathVariable UUID gameId){
        User user = this.userRepository.findByUsername(username);
        if(user==null){
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST);
        }
        Game game = games.get(gameId);
        if (game == null){
            return ServerUtils.createErrorResponse(Constants.GAME_ID, ErrorMessage.INVALID_GAME_ID, gameId);
        }

        if(game.isFirstUser(user)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        if(game.isSecondUser(user)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        if(game.hasUserSecond()){
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.GAME_IS_FULL, game.getId());
        }
        
        game.setUserSecond(user);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<?> makeMove(@RequestParam(Constants.USERNAME) String username, @PathVariable UUID gameId, @RequestBody MoveRequest moveRequest){
        User user = this.userRepository.findByUsername(username);
        if(user==null){
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST);
        }
        Game game = games.get(gameId);
        if (game == null){
            return ServerUtils.createErrorResponse(Constants.GAME_ID, ErrorMessage.INVALID_GAME_ID, gameId);
        }

        Player player;

        if (user.equals(game.getUserFirst())){
            player = Player.FIRST;
        } else if (user.equals(game.getUserSecond())) {
            player = Player.SECOND;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            game.getBoard().makeMove(moveRequest.getMove(), player);
        }catch (GameException gameException){
            return ServerUtils.createErrorResponse(Constants.MOVE, gameException.getErrorMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/{gameId}")
    public ResponseEntity<?> getGameUpdates(@RequestParam(Constants.USERNAME) String username, @PathVariable UUID gameId){
        User user = this.userRepository.findByUsername(username);
        if(user==null){
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST);
        }
        Game game = games.get(gameId);
        if (game == null){
            return ServerUtils.createErrorResponse(Constants.GAME_ID, ErrorMessage.INVALID_GAME_ID, gameId);
        }

        if (user.equals(game.getUserFirst()) || user.equals(game.getUserSecond())){
            return ResponseEntity.status(HttpStatus.OK).body(new GameResponse(game));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/{gameId}/quit")
    public ResponseEntity<?> quitAndDeleteGame(@PathVariable UUID gameId){
        if (!games.containsKey(gameId)){
            return ServerUtils.createErrorResponse(Constants.GAME_ID, ErrorMessage.INVALID_GAME_ID, gameId);
        }
        games.remove(gameId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

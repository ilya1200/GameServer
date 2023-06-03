package com.example.gameserver;

import com.example.gameserver.games.Player;
import com.example.gameserver.jpa.SessionRepository;
import com.example.gameserver.jpa.UserRepository;
import com.example.gameserver.model.Game;
import com.example.gameserver.model.db.User;
import com.example.gameserver.model.rest.GameResponse;
import com.example.gameserver.model.rest.GameRequest;
import com.example.gameserver.model.rest.MoveRequest;
import com.example.gameserver.utils.ServerUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/games")
public class GamesController {
    private Map<UUID,Game> games = new HashMap<UUID, Game>();
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public GamesController(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping()
    public ResponseEntity<?> addGame(@RequestBody @Valid GameRequest request, @RequestHeader("session") String sessionId){
        User user = this.userRepository.getUserBySessionId(UUID.fromString(sessionId));
        if(user==null){
            return ServerUtils.createErrorResponse("username", "Did not find user by session ID "+ sessionId);
        }
        Game game = new Game(request.getGameType(), user);
        games.put(game.getId(), game);

        return ResponseEntity.status(HttpStatus.OK).body(new GameResponse(game));
    }

    @GetMapping()
    public ResponseEntity<?> getJoinableGames(@RequestHeader("session") String sessionId){
        User user = this.userRepository.getUserBySessionId(UUID.fromString(sessionId));
        if(user==null){
            return ServerUtils.createErrorResponse("username", "Did not find user by session ID "+ sessionId);
        }
        List<GameResponse> joinableGames = games.entrySet().stream()
                .filter(g -> !g.getValue().isActiveGame() && !g.getValue().isFirstUser(user))
                .map(g -> new GameResponse(g.getValue()))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(joinableGames);
    }

    @PatchMapping("/{gameId}")
    public ResponseEntity<?> joinGame(@RequestHeader("session") String sessionId, @PathVariable UUID gameId){
        User user = this.userRepository.getUserBySessionId(UUID.fromString(sessionId));
        if(user==null){
            return ServerUtils.createErrorResponse("username", "Did not find user by session ID "+ sessionId);
        }
        Game game = games.get(gameId);
        if (game == null){
            return ServerUtils.createErrorResponse("gameId", "Did not find a game by gameId "+ gameId);
        }
        if (game.isFirstUser(user)){
            return ServerUtils.createErrorResponse("session", "User with this session already in the game "+ sessionId);
        }
        game.setUserSecond(user);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<?> makeMove(@RequestHeader("session") String sessionId, @PathVariable UUID gameId, @RequestBody MoveRequest moveRequest){
        User user = this.userRepository.getUserBySessionId(UUID.fromString(sessionId));
        if(user==null){
            return ServerUtils.createErrorResponse("username", "Did not find user by session ID "+ sessionId);
        }
        Game game = games.get(gameId);
        if (game == null){
            return ServerUtils.createErrorResponse("gameId", "Did not find a game by gameId "+ gameId);
        }

        Player player;

        if (user.equals(game.getUserFirst())){
            player = Player.FIRST;
        } else if (user.equals(game.getUserSecond())) {
            player = Player.SECOND;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        game.getBoard().makeMove(moveRequest.getMove(), player);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

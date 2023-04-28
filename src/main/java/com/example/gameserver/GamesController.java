package com.example.gameserver;

import com.example.gameserver.jpa.SessionRepository;
import com.example.gameserver.jpa.UserRepository;
import com.example.gameserver.model.Game;
import com.example.gameserver.model.db.User;
import com.example.gameserver.model.rest.GameRequest;
import com.example.gameserver.utils.ServerUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/games")
public class GamesController {
    private List<Game> games = new ArrayList<Game>();
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
        games.add(game);

        return ResponseEntity.status(HttpStatus.OK).body("Game with ID "+(games.size()-1) + " was created by User: "+user.getUsername());
    }
}

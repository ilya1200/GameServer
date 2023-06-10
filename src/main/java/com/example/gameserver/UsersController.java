package com.example.gameserver;

import com.example.gameserver.jpa.SessionRepository;
import com.example.gameserver.jpa.UserRepository;
import com.example.gameserver.model.ErrorMessage;
import com.example.gameserver.model.db.Session;
import com.example.gameserver.model.db.User;
import com.example.gameserver.model.rest.UserRequest;
import com.example.gameserver.utils.Constants;
import com.example.gameserver.utils.ServerUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/users")
public class UsersController {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public UsersController(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody @Valid UserRequest request){
        if(this.userRepository.existsByUsername(request.getUsername()))
        {
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_EXIST, request.getUsername());
        }
        User user = this.userRepository.save(User.createFromUserRequest(request));
        Session session = this.sessionRepository.save(new Session(user.getId()));
        return ResponseEntity.noContent().header("session", session.getId().toString()).build();
    }

    @GetMapping()
    public ResponseEntity<?> getUser(@RequestParam(Constants.USERNAME) String username, @RequestParam("password") String password){
        User user = this.userRepository.findByUsername(username);
        if(user == null)
        {
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST, username);
        }
        if(!password.equals(user.getPassword())){
            return ServerUtils.createErrorResponse(Constants.PASSWORD, ErrorMessage.WRONG_PASSWORD);
        }

        Session session = this.sessionRepository.save(new Session(user.getId()));
        return ResponseEntity.noContent().header("session", session.getId().toString()).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

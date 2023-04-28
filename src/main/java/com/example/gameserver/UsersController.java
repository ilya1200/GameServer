package com.example.gameserver;

import com.example.gameserver.jpa.SessionRepository;
import com.example.gameserver.jpa.UserRepository;
import com.example.gameserver.model.db.Session;
import com.example.gameserver.model.db.User;
import com.example.gameserver.model.rest.UserRequest;
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
            return this.createErrorResponse("username", "The username "+ request.getUsername()+" is already exist");
        }
        User user = this.userRepository.save(User.createFromUserRequest(request));
        Session session = this.sessionRepository.save(new Session(user.getId()));
        return ResponseEntity.noContent().header("session", session.getId().toString()).build();
    }

    @GetMapping()
    public ResponseEntity<?> getUser(@RequestBody @Valid UserRequest request){
        User user = this.userRepository.findByUsername(request.getUsername());
        if(user == null)
        {
            return this.createErrorResponse("username", "The username "+ request.getUsername()+" does not exist");
        }
        if(!request.getPassword().equals(user.getPassword())){
            return this.createErrorResponse("password", "Wrong password");
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

    public ResponseEntity<?> createErrorResponse(String field, String errorMessage){
        Map<String, String> errors = new HashMap<String, String>();
        errors.put(field, errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}

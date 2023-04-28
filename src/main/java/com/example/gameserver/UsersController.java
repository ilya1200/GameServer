package com.example.gameserver;

import com.example.gameserver.jpa.UserRepository;
import com.example.gameserver.model.db.User;
import com.example.gameserver.model.rest.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/users")
public class UsersController {
    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody @Valid CreateUserRequest request){
        if(this.userRepository.existsByUsername(request.getUsername()))
        {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("username", "The username "+ request.getUsername()+" is already exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        this.userRepository.save(User.createFromUserRequest(request));
        return ResponseEntity.noContent().build();
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

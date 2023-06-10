package com.example.gameserver;

import com.example.gameserver.jpa.UserRepository;
import com.example.gameserver.model.ErrorMessage;
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

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<?> signUp(@RequestBody @Valid UserRequest request){
        if(this.userRepository.existsByUsername(request.getUsername()))
        {
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_EXIST, request.getUsername());
        }
        this.userRepository.save(User.createFromUserRequest(request));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping()
    public ResponseEntity<?> logIn(@RequestParam(Constants.USERNAME) String username, @RequestParam(Constants.PASSWORD) String password){
        User user = this.userRepository.findByUsername(username);
        if(user == null)
        {
            return ServerUtils.createErrorResponse(Constants.USERNAME, ErrorMessage.USERNAME_NOT_EXIST, username);
        }
        if(!password.equals(user.getPassword())){
            return ServerUtils.createErrorResponse(Constants.PASSWORD, ErrorMessage.WRONG_PASSWORD);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
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

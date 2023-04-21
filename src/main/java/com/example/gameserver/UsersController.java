package com.example.gameserver;

import com.example.gameserver.jpa.UserRepository;
import com.example.gameserver.model.db.User;
import com.example.gameserver.model.rest.CreateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/users")
public class UsersController {
    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody @Validated CreateUserRequest request){
        this.userRepository.save(User.createFromUserRequest(request));
        return ResponseEntity.noContent().build();
    }
}

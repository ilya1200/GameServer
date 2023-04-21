package com.example.gameserver;

import com.example.gameserver.model.CreateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/users")
public class UsersController {

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody @Validated CreateUserRequest request){
        return ResponseEntity.noContent().build();
    }
}

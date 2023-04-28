package com.example.gameserver.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ServerUtils {
    public static ResponseEntity<?> createErrorResponse(String field, String errorMessage){
        Map<String, String> errors = new HashMap<String, String>();
        errors.put(field, errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
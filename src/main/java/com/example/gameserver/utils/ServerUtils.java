package com.example.gameserver.utils;

import com.example.gameserver.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ServerUtils {
    public static ResponseEntity<?> createErrorResponse(String field, String errorMessage, int messageID){
        Map<String, Object> errors = new HashMap<>();
        errors.put("field", field);
        errors.put("message", errorMessage);
        errors.put("messageID", messageID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    public static ResponseEntity<?> createErrorResponse(String field, ErrorMessage errorMessage, Object ...args){
        if( args!= null && args.length>0){
            return ServerUtils.createErrorResponse(field, String.format(errorMessage.getMessageTemplate(), args), errorMessage.ordinal());

        }else{
            return ServerUtils.createErrorResponse(field, errorMessage.getMessageTemplate(), errorMessage.ordinal());

        }
    }
}
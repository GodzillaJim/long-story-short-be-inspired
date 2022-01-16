package com.godzillajim.betterprogramming.helpers;

import com.godzillajim.betterprogramming.domain.payloads.MessageResponse;
import com.godzillajim.betterprogramming.domain.payloads.SignupRequest;
import org.springframework.http.ResponseEntity;

public class AuthHelper {
    public static ResponseEntity<MessageResponse> validateSignup(SignupRequest request){
        if(request.getEmail() == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Please provide an email"));
        }
        if(request.getPassword() == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Please provide a password"));
        }
        if(request.getUsername() == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Please provide a username"));
        }
        if(request.getFirstName() == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Please provide a firstName"));
        }
        if(request.getLastName() == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Please provide a lastName"));
        }
        return null;
    }
}

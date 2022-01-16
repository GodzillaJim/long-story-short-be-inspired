package com.godzillajim.betterprogramming.domain.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    String token;
    Long id;
    String username;
    String email;
    List<String> roles;
    String type;
}

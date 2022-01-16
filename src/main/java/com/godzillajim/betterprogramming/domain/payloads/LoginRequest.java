package com.godzillajim.betterprogramming.domain.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = "Provide username")
    private String username;
    @NotNull(message = "Provide password")
    private String password;
}

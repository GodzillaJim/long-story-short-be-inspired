package com.godzillajim.betterprogramming.domain.payloads;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class SignupRequest {
    @NotNull(message = "Provide a username")
    private String username;
    @NotNull(message = "Provide an email address")
    @Email(message = "Provide a valid email address")
    private String email;
    @NotNull(message = "Provide a password")
    private String password;
    @NotNull(message = "First Name is required")
    @NotEmpty
    @NotBlank
    private String firstName;
    @NotNull(message = "Last Name is required")
    @NotEmpty
    @NotBlank
    private String lastName;
    private Set<String> role;
}

package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.config.JwtUtils;
import com.godzillajim.betterprogramming.domain.entities.users.ERole;
import com.godzillajim.betterprogramming.domain.entities.users.Role;
import com.godzillajim.betterprogramming.domain.entities.users.User;
import com.godzillajim.betterprogramming.domain.payloads.JwtResponse;
import com.godzillajim.betterprogramming.domain.payloads.LoginRequest;
import com.godzillajim.betterprogramming.domain.payloads.MessageResponse;
import com.godzillajim.betterprogramming.domain.payloads.SignupRequest;
import com.godzillajim.betterprogramming.domain.repositories.IRoleRepository;
import com.godzillajim.betterprogramming.domain.repositories.IUserRepository;
import com.godzillajim.betterprogramming.helpers.AuthHelper;
import com.godzillajim.betterprogramming.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 36000)
@RestController
@RequestMapping("/api/v1/public/auth")
@RequiredArgsConstructor
public class AuthController {
    final AuthenticationManager authenticationManager;
    final IUserRepository userRepository;
    final IRoleRepository roleRepository;
    final PasswordEncoder encoder;
    final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getId(),userDetails.getUsername(),userDetails.getEmail(),roles,"Bearer"));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        if(AuthHelper.validateSignup(signupRequest) != null){
            return AuthHelper.validateSignup(signupRequest);
        }
        if(userRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(String.format("Username: %s is already taken", signupRequest.getUsername())));
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(String.format("Email: %s is already registered", signupRequest.getEmail())));
        }
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),encoder.encode(signupRequest.getPassword()), signupRequest.getFirstName(),signupRequest.getLastName());
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if(strRoles == null){
            Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Basic role USER does not exist"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

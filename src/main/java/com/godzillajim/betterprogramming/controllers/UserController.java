package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.entities.users.User;
import com.godzillajim.betterprogramming.domain.mappers.PasswordRequest;
import com.godzillajim.betterprogramming.domain.mappers.UserBody;
import com.godzillajim.betterprogramming.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Admin User Controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/users")
public class UserController {
    private final UserService userService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<User> getLoggedInUser(Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}/admin")
    public ResponseEntity<Boolean> makeUserAdmin(@PathVariable Long userId){
         return ResponseEntity.ok(userService.makeUserAdmin(userId));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}/demote")
    public ResponseEntity<Boolean> demoteAdmin(@PathVariable Long userId){
        return ResponseEntity.ok(userService.demoteAdmin(userId));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}/activate")
    public ResponseEntity<Boolean> activateUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.activateUser(userId));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}/deactivate")
    public ResponseEntity<Boolean> deActivateUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.deActivateUser(userId));
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/password")
    public ResponseEntity<Boolean> changePassword(Principal principal, @RequestBody
                                                  PasswordRequest passwordRequest){
        return ResponseEntity.ok(userService.changePassword(principal.getName(), passwordRequest));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{userId}/password")
    public ResponseEntity<String> adminChangePassword(
            @PathVariable Long userId, @RequestBody PasswordRequest passwordRequest){
        userService.adminChangePassword(userId, passwordRequest.getNewPassword());
        return ResponseEntity.ok(passwordRequest.getNewPassword());
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{userId}/update")
    public ResponseEntity<User> updateUserDetails(@PathVariable Long userId, @RequestBody
                                                  UserBody userBody){
        return ResponseEntity.ok(userService.updateUser(userId, userBody));
    }
}

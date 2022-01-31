package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.entities.users.User;
import com.godzillajim.betterprogramming.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

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
}

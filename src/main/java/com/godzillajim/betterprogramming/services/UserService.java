package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.users.ERole;
import com.godzillajim.betterprogramming.domain.entities.users.Role;
import com.godzillajim.betterprogramming.domain.entities.users.User;
import com.godzillajim.betterprogramming.domain.mappers.PasswordRequest;
import com.godzillajim.betterprogramming.domain.mappers.UserBody;
import com.godzillajim.betterprogramming.domain.repositories.IRoleRepository;
import com.godzillajim.betterprogramming.domain.repositories.IUserRepository;
import com.godzillajim.betterprogramming.errors.BadRequestException;
import com.godzillajim.betterprogramming.errors.ResourceNotFoundException;
import com.godzillajim.betterprogramming.errors.UnauthorizedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder encoder;
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()-> new UnauthorizedRequestException("Please login to continue"));
    }
    public Boolean makeUserAdmin(Long userId){
        User user = getUserById(userId);
        Role adminAdmin = roleRepository
                .findByRoleName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("Admin Role not found"));
        user.getRoles().forEach(role -> {
            if(role.getRoleName().equals(adminAdmin.getRoleName())){
                throw new BadRequestException("Role already exists");
            }
        });
        Set<Role> roleList = user.getRoles();
        roleList.add(adminAdmin);
        user.setRoles(roleList);
        userRepository.save(user);
        return true;
    }
    public Boolean activateUser(Long userId) {
        User user = getUserById(userId);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }
    public User getUserById(Long userId){
        return  userRepository.findUserById(userId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s does not exist.", userId)));
    }
    public Boolean demoteAdmin(Long userId) {
        User user = getUserById(userId);
        Role adminRole = roleRepository
                .findByRoleName(ERole.ROLE_ADMIN)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Admin Role not found"));
        Set<Role> roles = user
                .getRoles()
                .stream()
                .filter(
                        role -> !role
                                .getRoleName()
                                .equals(adminRole.getRoleName()))
                .collect(Collectors.toSet());
        if(roles.isEmpty()){
            Role userRole = roleRepository
                    .findByRoleName(ERole.ROLE_USER)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("USER Role not found"));
            roles.add(userRole);
        }
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }
    public Boolean deActivateUser(Long userId) {
        User user = getUserById(userId);
        user.setActive(false);
        userRepository.save(user);
        return true;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public boolean changePassword(String username, PasswordRequest request){
     User user = getUserByUsername(username);
     boolean isAuthorized = encoder.matches(request.getOldPassword(), user.getPassword());
     if(!isAuthorized){
         throw new BadCredentialsException("You entered wrong current password!");
     }
     user.setPassword(encoder.encode(request.getNewPassword()));
     userRepository.save(user);
     return true;
    }
    public void adminChangePassword(Long userId, String newPassword){
        User user = getUserById(userId);
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }
    public User updateUser(Long userId ,UserBody userBody){
        User user = getUserById(userId);
        user.setFirstName(userBody.getFirstName());
        user.setLastName(userBody.getLastName());
        return userRepository.save(user);
    }
}

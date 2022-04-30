package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.users.User;
import com.godzillajim.betterprogramming.domain.repositories.IUserRepository;
import com.godzillajim.betterprogramming.errors.UnauthorizedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@EnableJpaAuditing
@Service
@RequiredArgsConstructor
public class AuditorAwareService implements AuditorAware<User> {

    private final IUserRepository userRepository;

    @SuppressWarnings("NullableProblems")
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if(!authentication.isAuthenticated()){
            throw new UnauthorizedRequestException("Please login to continue");
        }
        Principal principal = (Principal) authentication.getPrincipal();
        return userRepository.findByUsername(principal.getName());
    }
}

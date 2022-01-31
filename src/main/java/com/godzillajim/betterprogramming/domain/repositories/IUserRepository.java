package com.godzillajim.betterprogramming.domain.repositories;

import com.godzillajim.betterprogramming.domain.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}

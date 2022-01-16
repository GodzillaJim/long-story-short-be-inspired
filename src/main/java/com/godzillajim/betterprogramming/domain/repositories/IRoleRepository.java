package com.godzillajim.betterprogramming.domain.repositories;

import com.godzillajim.betterprogramming.domain.entities.users.ERole;
import com.godzillajim.betterprogramming.domain.entities.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole name);
}

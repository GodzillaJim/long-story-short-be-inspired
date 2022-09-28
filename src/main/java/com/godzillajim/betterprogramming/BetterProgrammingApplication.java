package com.godzillajim.betterprogramming;

import com.godzillajim.betterprogramming.domain.entities.users.ERole;
import com.godzillajim.betterprogramming.domain.entities.users.Role;
import com.godzillajim.betterprogramming.domain.entities.users.User;
import com.godzillajim.betterprogramming.domain.repositories.IRoleRepository;
import com.godzillajim.betterprogramming.domain.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@SpringBootApplication
public class BetterProgrammingApplication {

    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    private final PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(BetterProgrammingApplication.class, args);
    }

    @Bean
    InitializingBean loadData() {
        return  () -> {
            var roles = roleRepository.findAll();
            if(roles.isEmpty()){
                Role role = new Role();
                role.setRoleName(ERole.ROLE_ADMIN);
                roleRepository.save(role);
            }

            var users = userRepository.findAll();
            if(users.isEmpty()){
                User user = new User();
                user.setActive(true);
                user.setEmail("jimna.njoroge@thejimna.com");
                user.setFirstName("Jimna");
                user.setLastName("Njoroge");
                user.setUsername("GodzillaJim");
                user.setPassword(encoder.encode("sherlockH@lmes05"));
                List<Role> roleList = roleRepository.findAll();
                Set<Role> roleSet = new HashSet<>(roleList);
                user.setRoles(roleSet);
                userRepository.save(user);
            }
        };
    }
}

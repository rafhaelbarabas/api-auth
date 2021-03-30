package com.rafhael.barabas.apiauth;

import com.rafhael.barabas.apiauth.entities.Permission;
import com.rafhael.barabas.apiauth.entities.User;
import com.rafhael.barabas.apiauth.repositories.PermissonRepository;
import com.rafhael.barabas.apiauth.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

import static java.util.Objects.isNull;

@SpringBootApplication
public class ApiAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAuthApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, PermissonRepository permissonRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            initUsers(userRepository, permissonRepository, passwordEncoder);
        };
    }

    private void initUsers(UserRepository userRepository, PermissonRepository permissonRepository, BCryptPasswordEncoder passwordEncoder) {
        Permission permission = null;
        Permission findPermission = permissonRepository.findByDescription("Admin");
        if (isNull(findPermission)) {
            permission = new Permission();
            permission.setDescription("Admin");
            permission = permissonRepository.save(permission);
        } else {
            permission = findPermission;
        }

        User admin = new User();
        admin.setUsername("rafhaelbarabas");
        admin.setPassword(passwordEncoder.encode("raf.1994"));
        admin.setAccountNonLocked(true);
        admin.setAccountNonExpired(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);
        admin.setPermissions(Arrays.asList(permission));

        User find = userRepository.findByUsername("rafhaelbarabas");
        if (isNull(find)) {
            userRepository.save(admin);
        }
    }
}

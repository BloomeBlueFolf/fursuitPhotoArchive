package com.archive.fursuit.services;

import com.archive.fursuit.User;
import com.archive.fursuit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args){

        if(!userRepository.existsByUsername("admin1")) {

            User admin1 = new User("default", "default", "admin1", passwordEncoder.encode("admin1"), "ADMIN");

            List<User> users = List.of(admin1);

            userRepository.saveAll(users);
        }
    }
}

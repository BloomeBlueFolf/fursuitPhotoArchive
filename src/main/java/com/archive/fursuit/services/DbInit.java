package com.archive.fursuit.services;

import com.archive.fursuit.User;
import com.archive.fursuit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args){
        User superAdmin = new User("SuperAdmin", "SuperAdmin", "SuperAdmin", "SuperAdmin", "ADMIN");

        List<User> users = List.of(superAdmin);

        userRepository.saveAll(users);
    }
}

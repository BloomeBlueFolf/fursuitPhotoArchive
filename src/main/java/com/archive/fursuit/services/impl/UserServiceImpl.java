package com.archive.fursuit.services.impl;

import com.archive.fursuit.User;
import com.archive.fursuit.repositories.UserRepository;
import com.archive.fursuit.services.interfaces.UserServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInterface {

    Logger logger = LoggerFactory.getLogger("UserServiceImpl");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        User newUser = new User(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getRoles());
        logger.info("New account saved in database.");
        return userRepository.save(newUser);
    }

    @Override
    public List<User> findAllAccounts() {
        logger.info("Get all users from database.");
        return userRepository.findAll();
    }

    @Override
    public User findUser(String username) {
        logger.info("Get user by username from database.");
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteAccount(User user){
        logger.info("Delete user from database.");
        userRepository.delete(user);
    }

    @Override
    public boolean existsUser(User user) {
        logger.info("Check if user exists in database by username.");
        return userRepository.existsByUsername(user.getUsername());
    }
}

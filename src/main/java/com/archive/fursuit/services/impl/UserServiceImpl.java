package com.archive.fursuit.services.impl;

import com.archive.fursuit.User;
import com.archive.fursuit.repositories.UserRepository;
import com.archive.fursuit.services.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        User newUser = new User(user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getRoles());
        return userRepository.save(newUser);
    }

    @Override
    public List<User> findAllAccounts() {
        return userRepository.findAll();
    }

    @Override
    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteAccount(User user){
        userRepository.delete(user);
    }

    @Override
    public boolean existsUser(User user) {
        return userRepository.existsByUsername(user.getUsername());
    }
}

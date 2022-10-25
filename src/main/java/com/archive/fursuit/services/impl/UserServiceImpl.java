package com.archive.fursuit.services.impl;

import com.archive.fursuit.User;
import com.archive.fursuit.dto.UserRegistrationDto;
import com.archive.fursuit.repositories.UserRepository;
import com.archive.fursuit.services.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        User newUser = new User(user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
/**                passwordEncoder.encode*/user.getPassword(),
                user.getRoles());
        return userRepository.save(newUser);
    }
}

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
    public User saveUser(UserRegistrationDto userRegistrationDto) {
        User user = new User(userRegistrationDto.getFirstName(),
                userRegistrationDto.getLastName(),
                userRegistrationDto.getEmail(),
/**                passwordEncoder.encode*/userRegistrationDto.getPassword(),
                "USER");
        return userRepository.save(user);
    }
}

package com.archive.fursuit.services.interfaces;

import com.archive.fursuit.User;
import com.archive.fursuit.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends UserDetailsService {

    User saveUser(UserRegistrationDto userRegistrationDto);
}

package com.archive.fursuit.services.interfaces;

import com.archive.fursuit.User;
import com.archive.fursuit.dto.UserRegistrationDto;

public interface UserServiceInterface {

    User saveUser(UserRegistrationDto userRegistrationDto);
}

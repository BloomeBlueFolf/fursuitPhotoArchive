package com.archive.fursuit.services.interfaces;

import com.archive.fursuit.User;

import java.util.List;

public interface UserServiceInterface {

    User saveUser(User user);

    List<User> findAllAccounts();

    User findUser(String username);

    void deleteAccount(User user);

    boolean existsUser(User user);
}

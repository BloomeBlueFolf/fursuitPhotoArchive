package com.archive.fursuit.controllers;

import com.archive.fursuit.User;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserREST {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/private/user/get/{username}")
    public User getUser(@PathVariable ("username") String username){
        return userService.findUser(username);
    }

    @GetMapping("/private/user/getAll")
        public List<User> findAllAccounts(){
        return userService.findAllAccounts();
    }

    @DeleteMapping("/private/user/delete/{username}")
    public String deleteUser(@PathVariable ("username") String username){
        User deletedUser = userService.findUser(username);
        if(deletedUser == null) {
            return String.format("Deleting failed. %s doesn't exist.", username);
        }
        else {
            if (deletedUser.getUsername().equals("admin1")) {
                return "Admin1 is the main admin and can not be deleted!";
            } else {
                userService.deleteAccount(deletedUser);
                return String.format("User %s successfully deleted.", username);
            }
        }
    }

    @PostMapping("private/user/create")
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }
}

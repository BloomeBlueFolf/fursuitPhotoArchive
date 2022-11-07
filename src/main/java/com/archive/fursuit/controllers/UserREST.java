package com.archive.fursuit.controllers;

import com.archive.fursuit.Credentials;
import com.archive.fursuit.User;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserREST {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/private/user/get/{username}")
    public ResponseEntity<?> getUser(@PathVariable ("username") String username, @RequestBody Credentials credentials){
        User user = userService.findUser(credentials.getUsername());
        if(user != null && passwordEncoder.matches(credentials.getPassword(), user.getPassword())){

            if(userService.findUser(username) == null){
                return new ResponseEntity<>("Requested user doesn't exist.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userService.findUser(username), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
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

package com.archive.fursuit.controllers;

import com.archive.fursuit.User;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class UserREST {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("admin/user/get/{username}")
    public ResponseEntity<?> getUser(@PathVariable ("username") String username, @RequestHeader Map<String, String> header){
        User user = userService.findUser(header.get("username"));
        if(user != null && passwordEncoder.matches(header.get("password"), user.getPassword()) && user.getRoles().contains("ADMIN")){

            if(userService.findUser(username) == null){
                return new ResponseEntity<>("Requested user doesn't exist.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userService.findUser(username), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("admin/user/getAll")
        public ResponseEntity<?> findAllAccounts(@RequestHeader Map<String, String> header){
            User user = userService.findUser(header.get("username"));
            if(user != null && passwordEncoder.matches(header.get("password"), user.getPassword()) && user.getRoles().contains("ADMIN")) {
                return new ResponseEntity<>(userService.findAllAccounts(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
            }
    }

    @DeleteMapping("admin/user/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable ("username") String username, @RequestHeader Map<String, String> header){

        if(username.equals("admin1")) {
            return new ResponseEntity<>("admin1 is the standard administrator and cannot be deleted!", HttpStatus.BAD_REQUEST);
        }
        User authUser = userService.findUser(header.get("username"));
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword()) && authUser.getRoles().contains("ADMIN")){

            User deletedUser = userService.findUser(username);
            if(deletedUser == null) {
                return new ResponseEntity<>(String.format("Deleting failed. %s doesn't exist.", username), HttpStatus.NOT_FOUND);
            } else {
                userService.deleteAccount(deletedUser);
                return new ResponseEntity<>(String.format("User %s successfully deleted.", username), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("admin/user/create")
    public ResponseEntity<?> createUser(@RequestBody User user, @RequestHeader Map<String, String> header){
        User authUser = userService.findUser(header.get("username"));
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword()) && authUser.getRoles().contains("ADMIN")) {
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }
}

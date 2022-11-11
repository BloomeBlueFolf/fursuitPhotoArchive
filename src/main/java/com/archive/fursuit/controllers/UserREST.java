package com.archive.fursuit.controllers;

import com.archive.fursuit.User;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class UserREST {

    Logger logger = LoggerFactory.getLogger("UserREST");

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("admin/user/get/{username}")
    public ResponseEntity<?> getUser(@PathVariable ("username") String username,
                                     @RequestHeader Map<String, String> header){

        logger.info("API get user called.");

        User user = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
        if(user != null && passwordEncoder.matches(header.get("password"), user.getPassword()) && user.getRoles().contains("ADMIN")){
            logger.info("Credentials ok.");

            if(userService.findUser(username) == null){
                logger.info("User not found.");
                return new ResponseEntity<>("Requested user doesn't exist.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userService.findUser(username), HttpStatus.OK);
        } else {
            logger.info("Credentials not ok.");
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("admin/user/getAll")
        public ResponseEntity<?> findAllAccounts(@RequestHeader Map<String, String> header){

            logger.info("API get all users called.");

            User user = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
            if(user != null && passwordEncoder.matches(header.get("password"), user.getPassword()) && user.getRoles().contains("ADMIN")) {
                logger.info("Credentials ok.");
                return new ResponseEntity<>(userService.findAllAccounts(), HttpStatus.OK);
            } else {
                logger.info("Credentials not ok.");
                return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
            }
    }

    @DeleteMapping("admin/user/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable ("username") String username,
                                        @RequestHeader Map<String, String> header){

        logger.info("API delete user called.");
        logger.info("Check if standard administrator shall be deleted.");
        if(username.equals("admin1")) {
            return new ResponseEntity<>("admin1 is the standard administrator and cannot be deleted!", HttpStatus.BAD_REQUEST);
        }
        User authUser = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword()) && authUser.getRoles().contains("ADMIN")){

            User deletedUser = userService.findUser(username);
            logger.info("Credentials ok.");
            if(deletedUser == null) {
                logger.error("User not found.");
                return new ResponseEntity<>(String.format("Deleting failed. %s doesn't exist.", username), HttpStatus.NOT_FOUND);
            } else {
                userService.deleteAccount(deletedUser);
                return new ResponseEntity<>(String.format("User %s successfully deleted.", username), HttpStatus.OK);
            }
        } else {
            logger.info("Credentials not ok.");
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("admin/user/create")
    public ResponseEntity<?> createUser(@RequestBody User user,
                                        @RequestHeader Map<String, String> header){

        logger.info("API create user called.");

        User authUser = userService.findUser(header.get("username"));
        logger.info("Check credentials.");
        if(authUser != null && passwordEncoder.matches(header.get("password"), authUser.getPassword()) && authUser.getRoles().contains("ADMIN")) {
            logger.info("Credentials ok.");
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        } else {
            logger.info("Credentials not ok.");
            return new ResponseEntity<>("Wrong credentials. Try again.", HttpStatus.FORBIDDEN);
        }
    }
}

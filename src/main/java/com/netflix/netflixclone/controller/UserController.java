package com.netflix.netflixclone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.netflixclone.entities.User;
import com.netflix.netflixclone.exception.UserException;
import com.netflix.netflixclone.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@RequestHeader("Authorization") String jwt, @PathVariable Long userId) throws UserException {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new UserException("User not found with id: " + userId);
        }
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<User> findUserProfileByJwt(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user == null) {
            throw new UserException("User profile not found for the provided JWT");
        }
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) throws UserException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }
        return ResponseEntity.ok(user);
    }
    
    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
}

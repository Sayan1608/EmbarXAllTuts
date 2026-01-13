package com.social.media.controllers;

import com.social.media.models.User;
import com.social.media.services.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social")
public class SocialMediaController {
    @Autowired
    private SocialMediaService socialMediaService;

    // get All Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        // Implementation goes here
        return ResponseEntity.ok(socialMediaService.getAllUsers());
    }

    // create New User
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        // Implementation goes here
        return ResponseEntity.status(HttpStatus.CREATED).body(socialMediaService.saveUser(user));
    }

    // create New User
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        // Implementation goes here
        socialMediaService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }
}

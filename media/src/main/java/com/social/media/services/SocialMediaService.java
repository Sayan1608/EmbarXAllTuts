package com.social.media.services;

import com.social.media.models.Profile;
import com.social.media.models.User;
import com.social.media.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialMediaService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        Profile profile = user.getProfile();
        if (profile != null && profile.getUser() != user) {
            // ensure owning side is set before persist
            profile.setUser(user);
        }
        User saved = userRepository.save(user);
        return saved;
    }
}

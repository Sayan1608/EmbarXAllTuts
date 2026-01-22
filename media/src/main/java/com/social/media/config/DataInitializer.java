package com.social.media.config;

import com.social.media.models.Group;
import com.social.media.models.Post;
import com.social.media.models.Profile;
import com.social.media.models.User;
import com.social.media.repositories.GroupRepository;
import com.social.media.repositories.PostRepository;
import com.social.media.repositories.ProfileRepository;
import com.social.media.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final  PostRepository postRepository;
    private final GroupRepository groupRepository;


    public DataInitializer(UserRepository userRepository, ProfileRepository profileRepository, PostRepository postRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
        this.groupRepository = groupRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        //save users
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // create two groups
        Group group1 = new Group();
        Group group2 = new Group();

        // associate users with groups
        group1.getUsers().add(user1);
        group1.getUsers().add(user2);
        group2.getUsers().add(user2);
        group2.getUsers().add(user3);

        // save groups
        groupRepository.save(group1);
        groupRepository.save(group2);

        //associate groups with users
        user1.getGroups().add(group1);
        user2.getGroups().add(group1);
        user2.getGroups().add(group2);
        user3.getGroups().add(group2);

        // update users with groups
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);

        // create 3 posts
        Post post1 = new Post();
        Post post2 = new Post();
        Post post3 = new Post();

        // associate posts with users
        post1.setUser(user1);
        post2.setUser(user2);
        post3.setUser(user3);

        // save posts
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        // create profiles and associate with users
        Profile profile1 = new Profile();
        Profile profile2 = new Profile();
        Profile profile3 = new Profile();

        profile1.setUser(user1);
        profile2.setUser(user2);
        profile3.setUser(user3);

        // save profiles
        profileRepository.save(profile1);
        profileRepository.save(profile2);
        profileRepository.save(profile3);

        user1.setProfile(profile1);
        user2.setProfile(profile2);
        user3.setProfile(profile3);

        // update users with profiles
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        System.out.println("Fetch Users");
        userRepository.findById(1L);


    }
}

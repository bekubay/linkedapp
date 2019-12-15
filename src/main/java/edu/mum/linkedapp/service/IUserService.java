package edu.mum.linkedapp.service;

import edu.mum.linkedapp.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    boolean save(User user);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    Optional<User> findById(Long id);
    void follow(String username, String followeeUsername);
    List<User> findFollowers(String username);
    List<User> findFollowing(String username);

    void unfollow(String username, String followeeUsername);

    void updateProfilePicture(String username,String url);
}

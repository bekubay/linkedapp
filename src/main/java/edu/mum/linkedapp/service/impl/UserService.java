package edu.mum.linkedapp.service.impl;

import edu.mum.linkedapp.domain.Profile;
import edu.mum.linkedapp.domain.Role;
import edu.mum.linkedapp.domain.User;
import edu.mum.linkedapp.repository.IRoleRepository;
import edu.mum.linkedapp.repository.IUserRepository;
import edu.mum.linkedapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean save(User user) {
        user.setActive(1);
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ROLE_USER"))));
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        System.out.println("Service-----------------------------");
        System.out.println(username);
        System.out.println("Service -----------------------------");
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public  void follow(String username, String followeeUsername){
        User user = userRepository.findByUsername(username).get();
        User followeeUser = userRepository.findByUsername(followeeUsername).get();
        Set<User> followees;
        if (!user.getFollowing().isEmpty()) {
            followees = new HashSet<User>(user.getFollowing());
        }else{
            followees = new HashSet<User>();
        }
        user.addFollowee(followeeUser);
        userRepository.save(user);
    }
    @Override
    public List<User> findFollowers(String username){
        return userRepository.findFollowers(username);
    }
    @Override
    public List<User> findFollowing(String username){
        return userRepository.findFollowing(username);
    }

    @Override
    public void unfollow(String username, String followeeUsername) {
        User user = userRepository.findByUsername(username).get();
        User followeeUser = userRepository.findByUsername(followeeUsername).get();
        user.removeFollowee(followeeUser);
        userRepository.save(user);
    }
    @Override
    public void updateProfilePicture(String username,String url) {
        User user = userRepository.findByUsername(username).get();
//        Profile profile = new Profile();
//        profile.setProfile_pic_url(url);
        user.setPortrait(url);
        userRepository.save(user);
    }

    @Override
    public List<User> findByNameLike(String name){
        return userRepository.findByNameLike(name);
    }
}

package edu.mum.linkedapp.service;

import edu.mum.linkedapp.domain.User;

import java.util.List;

public interface IUserService {
    boolean save(User user);
    User findByUsername(String username);
    List<User> findAll();
}

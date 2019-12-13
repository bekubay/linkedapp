package edu.mum.linkedapp.service;

import edu.mum.linkedapp.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    boolean save(User user);
    User findByUsername(String username);
    List<User> findAll();
    Optional<User> findById(Long id);

}

package edu.mum.linkedapp.service.impl;

import edu.mum.linkedapp.domain.Role;
import edu.mum.linkedapp.domain.User;
import edu.mum.linkedapp.repository.IRoleRepository;
import edu.mum.linkedapp.repository.IUserRepository;
import edu.mum.linkedapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
}

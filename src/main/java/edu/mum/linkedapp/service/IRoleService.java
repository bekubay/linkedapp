package edu.mum.linkedapp.service;

import edu.mum.linkedapp.domain.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    boolean save(Role role);
    Role findByRole(String role);
    List<Role> findAll();
}

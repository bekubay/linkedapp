package edu.mum.linkedapp.service.impl;

import edu.mum.linkedapp.domain.Role;
import edu.mum.linkedapp.repository.IRoleRepository;
import edu.mum.linkedapp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public boolean save(Role role) {
        roleRepository.save(role);
        return true;
    }

    @Override
    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}

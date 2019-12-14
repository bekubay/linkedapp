package edu.mum.linkedapp.repository;

import edu.mum.linkedapp.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(String role);
}

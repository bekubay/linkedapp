package edu.mum.linkedapp.repository;

import edu.mum.linkedapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

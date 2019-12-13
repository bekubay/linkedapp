package edu.mum.linkedapp.repository;

import edu.mum.linkedapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}

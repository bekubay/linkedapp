package edu.mum.linkedapp.repository;

import edu.mum.linkedapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("Select f from User u join u.followers f where u.username =:username")
    List<User> findFollowers(@Param("username") String username);
    @Query("Select f from User u join u.following f where u.username =:username")
    List<User> findFollowing(@Param("username") String username);
    Optional<User> findByUsername(String username);
    @Query("select u from User u where u.username like %:name% or u.firstname like %:name% or u.lastname like %:name%")
    List<User> findByNameLike(String name);
}

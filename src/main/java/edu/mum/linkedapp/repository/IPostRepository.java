package edu.mum.linkedapp.repository;

import edu.mum.linkedapp.domain.Post;
import edu.mum.linkedapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByOwnerIs(User user);
}

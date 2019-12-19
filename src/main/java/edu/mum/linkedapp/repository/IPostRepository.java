package edu.mum.linkedapp.repository;

import edu.mum.linkedapp.domain.Post;
import edu.mum.linkedapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByOwnerIs(User user);
    public Post findPostById(Long id);
    @Query(value = "select * from post p where p.user_id in (select followee_id from user u join user_network un where u.id = :id1 and u.id = un.follower_id union select :id2 as followee_id) order by p.date", nativeQuery = true)
    public List<Post> findAllByUserWithFollowers(Long id1, Long id2);
}

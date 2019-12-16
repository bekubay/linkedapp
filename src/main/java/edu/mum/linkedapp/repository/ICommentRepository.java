package edu.mum.linkedapp.repository;

import edu.mum.linkedapp.domain.Comment;
import edu.mum.linkedapp.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
}

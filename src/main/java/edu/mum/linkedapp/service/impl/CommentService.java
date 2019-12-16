package edu.mum.linkedapp.service.impl;

import edu.mum.linkedapp.domain.Comment;
import edu.mum.linkedapp.repository.ICommentRepository;
import edu.mum.linkedapp.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommentService implements ICommentService {

    @Autowired
    private ICommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}

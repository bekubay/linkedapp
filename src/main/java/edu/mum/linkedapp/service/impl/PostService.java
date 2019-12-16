package edu.mum.linkedapp.service.impl;

import edu.mum.linkedapp.domain.Post;
import edu.mum.linkedapp.domain.User;
import edu.mum.linkedapp.repository.IPostRepository;
import edu.mum.linkedapp.repository.IUserRepository;
import edu.mum.linkedapp.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PostService implements IPostService {

    @Autowired
    private IPostRepository postDao;
    @Autowired
    private IUserRepository userDao;

    @Override
    public List<Post> getAllPosts() {
        return StreamSupport.stream(postDao.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<Post> getAllPostsByUserId(Long userId) {
        Optional<User> op = userDao.findById(userId);
        User user = null;
        if (op.isPresent()) {
            user = op.get();
            return postDao.findByOwnerIs(user);
        }
        return new ArrayList<Post>();
    }

    @Override
    public Post save(Post post) {
        return postDao.save(post);
    }

    @Override
    public Post getPost(Long id) {
        return postDao.findPostById(id);
    }
}

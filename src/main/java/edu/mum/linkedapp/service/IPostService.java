package edu.mum.linkedapp.service;

import edu.mum.linkedapp.domain.Post;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface IPostService {

    public List<Post> getAllPosts();

    public List<Post> getAllPostsByUserId(Long userId);

    public Post save(Post post);
}

package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.domain.Post;
import edu.mum.linkedapp.service.impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/submitPost")
    public @ResponseBody Post submitPost(@ModelAttribute("post") Post post, Model model) {
        return postService.save(post);
    }

    @GetMapping("/{id}/posts")
    public @ResponseBody List<Post> getAllPostsByUserId(@PathVariable("id") Long userId) {
        List<Post> allPosts = postService.getAllPostsByUserId(userId);
        return allPosts;
    }

    @GetMapping("/allPosts")
    public @ResponseBody List<Post> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        return allPosts;
    }
}

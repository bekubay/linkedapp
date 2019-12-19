package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.bo.CommentBO;
import edu.mum.linkedapp.bo.CommentLikeBO;
import edu.mum.linkedapp.bo.PostBO;
import edu.mum.linkedapp.domain.Comment;
import edu.mum.linkedapp.domain.Post;
import edu.mum.linkedapp.domain.User;
import edu.mum.linkedapp.service.impl.CommentService;
import edu.mum.linkedapp.service.impl.PostService;
import edu.mum.linkedapp.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    public static final Pattern urlPattern= Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    @RequestMapping(value = "/submitPost", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public PostBO submitPost(@RequestParam("content") String content, Model model, Principal principal) {
        Matcher m = urlPattern.matcher(content);//replace with string to compare
        boolean result = m.find();
        int attachType = 0;
        String attach = "";
        if (result) {
            System.out.println("String contains URL");
            attach = m.group(0);
        }

        // video/*,  video/x-m4v, video/webm, video/x-ms-wmv, video/x-msvideo, video/3gpp, video/flv, video/x-flv, video/mp4, video/quicktime, video/mpeg, video/ogv, .ts, .mkv,
        // image/*, image/heic, image/heif
        String lowcaseAttach = attach.toLowerCase();
        if (lowcaseAttach.endsWith(".jpg") || lowcaseAttach.endsWith(".jpeg") || lowcaseAttach.endsWith(".png") ||
                lowcaseAttach.endsWith(".gif") || lowcaseAttach.endsWith(".heic") || lowcaseAttach.endsWith(".heif")) {
            attachType = 1;
        } else {
            attachType = (attach != null && attach.length() > 0) ? 2 : 0;
        }
        User user = userService.findByUsername(principal.getName()).get();
        Post post = new Post();
        post.setDate(new Date());
        post.setAttach(attach);
        post.setAttachType(attachType);
        post.setOwner(user);
        post.setText(content);
        post = postService.save(post);

        PostBO postBO = new PostBO();
        postBO.setUser(user);
        postBO.getPostList().add(post);
        return postBO;
    }

    @GetMapping("/{id}/allPosts")
    public @ResponseBody PostBO getAllPostsByUserId(@PathVariable("id") Long userId, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        List<Post> allPosts = postService.getAllPostsByUserId(userId);
        PostBO postBO = new PostBO();
        postBO.setUser(user);
        postBO.getPostList().addAll(allPosts);
        return postBO;
    }

    @GetMapping("/allPosts")
    public @ResponseBody PostBO getAllPosts(Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        List<Post> allPosts = postService.getAllPosts();
        PostBO postBO = new PostBO();
        postBO.setUser(user);
        postBO.getPostList().addAll(allPosts);
        return postBO;
    }

    @GetMapping("/allFollowersPosts")
    public @ResponseBody PostBO getAllFollowsPosts(Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        List<Post> allPosts = postService.getAllFollowersPostByUserId(user.getId());
        PostBO postBO = new PostBO();
        postBO.setUser(user);
        postBO.getPostList().addAll(allPosts);
        return postBO;
    }

    @PostMapping(value = "/addComment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Comment addComment(@RequestBody CommentBO commentBO, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        Post post = postService.getPost(Long.parseLong(commentBO.getPostId()));
        Comment comment = new Comment();
        comment.setDate(new Date());
        comment.setContent(commentBO.getContent());
        post.addComment(comment);
        comment.setOwner(user);
        comment = commentService.save(comment);
        System.out.println("post: " + post);
        System.out.println("comment: " + comment);
        return comment;
    }

    @PostMapping(value = "/commitLike", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CommentLikeBO commitLike(@RequestBody CommentBO postBO, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        Post post = postService.getPost(Long.parseLong(postBO.getPostId()));
        Set<User> likes = post.getLikedBy();
        CommentLikeBO likeBO = new CommentLikeBO();
        Integer count = likes.size();
        if (count == 0) {
            likeBO.setCount(1);
            likeBO.setLike(true);
            post.addLikedBy(user);
        } else {
            boolean hasLiked = false;
            for (User u: likes) {
                if (u.getUsername().equals(user.getUsername())) {
                    hasLiked = true;
                    break;
                }
            }
            if (hasLiked) {
                post.removeLikedBy(user);
                count--;
            } else {
                post.addLikedBy(user);
                count++;
            }
            likeBO.setCount(count);
            likeBO.setLike(!hasLiked);
        }
        postService.save(post);
        return likeBO;
    }

}

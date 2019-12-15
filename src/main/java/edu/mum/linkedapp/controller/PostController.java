package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.domain.Post;
import edu.mum.linkedapp.domain.User;
import edu.mum.linkedapp.service.impl.PostService;
import edu.mum.linkedapp.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    public static final Pattern urlPattern= Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    @RequestMapping(value = "/submitPost", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Post submitPost(@RequestParam("content") String content, Model model, Principal principal) {
        Matcher m = urlPattern.matcher(content);//replace with string to compare
        boolean result = m.find();
        int attachType = 0;
        String attach = "";
        if (result) {
            System.out.println("String contains URL");
            attach = m.group(0);
        }

//      video/*,  video/x-m4v, video/webm, video/x-ms-wmv, video/x-msvideo, video/3gpp, video/flv, video/x-flv, video/mp4, video/quicktime, video/mpeg, video/ogv, .ts, .mkv,
//      image/*, image/heic, image/heif
        String lowcaseAttach = attach.toLowerCase();
        if (lowcaseAttach.endsWith(".jpg") || lowcaseAttach.endsWith(".jpeg") || lowcaseAttach.endsWith(".png") ||
                lowcaseAttach.endsWith(".gif") || lowcaseAttach.endsWith(".heic") || lowcaseAttach.endsWith(".heif")) {
            attachType = 1;
            System.out.println("image.attachType." + attachType + ": " + attach);
        } else {
            attachType = (attach != null && attach.length() > 0) ? 2 : 0;
            System.out.println("video/text.attachType." + attachType + ": " + attach);
        }
        System.out.println("begin to user");
        User user = userService.findByUsername(principal.getName()).get();
        Post post = new Post();
        post.setDate(new Date());
        post.setAttach(attach);
        post.setAttachType(attachType);
        post.setOwner(user);
        post.setText(content);
        System.out.println("post is: " + post);
        post = postService.save(post);
        System.out.println("new post is: " + post);
        return post;
    }

    @GetMapping("/{id}/allPosts")
    public @ResponseBody List<Post> getAllPostsByUserId(@PathVariable("id") Long userId) {
        System.out.println("userId: " + userId);
        List<Post> allPosts = postService.getAllPostsByUserId(userId);
        return allPosts;
    }

    @GetMapping("/allPosts")
    public @ResponseBody List<Post> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        return allPosts;
    }
}

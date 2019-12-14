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

    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    @RequestMapping(value = "/submitPost", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Post submitPost(@RequestParam("content") String content, Model model, Principal principal) {
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher("content");//replace with string to compare
        if(m.find()) {
            System.out.println("String contains URL");
        }
        boolean result = m.find();
        String attach = "";
        int attachType = 0;
        while (result) {
            for (int i = 1; i <= m.groupCount(); i++) {
                attach = m.group(i);
                if (attach.startsWith("http://localhost:8080")) {
                    content = content.replace(attach, "");
                    break;
                }
            }
            attach = "";
            result = m.find();
        }
//      video/*,  video/x-m4v, video/webm, video/x-ms-wmv, video/x-msvideo, video/3gpp, video/flv, video/x-flv, video/mp4, video/quicktime, video/mpeg, video/ogv, .ts, .mkv,
//      image/*, image/heic, image/heif

        if (attach.endsWith("jpg") || attach.endsWith("jpeg") || attach.endsWith("png") ||
                attach.endsWith("gif") || attach.endsWith("heic") || attach.endsWith("heif")) {
            attachType = 1;
        } else {
            attachType = attach.length() > 0 ? 2 : 0;
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

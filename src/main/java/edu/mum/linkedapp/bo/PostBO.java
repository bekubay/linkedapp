package edu.mum.linkedapp.bo;

import edu.mum.linkedapp.domain.Post;
import edu.mum.linkedapp.domain.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostBO {
    private List<Post> postList = new ArrayList<>();
    private User user;
}

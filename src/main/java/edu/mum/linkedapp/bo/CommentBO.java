package edu.mum.linkedapp.bo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

@Data
public class CommentBO {
    private String content;
    private String postId;
}

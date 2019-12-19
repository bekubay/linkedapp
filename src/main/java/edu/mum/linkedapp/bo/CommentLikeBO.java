package edu.mum.linkedapp.bo;

import lombok.Data;

@Data
public class CommentLikeBO {
    private boolean isLike;
    private Integer count;
}

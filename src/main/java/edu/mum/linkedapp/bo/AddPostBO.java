package edu.mum.linkedapp.bo;

import lombok.Data;

@Data
public class AddPostBO {
    private String content;
    private String file;
    private boolean unhealth;
}

package edu.mum.linkedapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String attachment;

    @Column(columnDefinition = "boolean default true")
    private boolean isActive;
//
//    @Column(nullable = false, columnDefinition = "int default 0")
//    private int attachType;
}

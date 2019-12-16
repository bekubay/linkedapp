package edu.mum.linkedapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User owner;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "post_comment",
        joinColumns = {@JoinColumn(name = "comment_id")},
        inverseJoinColumns = {@JoinColumn(name = "post_id")})
    private Post post;
}

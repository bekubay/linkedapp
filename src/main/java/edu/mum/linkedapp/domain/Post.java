package edu.mum.linkedapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import edu.mum.linkedapp.domain.User;

@NoArgsConstructor
@Data
@Entity
public class Post implements Serializable {

    @Id
    private Long id;

    private String text;

    private String attach;

    /* attach type
        none:0
        image:1
        video:2 */
    @Column(nullable = false, columnDefinition = "int default 0")
    private int attachType;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany
    private Set<User> likedBy = new HashSet<>();

    @OneToMany
    private Set<User> dislikedBy = new HashSet<>();

}

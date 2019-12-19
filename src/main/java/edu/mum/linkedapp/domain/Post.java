package edu.mum.linkedapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.*;

import edu.mum.linkedapp.domain.User;

@NoArgsConstructor
@Data
@Entity
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
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

    /*
    * default is false, not unhealth info
    * */
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean unhealth;

    @OneToMany
    private Set<User> likedBy = new HashSet<>();

    @OneToMany
    private Set<User> dislikedBy = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public boolean addComment(Comment comment) {
        if (comments.add(comment)) {
            comment.setPost(this);
            return true;
        }
        return false;
    }

    public boolean removeComment(Comment comment) {
        if (comments.remove(comment)) {
            comment.setPost(null);
            return true;
        }
        return false;
    }

    public boolean addLikedBy(User user) {
        if (likedBy.add(user)) {
            return true;
        }
        return false;
    }

    public boolean removeLikedBy(User user) {
        if (likedBy.remove(user)) {
            return true;
        }
        return false;
    }

}

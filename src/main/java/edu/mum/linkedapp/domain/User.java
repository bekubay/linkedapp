package edu.mum.linkedapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude="followers")
//@SecondaryTable(name = "profile", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( unique = true, length=(250))
    @Size(min=3, max =12, message = "{size.username.validation}")
    private String username;

    @JsonIgnore
    @NotEmpty (message = "{NotEmpty}")
    @Size(min=3, max =12, message = "{size.password.validation}")
    private String password;

    @NotNull
    @NotEmpty (message = "{NotEmpty}")
    private String firstname;

    @NotNull
    @NotEmpty (message = "{NotEmpty}")
    private String lastname;

    @NotNull
    @NotEmpty (message = "{NotEmpty}")
    @Email
    private String email;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name = "active")
    private int active;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name = "head_portrait")
    private String portrait;

    @JsonIgnore
    @Transient
//    @NotNull
//    @NotEmpty
    private String confirm_password;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_network",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followee_id"))
    private Set<User> following = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "following",fetch = FetchType.EAGER)
    private Set<User> followers = new HashSet<>();

//    @OneToOne(cascade=CascadeType.ALL)
//    @JoinColumn
//    private Profile profile;

    public User(String username, String password, String firstname, String lastname,
                    String email, Date dob, Set<Role> roles ){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dob = dob;
        this.roles = roles;
    }

    public void addFollowee(User user){
        if(following.add(user)){
            user.getFollowers().add(this);
        }
    }
    public void removeFollowee(User user){
        if(following.remove(user)){
            user.getFollowers().remove(this);
        }
    }
}

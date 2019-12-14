package edu.mum.linkedapp.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
//@SecondaryTable(name = "profile", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( unique = true, length=(250))
    @Size(min=3, max =12, message = "{size.username.validation}")
    private String username;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Transient
    @NotNull
    @NotEmpty
    private String confirm_password;

    @OneToMany(mappedBy="followers")
    private List<Follow> followedBy;

    @OneToMany(mappedBy="follows")
    private List<Follow> following;

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
}

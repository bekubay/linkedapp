package edu.mum.linkedapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profile_pic_url;

    @ManyToOne
    @JoinColumn
    private Address address;

//    @OneToOne(mappedBy = "profile")
//    private User user;


}

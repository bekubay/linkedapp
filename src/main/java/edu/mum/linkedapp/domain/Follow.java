package edu.mum.linkedapp.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
public class Follow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    private Date followDate;

    @ManyToOne
    @JoinColumn(name="followers")
    private User followers;

    @ManyToOne
    @JoinColumn(name="following")
    private User follows;


}

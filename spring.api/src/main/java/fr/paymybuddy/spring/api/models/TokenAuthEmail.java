package fr.paymybuddy.spring.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "token_auth_email")
public class TokenAuthEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "update_date")
    private Date updateDate;
}

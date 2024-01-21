package fr.paymybuddy.spring.api.models;

import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String nom;

    @Column(unique = true, nullable = false)
    private String email;

    private String adresse;

    @Column(nullable = false)
    private String password;

    private StatusTypeEnum status;
    @Transient
    private String confirmPassword;
}

package fr.paymybuddy.spring.api.models;

import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.utils.TokenAuthMailUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private String idUser;

    private String prenom;

    private String nom;

    @Column(unique = true, nullable = false)
    private String email;

    private String adresse;

    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    private StatusTypeEnum status;

    public void newIdUser(){
        this.idUser = TokenAuthMailUtil.tokenGenerate(10);
    }
}

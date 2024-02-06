package fr.paymybuddy.spring.api.models;

import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.models.DTO.UserDTO;
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

    private String ville;

    @Column(name = "code_postal")
    private String codePostal;

    private String password;

    @Transient
    private String confirmPassword;

    private StatusTypeEnum status;

    public User() {
    }

    public User(UserDTO user){
        this.id = user.getId();
        this.idUser = user.getIdUser();
        this.nom = user.getNom();
        this.prenom = user.getPrenom();
        this.email = user.getEmail();
        this.adresse = user.getAdresse();
        this.ville = user.getVille();
        this.codePostal = user.getCodePostal();
        this.status = user.getStatus();
    }

    public void newIdUser(){
        this.idUser = TokenAuthMailUtil.tokenGenerate(10);
    }
}

package fr.paymybuddy.spring.api.models.DTO;

import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.models.User;
import lombok.Getter;

@Getter
public class UserDTO {

    private Long id;
    private String idUser;
    private String prenom;
    private String nom;
    private String email;
    private String adresse;
    private String ville;
    private String codePostal;
    private StatusTypeEnum status;

    public UserDTO(User user){
        this.id = user.getId();
        this.idUser = user.getIdUser();
        this.prenom = user.getPrenom();
        this.nom = user.getNom();
        this.email = user.getEmail();
        this.adresse = user.getAdresse();
        this.ville = user.getVille();
        this.codePostal = user.getCodePostal();
        this.status = user.getStatus();
    }

    public void nullAdresse(){
        this.adresse = null;
        this.ville = null;
        this.codePostal = null;
        this.status = null;
    }

}

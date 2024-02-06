package fr.paymybuddy.spring.api.models.DTO;

import fr.paymybuddy.spring.api.models.Carte;
import lombok.Getter;

import java.util.Date;

@Getter
public class CarteDTO {

    private Long id;
    private PortefeuilleDTO portefeuille;
    private String nomCarte;
    private String numCarte;
    private Date moisCarte;
    private int cryptogramme;

    public CarteDTO(Carte carte){
        this.id = carte.getId();
        this.portefeuille = new PortefeuilleDTO(carte.getPortefeuille());
        this.numCarte = carte.getNumCarte();
        this.nomCarte = carte.getNomCarte();
        this.moisCarte = carte.getMoisCarte();
        this.cryptogramme = carte.getCryptogramme();
    }
}

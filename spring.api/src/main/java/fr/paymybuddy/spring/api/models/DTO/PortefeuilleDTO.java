package fr.paymybuddy.spring.api.models.DTO;

import fr.paymybuddy.spring.api.models.Portefeuille;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class PortefeuilleDTO {

    private Long id;
    private UserDTO user;
    private BigDecimal solde;
    private Date updateDate;

    public PortefeuilleDTO(Portefeuille portefeuille){
        this.id = portefeuille.getId();
        this.solde = portefeuille.getSolde();
        this.user = new UserDTO(portefeuille.getUser());
        this.updateDate = portefeuille.getUpdateDate();
    }
}

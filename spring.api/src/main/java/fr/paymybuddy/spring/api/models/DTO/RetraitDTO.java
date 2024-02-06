package fr.paymybuddy.spring.api.models.DTO;

import fr.paymybuddy.spring.api.models.Retrait;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class RetraitDTO {

    private Long id;
    private PortefeuilleDTO portefeuille;
    private IbanDTO iban;
    private BigDecimal montantTotal;
    private BigDecimal montantLiquide;
    private BigDecimal taxe;
    private Date dateRetrait;

    public RetraitDTO(Retrait retrait){
        this.id = retrait.getId();
        this.portefeuille = new PortefeuilleDTO(retrait.getPortefeuille());
        this.iban = new IbanDTO(retrait.getIban());
        this.montantTotal = retrait.getMontantTotal();
        this.montantLiquide = retrait.getMontantLiquide();
        this.taxe = retrait.getTaxe();
        this.dateRetrait = retrait.getDateRetrait();
    }
}

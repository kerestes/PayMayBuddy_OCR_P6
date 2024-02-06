package fr.paymybuddy.spring.api.models.DTO;

import fr.paymybuddy.spring.api.models.Depot;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class DepotDTO {

    private Long id;
    private PortefeuilleDTO portefeuille;
    private CarteDTO carte;
    private IbanDTO iban;
    private BigDecimal montantTotal;
    private BigDecimal montantLiquide;
    private BigDecimal taxe;
    private Date dateDepot;

    public DepotDTO (Depot depot){
        this.id = depot.getId();
        this.portefeuille = new PortefeuilleDTO(depot.getPortefeuille());
        if(depot.getCarte() != null)
            this.carte = new CarteDTO(depot.getCarte());
        if(depot.getIban() != null)
            this.iban = new IbanDTO(depot.getIban());
        this.montantTotal = depot.getMontantTotal();
        this.montantLiquide = depot.getMontantLiquide();
        this.taxe = depot.getTaxe();
        this.dateDepot = depot.getDateDepot();
    }
}

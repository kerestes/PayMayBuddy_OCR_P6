package fr.paymybuddy.spring.api.models.DTO;

import fr.paymybuddy.spring.api.models.TransactionBuddys;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class TransactionBuddysDTO {

    private Long id;
    private PortefeuilleDTO portefeuilleOrigine;
    private PortefeuilleDTO portefeuilleDestination;
    private BigDecimal montantTotal;
    private BigDecimal montantLiquide;
    private BigDecimal taxe;
    private Date dateTransaction;

    public TransactionBuddysDTO(TransactionBuddys transactionBuddys){
        this.id = transactionBuddys.getId();
        this.portefeuilleOrigine = new PortefeuilleDTO(transactionBuddys.getPortefeuilleOrigine());
        this.portefeuilleDestination = new PortefeuilleDTO(transactionBuddys.getPortefeuilleDestination());
        this.montantTotal = transactionBuddys.getMontantTotal();
        this.montantLiquide = transactionBuddys.getMontantLiquide();
        this.taxe = transactionBuddys.getTaxe();
        this.dateTransaction = transactionBuddys.getDateTransaction();
    }
}

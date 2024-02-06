package fr.paymybuddy.spring.api.models.DTO;

import fr.paymybuddy.spring.api.models.Iban;
import lombok.Getter;

@Getter
public class IbanDTO {

    private Long id;
    private PortefeuilleDTO portefeuille;
    private String codeBanque;
    private String codeGuichet;
    private String numeroCompte;
    private String cleRib;
    private String iban;
    private String bic;

    public IbanDTO(Iban iban){
        this.id = iban.getId();
        this.portefeuille = new PortefeuilleDTO(iban.getPortefeuille());
        this.codeBanque = iban.getCodeBanque();
        this.numeroCompte = iban.getNumeroCompte();
        this.cleRib = iban.getCleRib();
        this.iban = iban.getIban();
        this.bic = iban.getBic();
    }
}

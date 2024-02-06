package fr.paymybuddy.spring.api.models;

import fr.paymybuddy.spring.api.models.DTO.IbanDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ibans")
public class Iban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_portefeuille")
    private Portefeuille portefeuille;

    @Column(name = "code_banque")
    private String codeBanque;

    @Column(name = "code_guichet")
    private String codeGuichet;

    @Column(name = "num_compte")
    private String numeroCompte;

    @Column(name = "cle_rib")
    private String cleRib;

    private String iban;

    private String bic;

    public Iban() {
    }

    public Iban(IbanDTO iban) {
        this.id = iban.getId();
        this.portefeuille = new Portefeuille(iban.getPortefeuille());
        this.iban = iban.getIban();
        this.codeBanque = iban.getCodeBanque();
        this.codeGuichet = iban.getCodeGuichet();
        this.numeroCompte = iban.getNumeroCompte();
        this.cleRib = iban.getCleRib();
        this.bic = iban.getBic();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Iban) {
            if (((Iban) obj).toString().equals(this.toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 71;
        int result = 1;
        result = prime * result + ((iban == null) ? 0: iban.hashCode());
        result = prime * result + ((codeBanque == null) ? 0: codeBanque.hashCode());
        result = prime * result + ((codeGuichet == null) ? 0: codeGuichet.hashCode());
        result = prime * result + ((numeroCompte == null) ? 0: numeroCompte.hashCode());
        result = prime * result + ((cleRib == null) ? 0: cleRib.hashCode());
        result = prime * result + ((portefeuille.getId() == null)?0: portefeuille.getId().hashCode());
        return result;
    }

    @Override
    public String toString(){
        return this.iban + this.codeBanque + this.codeGuichet + this.numeroCompte + this.cleRib + this.portefeuille.getId();
    }
}

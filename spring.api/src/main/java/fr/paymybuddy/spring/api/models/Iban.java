package fr.paymybuddy.spring.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
}

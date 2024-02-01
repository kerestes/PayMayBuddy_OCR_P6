package fr.paymybuddy.spring.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "depots")
public class Depot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_portefeuille")
    private Portefeuille portefeuille;

    @ManyToOne
    @JoinColumn(name = "id_carte")
    private Carte carte;

    @ManyToOne
    @JoinColumn(name = "id_iban")
    private Iban iban;

    @Column(name = "montant_total")
    private BigDecimal montantTotal;

    @Column(name= "montant_liquide")
    private BigDecimal montantLiquide;

    private BigDecimal taxe;

    @Column(name = "date_depot")
    private Date dateDepot;
}

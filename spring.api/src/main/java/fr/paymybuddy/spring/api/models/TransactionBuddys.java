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
@Table(name = "transaction_buddys")
public class TransactionBuddys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_portefeuille_origine", nullable = false)
    private Portefeuille portefeuilleOrigine;

    @ManyToOne
    @JoinColumn(name = "id_portefeuille_destination", nullable = false)
    private Portefeuille portefeuilleDestination;

    private BigDecimal montant;

    @Column(name = "date_transaction")
    private Date dateTransaction;
}

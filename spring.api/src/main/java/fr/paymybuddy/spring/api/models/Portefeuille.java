package fr.paymybuddy.spring.api.models;

import fr.paymybuddy.spring.api.models.DTO.PortefeuilleDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="portefeuilles")
public class Portefeuille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal solde;

    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    public Portefeuille(){}

    public Portefeuille(PortefeuilleDTO portefeuilleDTO){
        this.id = portefeuilleDTO.getId();
        this.user = new User(portefeuilleDTO.getUser());
        this.solde = portefeuilleDTO.getSolde();
        this.updateDate = portefeuilleDTO.getUpdateDate();
    }

}

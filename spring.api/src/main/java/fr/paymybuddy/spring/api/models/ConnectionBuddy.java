package fr.paymybuddy.spring.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="connection_buddys")
public class ConnectionBuddy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user1")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "id_user2")
    private User user2;
}
